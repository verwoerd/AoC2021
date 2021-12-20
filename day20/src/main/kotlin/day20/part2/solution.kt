package day20.part2

import day20.part1.enhance
import day20.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 20/12/21
 */
fun day20Part2(input: BufferedReader): Any {
  val (diffMap, scan) = input.readInput()
  return enhance(scan, 50, diffMap)
}
