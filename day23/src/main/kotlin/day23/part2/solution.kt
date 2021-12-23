package day23.part2

import Coordinate
import manhattanDistance
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 23/12/21
 */
fun day23Part2(input: BufferedReader): Any {
  val grid = input.readInput()
  val seen = mutableSetOf<Map<Coordinate, Amphipod>>()
  val queue = priorityQueueOf(Comparator { o1, o2 ->
    when (val diff = o1.first.compareTo(o2.first)) {
      0 -> o1.second.closenessHash().compareTo(o2.second.closenessHash())
      else -> diff
    }
  }, 0L to grid)
  val transitionMap = mutableMapOf<Map<Coordinate, Amphipod>, Pair<Int, Map<Coordinate, Amphipod>>>()
  while (queue.isNotEmpty()) {
    val (cost, current) = queue.poll()
    if (!seen.add(current)) {
      continue
    }
    if (current.isFinished()) {
      return cost
    }
    val options = current.findMovableCandidates()
    if (options.isEmpty()) {
      continue
    }
    options.flatMap { current.findMovesForCandidate(it) }
      .filter { (_, next) -> next !in seen }
      .map { (c, next) -> cost + c to next.also { transitionMap[next] = c to current.toMap() } }
      .toCollection(queue)
  }

  error("no path found")
}


fun Map<Coordinate, Amphipod>.closenessHash() =
  filter { it.value != Amphipod.EMPTY }
    .map {
      manhattanDistance(it.key, Coordinate(it.value.column, 4)) * manhattanDistance(
        it.key,
        Coordinate(it.value.column, 4)
      )
    }
    .sum()

fun Map<Coordinate, Amphipod>.isFinished() =
  filter { it.key.y != 0 }
    .all { it.key.x == it.value.column }


fun Map<Coordinate, Amphipod>.findMovesForCandidate(coordinate: Coordinate): List<Pair<Int, Map<Coordinate, Amphipod>>> {
  val target = get(coordinate)!!
  if (coordinate.y == 0) {
    return when (Amphipod.EMPTY) {
      get(Coordinate(target.column, 4)) ->
        listOf(
          manhattanDistance(coordinate, Coordinate(target.column, 4)) * target.energy to doMove(
            coordinate,
            Coordinate(target.column, 4)
          )
        )
      get(Coordinate(target.column, 3)) ->
        listOf(
          manhattanDistance(coordinate, Coordinate(target.column, 3)) * target.energy to doMove(
            coordinate,
            Coordinate(target.column, 3)
          )
        )
      get(Coordinate(target.column, 2)) ->
        listOf(
          manhattanDistance(coordinate, Coordinate(target.column, 2)) * target.energy to doMove(
            coordinate,
            Coordinate(target.column, 2)
          )
        )
      else ->
        listOf(
          manhattanDistance(coordinate, Coordinate(target.column, 1)) * target.energy to doMove(
            coordinate,
            Coordinate(target.column, 1)
          )
        )
    }
  }
  val options = mutableListOf<Pair<Int, Map<Coordinate, Amphipod>>>()
  // check if there is a direct route to target room
  val range = when {
    target.column > coordinate.x -> (coordinate.x..target.column)
    else -> (target.column..coordinate.x)
  }
  if (range.filter { it !in reservedSpots }.all { get(Coordinate(it, 0)) == Amphipod.EMPTY }) {

    if (
      get(Coordinate(target.column, 4)) == target
      && get(Coordinate(target.column, 3)) ==target
      && get(Coordinate(target.column, 2)) == target
      && get(Coordinate(target.column, 1)) == Amphipod.EMPTY
    ) {
      return listOf(
        (manhattanDistance(coordinate, Coordinate(target.column, 0)) + 1) * target.energy to
            doMove(coordinate, Coordinate(target.column, 1))
      )
    } else if (
      get(Coordinate(target.column, 4)) == target
      && get(Coordinate(target.column, 3)) ==target
      && get(Coordinate(target.column, 2)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 1)) == Amphipod.EMPTY
    ) {
      return listOf(
        (manhattanDistance(coordinate, Coordinate(target.column, 0)) + 2) * target.energy to
            doMove(coordinate, Coordinate(target.column, 2))
      )
    }else if (
      get(Coordinate(target.column, 4)) == target
      && get(Coordinate(target.column, 3)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 2)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 1)) == Amphipod.EMPTY
    ) {
      return listOf(
        (manhattanDistance(coordinate, Coordinate(target.column, 0)) + 3) * target.energy to
            doMove(coordinate, Coordinate(target.column, 3))
      )
    } else if (
      get(Coordinate(target.column, 4)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 3)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 2)) == Amphipod.EMPTY
      && get(Coordinate(target.column, 1)) == Amphipod.EMPTY
    ) {
      return listOf(
        (manhattanDistance(coordinate, Coordinate(target.column, 0)) + 4) * target.energy to
            doMove(coordinate, Coordinate(target.column, 4))
      )
    }
  }
  // else park directly in the hallway
  (coordinate.x - 1 downTo 1)
    .filter { it !in reservedSpots }
    .takeWhile { get(Coordinate(it, 0)) == Amphipod.EMPTY }
    .map {
      val destination = Coordinate(it, 0)
      manhattanDistance(coordinate, destination) * target.energy to doMove(coordinate, destination)
    }.toCollection(options)
  (coordinate.x + 1..11)
    .filter { it !in reservedSpots }
    .takeWhile { get(Coordinate(it, 0)) == Amphipod.EMPTY }
    .map {
      val destination = Coordinate(it, 0)
      manhattanDistance(coordinate, destination) * target.energy to doMove(coordinate, destination)
    }.toCollection(options)
  return options
}

fun Map<Coordinate, Amphipod>.doMove(from: Coordinate, to: Coordinate): Map<Coordinate, Amphipod> {
  val target = get(from)!!
  return toMutableMap().also {
    it[from] = Amphipod.EMPTY
    it[to] = target
  }
}

fun Map<Coordinate, Amphipod>.findMovableCandidates(): List<Coordinate> {
  val options = mutableListOf<Coordinate>()
  filter { it.key.y == 0 }
    .filter { it.value != Amphipod.EMPTY }
    .filter {
      pathToChamber(it) &&
          ((get(Coordinate(it.value.column, 1)) == Amphipod.EMPTY
              && get(Coordinate(it.value.column, 2)) == Amphipod.EMPTY
              && get(Coordinate(it.value.column, 3)) == Amphipod.EMPTY
              && get(Coordinate(it.value.column, 4)) == Amphipod.EMPTY) ||
              (get(Coordinate(it.value.column, 1)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 2)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 3)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 4)) == it.value) ||
              (get(Coordinate(it.value.column, 1)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 2)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 3)) == it.value
                  && get(Coordinate(it.value.column, 4)) == it.value) ||
              (get(Coordinate(it.value.column, 1)) == Amphipod.EMPTY
                  && get(Coordinate(it.value.column, 2)) == it.value
                  && get(Coordinate(it.value.column, 3)) == it.value
                  && get(Coordinate(it.value.column, 4)) == it.value))
    }.map { it.key }
    .toCollection(options)
  if (options.isNotEmpty()) {
    return options
  }
  filter { it.key.y == 4 }
    .filter { it.value != Amphipod.EMPTY }
    .filter { it.key.x != it.value.column }
    .filter { get(Coordinate(it.key.x, 3)) == Amphipod.EMPTY
        && get(Coordinate(it.key.x, 2)) == Amphipod.EMPTY
        && get(Coordinate(it.key.x, 1)) == Amphipod.EMPTY}
    .filter { get(Coordinate(it.key.x + 1, 0)) == Amphipod.EMPTY || get(Coordinate(it.key.x - 1, 0)) == Amphipod.EMPTY }
    .map { it.key }
    .toCollection(options)
  filter { it.key.y == 3 }
    .filter { it.value != Amphipod.EMPTY }
    .filter { get(Coordinate(it.key.x, 2)) == Amphipod.EMPTY   && get(Coordinate(it.key.x, 1)) == Amphipod.EMPTY}
    .filter { it.key.x != it.value.column || get(Coordinate(it.key.x, 4)) != it.value }
    .filter { get(Coordinate(it.key.x + 1, 0)) == Amphipod.EMPTY || get(Coordinate(it.key.x - 1, 0)) == Amphipod.EMPTY }
    .map { it.key }
    .toCollection(options)
  filter { it.key.y == 2 }
    .filter { it.value != Amphipod.EMPTY }
    .filter { get(Coordinate(it.key.x, 1)) == Amphipod.EMPTY }
    .filter {
      it.key.x != it.value.column || get(Coordinate(it.key.x, 3)) != it.value
          || get(Coordinate(it.key.x, 4)) != it.value
    }
    .filter {
      get(Coordinate(it.key.x + 1, 0)) == Amphipod.EMPTY || get(Coordinate(it.key.x - 1, 0)) == Amphipod.EMPTY
    }
    .map { it.key }
    .toCollection(options)
  filter { it.key.y == 1 }
    .filter { it.value != Amphipod.EMPTY }
    .filter {
      it.key.x != it.value.column || get(Coordinate(it.key.x, 2)) != it.value
          || get(Coordinate(it.key.x, 3)) != it.value || get(Coordinate(it.key.x, 4)) != it.value
    }
    .filter { get(Coordinate(it.key.x + 1, 0)) == Amphipod.EMPTY || get(Coordinate(it.key.x - 1, 0)) == Amphipod.EMPTY }
    .map { it.key }
    .toCollection(options)
  return options
}

val reservedSpots = setOf(3, 5, 7, 9)

fun Map<Coordinate, Amphipod>.pathToChamber(entry: Map.Entry<Coordinate, Amphipod>): Boolean {
  if (entry.value == Amphipod.EMPTY) error("This should not happen")
  if (entry.key.y != 0) error("This should not happen")
  val target = entry.value.column
  val current = entry.key.x
  val range = when {
    current > target -> (target until current)
    else -> (current + 1..target)
  }
  return range.filter { it !in reservedSpots }.map { x -> Coordinate(x, 0) }.all { get(it) == Amphipod.EMPTY }
}


fun BufferedReader.readInput(): Map<Coordinate, Amphipod> {
  val grid = mutableMapOf(
    Coordinate(1, 0) to Amphipod.EMPTY,
    Coordinate(2, 0) to Amphipod.EMPTY,
    Coordinate(4, 0) to Amphipod.EMPTY,
    Coordinate(6, 0) to Amphipod.EMPTY,
    Coordinate(8, 0) to Amphipod.EMPTY,
    Coordinate(10, 0) to Amphipod.EMPTY,
    Coordinate(11, 0) to Amphipod.EMPTY,
    Coordinate(3, 2) to Amphipod.D,
    Coordinate(5, 2) to Amphipod.C,
    Coordinate(7, 2) to Amphipod.B,
    Coordinate(9, 2) to Amphipod.A,
    Coordinate(3, 3) to Amphipod.D,
    Coordinate(5, 3) to Amphipod.B,
    Coordinate(7, 3) to Amphipod.A,
    Coordinate(9, 3) to Amphipod.C
  )
  readLine()
  readLine()
  var line = readLine()
  grid[Coordinate(3, 1)] = Amphipod.fromChar(line[3])
  grid[Coordinate(5, 1)] = Amphipod.fromChar(line[5])
  grid[Coordinate(7, 1)] = Amphipod.fromChar(line[7])
  grid[Coordinate(9, 1)] = Amphipod.fromChar(line[9])
  line = readLine()
  grid[Coordinate(3, 4)] = Amphipod.fromChar(line[3])
  grid[Coordinate(5, 4)] = Amphipod.fromChar(line[5])
  grid[Coordinate(7, 4)] = Amphipod.fromChar(line[7])
  grid[Coordinate(9, 4)] = Amphipod.fromChar(line[9])
  return grid
}

enum class Amphipod(val energy: Int, val column: Int) {
  EMPTY(0, -1), A(1, 3), B(10, 5), C(100, 7), D(1000, 9);

  companion object {
    fun fromChar(char: Char) = when (char) {
      'A' -> A
      'B' -> B
      'C' -> C
      'D' -> D
      else -> error("Invalid input")
    }
  }
}
