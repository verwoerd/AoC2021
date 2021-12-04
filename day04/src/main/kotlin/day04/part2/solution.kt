package day04.part2

import day04.part1.playBingo
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/21
 */
fun day04Part2(input: BufferedReader): Any {
  val (card, number) = input.playBingo().last{(cards)->cards.isNotEmpty()}
  return card.sumOf { it.unmarkedNumbers() * number }
}
