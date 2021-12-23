package day22.part2

import TripleCoordinate
import day22.part1.RebootStep
import day22.part1.readInput
import java.io.BufferedReader
import java.lang.Long.max
import java.lang.Long.min

/**
 * @author verwoerd
 * @since 22/12/21
 */
fun day22Part2(input: BufferedReader): Any {
  val steps = input.readInput()

  val grid = steps
    .fold(mutableMapOf<TripleCoordinateRange, Long>().withDefault { 0L }) { acc, step ->
      val range = step.toRange()
      val next = acc.toMutableMap().withDefault { 0L }
      acc.forEach { (c, v) ->
        val overlap = TripleCoordinate(
          max(range.start.x, c.start.x),
          max(range.start.y, c.start.y),
          max(range.start.z, c.start.z)
        )..TripleCoordinate(min(range.end.x, c.end.x), min(range.end.y, c.end.y), min(range.end.z, c.end.z))
        if (overlap.isValid()) {
          next[overlap] = next.getValue(overlap) - v
          if (next.getValue(overlap) == 0L) {
            next.remove(overlap)
          }
        }
      }
      if (step.state) {
        next[range] = next.getValue(range) + 1
      }
      next
    }
  return grid.map { (k, v) -> k.totalElements() * v }.sum()
}

fun RebootStep.toRange() =
  TripleCoordinate(xRange.first, yRange.first, zRange.first)..TripleCoordinate(xRange.last, yRange.last, zRange.last)


operator fun TripleCoordinate.rangeTo(other: TripleCoordinate) = TripleCoordinateRange(this, other)

data class TripleCoordinateRange(
  val start: TripleCoordinate,
  val end: TripleCoordinate
) {
  operator fun minus(value: TripleCoordinateRange): Long {
    return when {
      value.start in this && value.end in this -> -totalElements()
      value.start in this -> -(value.start..end).totalElements()
      value.end in this -> -(start..value.end).totalElements()
      else -> 0
    }
  }

  operator fun contains(value: TripleCoordinate): Boolean =
    value.x >= start.x && value.y >= start.y && value.z >= start.z &&
        value.x <= end.x && value.y <= end.y && value.z <= end.z

  fun totalElements() =
    (end.x - start.x + 1) * (end.y - start.y + 1) * (end.z - start.z + 1)

  fun isValid() =
    start.x <= end.x && start.y <= end.y && start.z <= end.z
}
