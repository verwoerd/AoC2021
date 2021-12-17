package day17.part1

import Coordinate
import origin
import java.io.BufferedReader
import java.lang.Integer.max

/**
 * @author verwoerd
 * @since 17/12/21
 */
fun day17Part1(input: BufferedReader): Any {
  val (xRange, yRange) = input.parseInput()
  var top = Int.MIN_VALUE
  (yRange.last..-yRange.last).map { y ->
    (-xRange.last..xRange.last).map { x ->
      val trajectory = simulateTrajectory(x, y, xRange, yRange)
      if (trajectory.isNotEmpty() && trajectory.any { it.first.x in xRange && it.first.y in yRange }) {
        top = max(top, trajectory.maxOf { it.first.y })
      }
    }
  }
  return top
}


fun simulateTrajectory(x: Int, y: Int, xRange: IntRange, yRange: IntRange): List<Triple<Coordinate, Int, Int>> {
  return generateSequence(Triple(origin, x, y)) { (location, xVal, yVal) ->
    Triple(
      location.plusX(xVal).plusY(yVal), when (xVal) {
        0 -> 0
        in 1..Int.MAX_VALUE -> xVal - 1
        else -> xVal + 1
      }, yVal - 1
    )
  }.takeWhile { (c) -> c.y > yRange.first - 1 && c.x < xRange.last + 1 }
    .toList()
}

val REGEX = Regex("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)")

fun BufferedReader.parseInput() =
  REGEX.matchEntire(readLine())!!.groupValues.let { (_, xMin, xMax, yMin, yMax) -> (xMin.toInt()..xMax.toInt()) to (yMin.toInt()..yMax.toInt()) }
