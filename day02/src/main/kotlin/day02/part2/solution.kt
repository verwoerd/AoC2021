package day02.part2

import Coordinate
import day02.part1.readRoute
import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 02/12/21
 */
fun day02Part2(input: BufferedReader): Any {
  return input.readRoute().fold(origin to 0) { (current, aim), (direction, amount) ->
      when (direction) {
        FourDirections.RIGHT -> current + Coordinate(amount, aim * amount)
        else -> current
      } to aim + when (direction) {
        FourDirections.UP -> amount
        FourDirections.DOWN -> -amount
        else -> 0
      }
    }.let { it.first.x * it.first.y }
}
