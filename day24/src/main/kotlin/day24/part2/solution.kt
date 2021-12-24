package day24.part2

import day24.part1.parseProgramParameters
import day24.part1.smartSearch
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 24/12/21
 */
fun day24Part2(input: BufferedReader): Any {
  val groups = input.parseProgramParameters()
  return groups.smartSearch(0, 0).minOf { it.toLong() }
}
