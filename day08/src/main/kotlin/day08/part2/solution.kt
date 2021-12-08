package day08.part2

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/21
 */
fun day08Part2(input: BufferedReader): Any {
  return input.lineSequence().map { line ->
      line.split("|").let { (code, result) ->
        code.split(" ").filter { it.isNotBlank() } to result.split(" ").filter { it.isNotBlank() }
      }
    }.sumOf { (description, numbers) -> Digit.decode(description).parseSequence(numbers) }
}

data class Digit(
  val top: Char = ' ',
  val middle: Char = ' ',
  val bottom: Char = ' ',
  val topRight: Char = ' ',
  val topLeft: Char = ' ',
  val bottomRight: Char = ' ',
  val bottomLeft: Char = ' '
) {
  companion object {
    fun decode(list: List<String>): Digit {
      val sizeCache = list.groupBy { it.length }
      val one = sizeCache[2]!!.first()
      val seven = sizeCache[3]!!.first()
      val four = sizeCache[4]!!.first()
      val eight = sizeCache[7]!!.first()
      var digit = Digit(top = seven.first { it !in one })
      val six = sizeCache[6]!!.first { d -> d.filter { it !in one }.length == 5 }
      val nine = sizeCache[6]!!.first { d -> four.all { it in d } }
      val zero = sizeCache[6]!!.first { it != six && it != nine }
      digit = digit.copy(topRight = eight.first { it !in six },
        bottomLeft = eight.first { it !in nine },
        bottomRight = one.first { it != digit.topRight },
        bottom = nine.first { it !in four && it != digit.top },
        topLeft = zero.first { it != digit.top && it != digit.bottom && it != digit.topRight && it != digit.bottomRight && it != digit.bottomLeft })
      return digit.copy(middle = four.first { it != digit.topLeft && it != digit.topRight && it != digit.bottomRight })
    }
  }

  fun parseSequence(numbers: List<CharSequence>) = numbers.fold(0) { acc, i -> acc * 10 + parseNumber(i) }


  private fun parseNumber(number: CharSequence): Int {
    return when (number.length) {
      2 -> 1
      3 -> 7
      4 -> 4
      5 -> when {
        topLeft in number && bottomRight in number -> 5
        topRight in number && bottomRight in number -> 3
        topRight in number && bottomLeft in number -> 2
        else -> error("invalid digit $number")
      }
      6 -> when {
        middle !in number -> 0
        bottomLeft !in number -> 9
        topRight !in number -> 6
        else -> error("invalid number $number")
      }
      7 -> 8
      else -> error("invalid digit $number")
    }
  }
}

