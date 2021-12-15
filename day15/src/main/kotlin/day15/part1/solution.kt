package day15.part1

import Coordinate
import adjacentCoordinates
import origin
import priorityQueueOf
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/12/21
 */
fun day15Part1(input: BufferedReader): Any {
  val maze = input.lineSequence().flatMapIndexed { y, line ->
    line.mapIndexed { x, c -> Coordinate(x, y) to c - '0' }
  }.toMap().withDefault { 10 }
  return maze.dijkstra()
}

fun Map<Coordinate, Int>.dijkstra(): Int {
  val queue = priorityQueueOf(comparator = { a, b -> a.first.compareTo(b.first) }, 0 to origin)
  val seen = mutableSetOf(origin)
  val target = Coordinate(xRange().second, yRange().second)
  while (queue.isNotEmpty()) {
    val (distance, current) = queue.poll()
    if (current == target) {
      return distance
    }
    adjacentCoordinates(current)
      .filter { seen.add(it) }
      .map { distance + getValue(it) to it }
      .toCollection(queue)
  }
  error("Error replace user")
}
