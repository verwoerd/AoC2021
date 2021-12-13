package day13.part1

import Coordinate
import java.io.BufferedReader
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 13/12/21
 */
fun day13Part1(input: BufferedReader): Any {
  val (coordinates, instructions) = input.readInput()
  return coordinates.doFold(instructions.first()).count()
}

fun Set<Coordinate>.doFold(instruction: Pair<String, Int>) =
  map { it.fold(instruction) }.toSet()

fun Coordinate.fold(instruction: Pair<String, Int>): Coordinate {
  val (direction, i) = instruction
  return when (direction) {
    "x" -> when (x) {
      in 0..i -> this
      else -> Coordinate(2 * i - x, y)
    }
    "y" -> when (y) {
      in 0..i -> this
      else -> Coordinate(x, 2 * i - y)
    }
    else -> error("Unknown fold $direction=$i")
  }
}

fun BufferedReader.readInput(): Pair<Set<Coordinate>, List<Pair<String, Int>>> {
  val lines = lines().toList()
  val coordinates = lines.takeWhile { it.isNotBlank() }
    .map { line -> line.split(",").map { it.toInt() }.let { (x, y) -> Coordinate(x, y) } }
    .toSet()
  val instructions = lines.dropWhile { it.isNotBlank() }.drop(1)
    .map { it.drop(11).split('=').let { (d, i) -> d to i.toInt() } }
  return coordinates to instructions
}
