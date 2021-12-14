package day14.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 14/12/21
 */
fun day14Part2(input: BufferedReader): Any {
  val template = input.readLine().toList()
  input.readLine()
  val translation = input.lineSequence().map {
    it.split(" -> ").let { (a, b) ->
      a.toList() to listOf(listOf(a.first(), b.first()), listOf(b.first(), a.last()))
    }
  }.toMap()
  var current = template.windowed(2).associateWith { it }.mapValues { it.value.size.toLong() / 2 }
  repeat(40) {
    val next = mutableMapOf<List<Char>, Long>().withDefault { 0L }
    current.forEach { (key, count) ->
      translation[key]!!.forEach { newKey ->
        next[newKey] = next.getValue(newKey) + count
      }
    }
    current = next
  }
  val counts = current.keys.flatten().distinct()
    .associateWith { c -> current.keys.filter { it.first() == c }.sumOf { current.getValue(it) } }.toMutableMap()
  counts[template.last()] = counts[template.last()]!! + 1
  return counts.maxOf { it.value } - counts.minOf { it.value }
}
