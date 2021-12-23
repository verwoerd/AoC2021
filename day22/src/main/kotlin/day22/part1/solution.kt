package day22.part1

import TripleCoordinate
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 22/12/21
 */
fun day22Part1(input: BufferedReader): Any {
  val steps = input.readInput()
  val grid = steps
    .filter { it.xRange.last >= -50 && it.xRange.first <= 50 }
    .filter { it.yRange.last >= -50 && it.yRange.first <= 50 }
    .filter { it.zRange.last >= -50 && it.zRange.first <= 50 }
    .fold(mutableSetOf<TripleCoordinate>()) { acc, step ->
      step.xRange.map { x ->
        step.yRange.map { y ->
          step.zRange.map { z ->
            when (step.state) {
              true -> acc.add(TripleCoordinate(x.toLong(), y.toLong(), z.toLong()))
              else -> acc.remove(TripleCoordinate(x.toLong(), y.toLong(), z.toLong()))
            }
          }
        }
      }
      acc
    }
  return grid.size
}


fun BufferedReader.readInput() =
  lineSequence().map { RebootStep.fromLine(it) }.toList()


data class RebootStep(
  val state: Boolean,
  val xRange: IntRange,
  val yRange: IntRange,
  val zRange: IntRange
) {
  companion object {
    private val REGEX = Regex("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)")

    fun fromLine(line: String): RebootStep {
      val (state, xMin, xMax, yMin, yMax, zMin, zMax) = REGEX.matchEntire(line)!!.destructured
      return RebootStep(
        when (state) {
          "on" -> true
          else -> false
        }, xMin.toInt()..xMax.toInt(),
        yMin.toInt()..yMax.toInt(),
        zMin.toInt()..zMax.toInt()
      )
    }
  }
}
