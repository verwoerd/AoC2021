package day07.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 07/12/21
 */
fun day07Part2(input: BufferedReader): Any {
  val crabs  = input.readLine().split(",").map{it.toInt()}
  val min  = crabs.minOrNull()?: error{"no min"}
  val max = crabs.maxOrNull()?:error("no max")
  return (min..max).minOfOrNull { pos->
    crabs.fold(0) { acc, i ->
     val steps = when (i) {
        in (0..pos) -> pos - i
        else -> i - pos
      }
      acc + (steps*(steps+1))/2
    }
  } ?:error("please replace user")
}
