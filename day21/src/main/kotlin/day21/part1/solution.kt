package day21.part1

import java.io.BufferedReader
import kotlin.math.min

/**
 * @author verwoerd
 * @since 21/12/21
 */
fun day21Part1(input: BufferedReader): Any {
  var (player1, player2) = input.readInput()
  player1--
  player2--
  var player1Score = 0
  var player2Score = 0
  var turn = 0
  while (player1Score < 1000 && player2Score < 1000) {
    if (turn % 2 == 1) {
      player2 = (player2 + deterministicDice()) % 10
      player2Score += player2 + 1
    } else {
      player1 = (player1 + deterministicDice()) % 10
      player1Score += player1 + 1
    }
    turn++
  }
  return min(player1Score, player2Score) * turn * 3
}

fun BufferedReader.readInput(): Pair<Int, Int> {
  return readLine().last() - '0' to readLine().last() - '0'
}

var dice = 0
fun deterministicDice(): Int {
  var result = 0
  repeat(3) {
    if (dice == 100) dice = 0
    result += ++dice
  }
  return result
}
