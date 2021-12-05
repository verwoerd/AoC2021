package day05.part1

import Coordinate
import java.io.BufferedReader
import java.lang.Integer.max
import java.lang.Integer.min

/**
 * @author verwoerd
 * @since 05/12/21
 */
fun day05Part1(input: BufferedReader): Any {
  val lines = input.lineSequence().map { Line.parseLine(it) }.toList()
  val map = lines.filter { it.isHorizontal() xor it.isVertical() }.flatMap { it.expandLines() }.groupBy { it }
  return map.count { it.value.size > 1 }
}

data class Line(
  val start: Coordinate,
  val end: Coordinate,
) {
  companion object {
    private val REGEX = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")
    fun parseLine(line: String) = REGEX.matchEntire(line)!!.destructured.let { (x1, y1, x2, y2) ->
      Line(Coordinate(x1.toInt(), y1.toInt()), Coordinate(x2.toInt(), y2.toInt()))
    }
  }

  fun isHorizontal() = start.y == end.y

  fun isVertical() = start.x == end.x

  fun expandLines(): List<Coordinate> {
    if (isHorizontal() xor isVertical()) {
      return (min(start.x, end.x)..max(start.x, end.x)).flatMap { x ->
        (min(start.y, end.y)..max(start.y, end.y)).map { y ->
          Coordinate(x, y)
        }
      }
    } else {
      val xRange = when (start.x > end.x) {
        true -> start.x downTo end.x
        else -> start.x..end.x
      }
      val yRange = when (start.y > end.y) {
        true -> start.y downTo end.y
        else -> start.y..end.y
      }
      return xRange.zip(yRange).map { (x, y) -> Coordinate(x, y) }
    }
  }

}
