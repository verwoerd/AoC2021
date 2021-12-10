package day10.part2

import day10.part1.parseLine
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/21
 */
fun day10Part2(input: BufferedReader): Any {
  val scores = input.lineSequence()
    .map { parseLine(it) }
    .filter { it.first == ' ' }
    .map { (_, left) ->
      left.foldRight(0L) { c, acc ->
        5 * acc + when (c) {
          '(' -> 1
          '[' -> 2
          '{' -> 3
          '<' -> 4
          else -> error("invalid char $c")
        }
      }
    }.sorted().toList()
  return scores[scores.size / 2]
}
