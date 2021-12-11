package day11.part2

import Coordinate
import day11.part1.executeFlashes
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/12/21
 */
fun day11Part2(input: BufferedReader): Any {
  val startState = input.lineSequence()
  .flatMapIndexed { y, line ->
    line.mapIndexed { x, c ->
      Coordinate(x, y) to c - '0'
    }
  }.toMap()
  return executeFlashes(startState)
    .mapIndexed {index, state -> index to state}
    .first { (_, state) -> state.values.all { it == 0 }}.first
}
