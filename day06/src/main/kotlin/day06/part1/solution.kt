package day06.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06/12/21
 */
fun day06Part1(input: BufferedReader, target: Int = 80): Any {
  var fishes = input.readLine().split(",").map { it.toInt() }
  repeat(target) {
    var count = 0
    fishes = fishes.map {
      when (it - 1) {
        -1 -> 6.also { count++ }
        else -> it - 1
      }
    } + (1..count).map { 8 }.toList()
  }
  return fishes.size
}
