package day17.part2

import day17.part1.parseInput
import day17.part1.simulateTrajectory
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 17/12/21
 */
fun day17Part2(input: BufferedReader): Any {
  val (xRange, yRange) = input.parseInput()
  return  (yRange.last..-yRange.last).flatMap { y ->
    (-xRange.last..xRange.last).map { x ->
      simulateTrajectory(x, y, xRange, yRange)
     }.filter { it.isNotEmpty() }
      .filter { trajectory ->
        trajectory.any { it.first.x in xRange && it.first.y in yRange }
      }
  }.size
}
