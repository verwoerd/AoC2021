package day15.part2

import Coordinate
import day15.part1.dijkstra
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 15/12/21
 */
fun day15Part2(input: BufferedReader): Any {
  val original = input.lineSequence().flatMapIndexed { y, line ->
    line.mapIndexed { x, c -> Coordinate(x, y) to c - '0' }
  }.toMap()
  val maze = original.toMutableMap().withDefault { 10 }
  val xRange = maze.xRange().second + 1
  val yRange = maze.yRange().second + 1
  repeat(5) { yFactor ->
    repeat(5) { xFactor ->
      if (xFactor != 0 || yFactor != 0) {
        original.map { (key, value) ->
          Coordinate(xFactor * xRange + key.x, yFactor * yRange + key.y) to when (val newValue =
            value + xFactor + yFactor) {
            in 1..9 -> newValue
            else -> newValue - 10 + 1
          }
        }.toMap(maze)
      }
    }
  }
  return maze.dijkstra()
}
