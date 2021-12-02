package day02.part1

import origin
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 02/12/21
 */
fun day02Part1(input: BufferedReader): Any {
  return input.readRoute().fold(origin) { current, (direction, amount) ->
      current + (direction.direction * amount)
    }.let { it.x * it.y }
}

fun BufferedReader.readRoute() = lineSequence()
  .map {
    it.split(" ")
      .let { (direction, amount) ->
        when (direction) {
          "forward" -> FourDirections.RIGHT
          "up" -> FourDirections.DOWN
          "down" -> FourDirections.UP
          else -> error("can't parse direction $direction")
        } to amount.toInt()
      }
  }
