package day01.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 01/12/2021
 */
fun day01Part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map { it.toLong() }
    .zipWithNext()
    .count { (a,b) -> b > a }
}
