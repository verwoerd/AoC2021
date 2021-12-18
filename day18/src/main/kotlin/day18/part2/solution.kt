package day18.part2


import day18.part1.TreePair
import day18.part1.parseTree
import day18.part1.plus
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/12/21
 */
fun day18Part2(input: BufferedReader): Any {
  val pairs = input.lineSequence()
    .map(::parseTree)
    .toList()
  return pairs.flatMap { base ->
    pairs.filter { it != base }
      .map { (base.copyNode() as TreePair) + it.copyNode() as TreePair }
  }.maxOf { it.magnitude() }
}
