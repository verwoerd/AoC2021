package day19.part1

import TripleCoordinate
import tripleOrigin
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 19/12/21
 */
fun day19Part1(input: BufferedReader): Any {
  val scanners = input.readInput()
  val (coordinates) = findScannerMap(scanners)
  return coordinates.size
}

fun findScannerMap(scanners: List<Scanner>): Pair<MutableSet<TripleCoordinate>, MutableMap<TripleCoordinate, Scanner>> {
  val baseOrientation = scanners[0]
  val coordinates = baseOrientation.beacons.toMutableSet()
  val queue = scanners.drop(1).map { it.id }.toCollection(LinkedList())
  val placedScanners = mutableMapOf(tripleOrigin to baseOrientation)
  while (queue.isNotEmpty()) {
    val currentId = queue.removeFirst()
    val current = scanners.first { it.id == currentId }
    val distances = placedScanners.asSequence()
      .map { (location, scanner) -> location to calculateScannerOverlapOrientation(scanner, current) }
      .filter { it.second.isNotEmpty() }
      .toMap()
    if (distances.all { (_, value) -> value.isEmpty() }) {
      queue.add(currentId)
      continue
    }
    val mapCoordinate = distances.keys.first()
    val diffMap = distances[mapCoordinate]!!
    val orientation = findOrientation(diffMap)
    val (a, b) = diffMap.entries.first()
    val location = a - orientation(b)
    val mapCoordinates = current.beacons.map(orientation).map { location + it }
    placedScanners[location] = current.copy(beacons = mapCoordinates.toMutableList())
    coordinates.addAll(mapCoordinates)
  }
  return coordinates to placedScanners

}

fun BufferedReader.readInput() = readLines()
  .fold(Scanner(-1) to mutableListOf<Scanner>()) { (scanner, list), line ->
    when {
      line.isBlank() -> scanner to list.also { list.add(scanner) }
      line.startsWith("---") -> Scanner(line.drop(12).dropLast(4).toInt()) to list
      else ->
        scanner.also {
          it.beacons.add(
            line.split(",").let { (x, y, z) -> TripleCoordinate(x.toLong(), y.toLong(), z.toLong()) })
        } to list
    }
  }.let { (scanner, list) -> list.also { it.add(scanner) } }

fun findOrientation(matches: Map<TripleCoordinate, TripleCoordinate>) = generateOrientations().first() { o ->
  val base = matches.entries.first().let { (k, v) -> k - o(v) }
  matches.map { (k, v) -> k to base + o(v) }.all { (k, v) -> k == v }
}

fun calculateScannerOverlapOrientation(base: Scanner, other: Scanner) =
  base.beacons.map { beacon ->
    val distance = base.beaconDistanceMap[beacon]!!
    beacon to other.beaconDistanceMap.map { (candidate, otherDistances) ->
      candidate to otherDistances.filter { it in distance }
    }.filter { it.second.size >= 11 }.map { it.first }
  }.filter { it.second.size == 1 }.associate { it.first to it.second.first() }

data class Scanner(
  val id: Int,
  val beacons: MutableList<TripleCoordinate> = mutableListOf()
) {
  val beaconDistanceMap by lazy {
    beacons.associateWith { current ->
      beacons.filter { it != current }.map { current - it }
        .map { it.x * it.x + it.y * it.y + it.z * it.z }
    }
  }
}

fun generateOrientations(): Sequence<(TripleCoordinate) -> TripleCoordinate> = sequenceOf(
  { TripleCoordinate(it.x, it.y, it.z) },
  { TripleCoordinate(it.x, -it.z, it.y) },
  { TripleCoordinate(it.x, -it.y, -it.z) },
  { TripleCoordinate(it.x, it.z, -it.y) },

  { TripleCoordinate(-it.x, -it.y, it.z) },
  { TripleCoordinate(-it.x, -it.z, -it.y) },
  { TripleCoordinate(-it.x, it.y, -it.z) },
  { TripleCoordinate(-it.x, it.z, it.y) },

  { TripleCoordinate(it.y, it.z, it.x) },
  { TripleCoordinate(it.y, -it.x, it.z) },
  { TripleCoordinate(it.y, -it.z, -it.x) },
  { TripleCoordinate(it.y, it.x, -it.z) },

  { TripleCoordinate(-it.y, -it.z, it.x) },
  { TripleCoordinate(-it.y, -it.x, -it.z) },
  { TripleCoordinate(-it.y, it.z, -it.x) },
  { TripleCoordinate(-it.y, it.x, it.z) },

  { TripleCoordinate(it.z, it.x, it.y) },
  { TripleCoordinate(it.z, -it.y, it.x) },
  { TripleCoordinate(it.z, -it.x, -it.y) },
  { TripleCoordinate(it.z, it.y, -it.x) },

  { TripleCoordinate(-it.z, -it.x, it.y) },
  { TripleCoordinate(-it.z, -it.y, -it.x) },
  { TripleCoordinate(-it.z, it.x, -it.y) },
  { TripleCoordinate(-it.z, it.y, it.x) }
)
