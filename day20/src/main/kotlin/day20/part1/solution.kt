package day20.part1

import Coordinate
import toInt
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 20/12/21
 */
fun day20Part1(input: BufferedReader): Any {
  val (diffMap, scan) = input.readInput()
  return enhance(scan, 2, diffMap)
}

fun BufferedReader.readInput(): Pair<Map<Int, Boolean>, Map<Coordinate, Boolean>> {
  val diffMap = readLine().mapIndexed { index, c -> index to (c == '#') }.toMap()
  readLine()
  val scan = lineSequence().flatMapIndexed { y, line ->
    line.mapIndexed { x, c -> Coordinate(x, y) to (c == '#') }
  }.toMap().withDefault { false }
  return diffMap to scan
}


fun enhance(original: Map<Coordinate, Boolean>, times: Int, diffMap: Map<Int, Boolean>): Int {
  var scan = original.toMap().withDefault { false }
  repeat(times) {
    scan = scan.enhance(
      diffMap, when {
        it % 2 == 0 -> diffMap.getValue(0)
        else -> diffMap.getValue(diffMap.getValue(0).toInt() * 511)
      }
    )
  }
  return scan.count { it.value }
}

fun Map<Coordinate, Boolean>.enhance(diffMap: Map<Int, Boolean>, default: Boolean): Map<Coordinate, Boolean> {
  val target = this.toMutableMap().withDefault { default }
  val xRange = xRange()
  val yRange = yRange()
  (yRange.first-2 ..yRange.second+2).map { y ->
    (xRange.first-2..xRange.second+2).map { x ->
      val current = Coordinate(x, y)
      target[current] = diffMap.getValue(getNumberFor(current))
    }
  }
  return target
}

fun Map<Coordinate, Boolean>.getNumberFor(coordinate: Coordinate) =
  sequenceOf(
    Coordinate(coordinate.x - 1, coordinate.y - 1),
    Coordinate(coordinate.x, coordinate.y - 1),
    Coordinate(coordinate.x + 1, coordinate.y - 1),
    Coordinate(coordinate.x - 1, coordinate.y),
    Coordinate(coordinate.x, coordinate.y),
    Coordinate(coordinate.x + 1, coordinate.y),
    Coordinate(coordinate.x - 1, coordinate.y + 1),
    Coordinate(coordinate.x, coordinate.y + 1),
    Coordinate(coordinate.x + 1, coordinate.y + 1),
  ).fold(0) { acc, c ->
    (acc shl 1) + getValue(c).toInt()
  }
