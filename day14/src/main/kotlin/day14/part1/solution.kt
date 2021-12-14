package day14.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 14/12/21
 */
fun day14Part1(input: BufferedReader): Any {
  val template = input.readLine().toList()
  input.readLine()
  val translation = input.lineSequence().map { it.split(" -> ").let { (a, b) -> a.toList() to b.first() } }.toMap()
  var current = template
  repeat(10) {
    current = current.windowed(2, partialWindows = true)
      .flatMap {
        when (it.size) {
          1 -> it
          else -> listOfNotNull(it.first(), translation[it])
        }
      }
      .toList()
  }
  val counts = current.distinct().map { c -> current.count { it == c } }
  return counts.maxOrNull()!! - counts.minOrNull()!!
}
