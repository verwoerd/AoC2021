package day05.part2

import day05.part1.Line
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 05/12/21
 */
fun day05Part2(input: BufferedReader): Any {
  val lines = input.lineSequence().map { Line.parseLine(it) }.toList()
  val map = lines
    .flatMap { it.expandLines() }
    .groupBy { it }
  return map.count { it.value.size > 1 }
}
