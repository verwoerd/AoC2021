package day01.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 01/12/2021
 */
fun day01Part2(input: BufferedReader): Any {
  return input.lineSequence()
    .map { it.toLong() }
    .windowed(3)
    .map { (a, b, c) -> a + b + c }
    .zipWithNext()
    .count { (a, b) -> b > a }
}
