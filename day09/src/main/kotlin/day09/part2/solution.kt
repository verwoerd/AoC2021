package day09.part2

import Coordinate
import adjacentCoordinates
import linkedListOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/21
 */
fun day09Part2(input: BufferedReader): Any {
  val heightMap = input.lineSequence().flatMapIndexed { y, s ->
    s.mapIndexed { x, c ->
      Coordinate(x, y) to (c - '0')
    }
  }.toMap().withDefault { 10 }
  return heightMap.filter { (coordinate, height) ->
    adjacentCoordinates(coordinate).all { heightMap.getValue(it) > height }
  }.map { (coordinate, height) ->
    val seen = mutableSetOf(coordinate)
    val queue = linkedListOf(coordinate to height)
    while (queue.isNotEmpty()) {
      val (current, depth) = queue.poll()
      if (depth + 1 == 9) {
        continue
      }
      adjacentCoordinates(current).filter { heightMap.getValue(it) > depth }
        .map { it to heightMap.getValue(it) }
        .filter { it.second < 9 }
        .filter { seen.add(it.first) }
        .toCollection(queue)
    }
    seen.size
  }.sortedDescending().take(3).fold(1) { acc, i -> acc * i }
}
