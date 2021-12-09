package day09.part1

import Coordinate
import adjacentCoordinates
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 09/12/21
 */
fun day09Part1(input: BufferedReader): Any {
  val heightMap = input.lineSequence().flatMapIndexed { y, s ->
    s.mapIndexed { x, c ->
      Coordinate(x, y) to (c - '0')
    }
  }.toMap().withDefault { 10 }
  return heightMap.filter { (coordinate, height) ->
    adjacentCoordinates(coordinate).all { heightMap.getValue(it) > height }
  }.map { it.value + 1 }.sum()
}
