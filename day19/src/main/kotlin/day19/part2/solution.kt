package day19.part2

import day19.part1.findScannerMap
import day19.part1.readInput
import manhattanDistance
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 19/12/21
 */
fun day19Part2(input: BufferedReader): Any {
  val scanners = input.readInput()
  val (_, placedScanners) = findScannerMap(scanners)
  return placedScanners.keys.maxOf { left ->
    placedScanners.keys.maxOf { manhattanDistance(left, it) }
  }
}
