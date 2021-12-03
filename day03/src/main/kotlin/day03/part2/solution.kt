package day03.part2

import java.io.BufferedReader
import kotlin.math.log2

/**
 * @author verwoerd
 * @since 03/12/21
 */
fun day03Part2(input: BufferedReader): Any {
  val lines = input.lineSequence()
    .map { it.toLong(2) }.toList()
  val max = log2(lines.maxOrNull()!!.toDouble()).toInt()
  var oxygenSet = lines.toSet()
  var index = max
  while (oxygenSet.size > 1) {
    val ones = oxygenSet.filter { (it shr index) and 1 == 1L }.toSet()
    oxygenSet = when (2*ones.size >= oxygenSet.size ) {
      true -> ones
      else -> oxygenSet - ones
    }
    index--
  }
  var co2Set = lines.toSet()
  index = max
  while (co2Set.size > 1) {
    val ones = co2Set.filter { (it shr index) and 1  == 1L }.toSet()
    co2Set = when (2*ones.size >= co2Set.size) {
      true -> co2Set - ones
      else -> ones
    }
    index--
  }
  return oxygenSet.first() * co2Set.first()
}
