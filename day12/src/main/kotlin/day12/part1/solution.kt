package day12.part1

import linkedListOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/12/21
 */
fun day12Part1(input: BufferedReader): Any {
  val map = input.readRouteMap()
  val queue = linkedListOf(Triple("start", mutableSetOf("start"), listOf("start")))
  val routes = mutableSetOf<List<String>>()
  while (queue.isNotEmpty()) {
    val (current, seen, route) = queue.poll()
    if (current !in map.keys) {
      continue
    }
    val options = map[current]!!.asSequence()
      .map { it to seen.toMutableSet() }
      .filter { (node, set) -> !node.isSmallCave() || set.add(node) }
      .map { (node, set) -> Triple(node, set, route + listOf(node)) }
      .groupBy { it.first == "end" }
    options[true]?.map { it.third }?.toCollection(routes)
    options[false]?.toCollection(queue)
  }
  return routes.size
}


fun BufferedReader.readRouteMap() = lineSequence()
  .map { it.split("-").take(2).let { (a, b) -> a to b } }
  .flatMap { (a, b) -> listOf(a to b, b to a) }
  .groupBy { it.first }.mapValues { list -> list.value.map { it.second } }

fun String.isSmallCave() =
  this.all { it in 'a'..'z' }


