package day21.part2

import day21.part1.readInput
import priorityQueueOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 21/12/21
 */
fun day21Part2(input: BufferedReader): Any {
  val (player1, player2) = input.readInput()
  val startState = Triple(0, Player(1, player1 - 1), Player(2, player2 - 1))
  val unresolved = mutableMapOf(startState to 1L).withDefault { 0L }
  val queue = priorityQueueOf(Comparator { o1, o2 ->
    when (val diff = o1.second.score.compareTo(o2.second.score)) {
      0 -> o1.third.score.compareTo(o2.third.score)
      else -> diff
    }
  }, startState)
  val seen = mutableSetOf(startState)
  val playerWins = mutableListOf(0L, 0L)
  while (queue.isNotEmpty()) {
    val key = queue.poll()
    val occurrences = unresolved[key]!!
    val (turn, p1, p2) = key
    val player = when (turn) {
      0 -> p1
      else -> p2
    }
    frequencyList.forEach { (dice, times) ->
      val next = player.move(dice)
      if (next.score >= 21) {
        playerWins[turn] = playerWins[turn] + times * occurrences
        return@forEach
      }
      val newKey = when (turn) {
        0 -> Triple(1, next, p2)
        else -> Triple(0, p1, next)
      }
      unresolved[newKey] = unresolved.getValue(newKey) + occurrences * times
      if (seen.add(newKey)) {
        queue.add(newKey)
      }
    }

  }
  return playerWins.maxOrNull()!!
}

val frequencyList = listOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

data class Player(
  val id: Int,
  val location: Int,
  val score: Int = 0
) {
  fun move(steps: Int): Player {
    val newLocation = (location + steps) % 10
    val newScore = score + newLocation + 1
    return copy(location = newLocation, score = newScore)
  }
}
