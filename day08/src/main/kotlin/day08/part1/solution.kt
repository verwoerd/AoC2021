package day08.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 08/12/21
 */
fun day08Part1(input: BufferedReader): Any {
  return input.lineSequence()
    .sumOf { line ->
      line.split("|").last().split(" ").count { it.length in 2..4 || it.length == 7 }
    }
}
