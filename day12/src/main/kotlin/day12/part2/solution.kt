package day12.part2

import day12.part1.isSmallCave
import day12.part1.readRouteMap
import linkedListOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 12/12/21
 */
fun day12Part2(input: BufferedReader): Any {
  val map = input.readRouteMap()
  val queue = linkedListOf(Triple("start", mutableMapOf("start" to 1), listOf("start")))
  val routes = mutableSetOf<List<String>>()
  while (queue.isNotEmpty()) {
    val (current, seen, route) = queue.poll()
    if (current !in map.keys) {
      continue
    }
    val options = map[current]!!.asSequence()
      .map { it to seen.toMutableMap().withDefault { 0 } }
      .filter { (node, _) -> node != "start" }
      .filter { (node, set) -> !node.isSmallCave() || set.getValue(node) == 0 || set.values.none { it == 2 } }
      .map { (node, set) ->
        Triple(
          node,
          set.also { if (node.isSmallCave()) set[node] = set.getValue(node) + 1 },
          route + listOf(node)
        )
      }.groupBy { it.first == "end" }
    options[true]?.map { it.third }?.toCollection(routes)
    options[false]?.toCollection(queue)
  }
  return routes.size
}
