package day04.part1

import Coordinate
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 04/12/21
 */
fun day04Part1(input: BufferedReader): Any {
  val (card, number) = input.playBingo()
    .first { (cards) -> cards.isNotEmpty() }

  return card.sumOf { it.unmarkedNumbers() * number }
}

fun BufferedReader.playBingo(): Sequence<Pair<List<BingoCard>, Int>> {
  val numbers = readLine().split(",").map { it.toInt() }
  val cards = lineSequence()
    .windowed(6, 6)
    .map { list ->
      BingoCard(list.drop(1)
        .map { line -> line.split(" ").mapNotNull { it.toIntOrNull() }.toIntArray() }
        .toTypedArray())
    }.toMutableList()

  return numbers.asSequence().map { number ->
    cards.filter { !it.hasBingo() }.filter { it.markNumber(number) }
      .filter { it.hasBingo() } to number
  }
}

data class BingoCard(
  val card: Array<IntArray>,
  val marked: Array<BooleanArray> = Array(5) { BooleanArray(5) },
  val transposed: Array<BooleanArray> = Array(5) { BooleanArray(5) }
) {
  private val numberMap: Map<Int, Coordinate> by lazy {
    card.flatMapIndexed { y, line ->
      line.mapIndexed { x, i -> i to Coordinate(x, y) }
    }.toMap()
  }

  fun markNumber(number: Int): Boolean {
    if (number in numberMap.keys) {
      val coordinate = numberMap[number]!!
      marked[coordinate.y][coordinate.x] = true
      transposed[coordinate.x][4 - coordinate.y] = true
      return true
    }
    return false
  }

  fun hasBingo() =
    marked.any { line -> line.all { it } } ||
        transposed.any { line -> line.all { it } }

  fun unmarkedNumbers() =
    marked.flatMapIndexed { y, line ->
      line.mapIndexed { x, mark ->
        when (mark) {
          false -> card[y][x]
          else -> null
        }
      }.filterNotNull()
    }.sum()

  override fun toString(): String {
    return card.mapIndexed { y, line ->
      line.mapIndexed { x, value ->
        when (marked[y][x]) {
          true -> "*${value.toString().padStart(2, ' ')}*"
          else -> " ${value.toString().padStart(2, ' ')} "
        }
      }.joinToString(" ")
    }.joinToString("\n", "\n")
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BingoCard) return false

    if (!card.contentDeepEquals(other.card)) return false
    if (!marked.contentDeepEquals(other.marked)) return false
    if (!transposed.contentDeepEquals(other.transposed)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = card.contentDeepHashCode()
    result = 31 * result + marked.contentDeepHashCode()
    result = 31 * result + transposed.contentDeepHashCode()
    return result
  }
}
