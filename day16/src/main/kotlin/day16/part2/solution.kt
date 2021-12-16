package day16.part2

import day16.part1.parsePacket
import day16.part1.readInput
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 16/12/21
 */
fun day16Part2(input: BufferedReader): Any {
  val packages = input.readInput()
  val packet = packages.parsePacket()
  return packet.realValue
}
