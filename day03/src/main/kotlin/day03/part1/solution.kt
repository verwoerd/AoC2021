package day03.part1

import java.io.BufferedReader
import kotlin.math.log2

/**
 * @author verwoerd
 * @since 03/12/21
 */
fun day03Part1(input: BufferedReader): Any {
  val lines =  input.lineSequence().map {it.toLong(2)}.toList()
  var gamma = 0
  var epsilon = 0
  val max = log2(lines.maxOrNull()!!.toDouble()).toInt()
  (0..max).map { index->
    val ones = lines.map { (it shr index) and 1  }.count { it == 1L }
    when(ones > lines.size/2) {
      true ->  gamma = gamma or (1 shl index)
      else -> epsilon = epsilon or (1 shl index)
    }
  }
  return gamma * epsilon
}
