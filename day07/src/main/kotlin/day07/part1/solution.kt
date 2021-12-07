package day07.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/21
 */
fun day07Part1(input: BufferedReader): Any {
  val crabs = input.readLine().split(",").map { it.toInt() }
  val min = crabs.minOrNull() ?: error { "no min" }
  val max = crabs.maxOrNull() ?: error("no max")
  return (min..max).minOfOrNull {
    crabs.fold(0) { acc, i ->
      acc + when (i) {
        in (0..it) -> it - i
        else -> i - it
      }
    }
  } ?: error("please replace user")
}
