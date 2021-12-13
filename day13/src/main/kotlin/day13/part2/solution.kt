package day13.part2

import Coordinate
import day13.part1.doFold
import day13.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 13/12/21
 */
fun day13Part2(input: BufferedReader): Any {
  val (coordinates, instructions) = input.readInput()
  val result = instructions.fold(coordinates) {acc, i -> acc.doFold(i)}
  return (0..6).joinToString("\n") { y ->
    (0..40).joinToString("") { x ->
        if (Coordinate(x,y) in result) "#" else "."
    }
  }
}
