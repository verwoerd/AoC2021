package day06.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/21
 */
fun day06Part2(input: BufferedReader, target: Int = 256): Any {
  val fishes = input.readLine().split(",").map { it.toInt() }
  var countMap = mutableMapOf<Int, Long>().withDefault { 0L }
  fishes.forEach { countMap[it] = countMap.getValue(it) + 1 }
  repeat(target) { _ ->
    val newMap = mutableMapOf<Int, Long>().withDefault { 0L }
    (0..8).map {
      newMap[it] = countMap.getValue((it + 1) % 9)
    }
    newMap[6] = newMap.getValue(6) + countMap.getValue(0)
    countMap = newMap
  }
  return countMap.values.sum()
}

