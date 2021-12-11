package day11.part1

import Coordinate
import adjacentCircularCoordinates
import linkedListOf
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 11/12/21
 */
fun day11Part1(input: BufferedReader): Any {
  val startState = input.lineSequence()
    .flatMapIndexed { y, line ->
      line.mapIndexed { x, c ->
        Coordinate(x, y) to c - '0'
      }
    }.toMap()
  return executeFlashes(startState).take(101).sumOf { value -> value.values.count { it == 0 } }
}

fun executeFlashes(startState: Map<Coordinate, Int>): Sequence<MutableMap<Coordinate, Int>> {
  return generateSequence (startState.toMutableMap()) { currentState ->
    val queue = linkedListOf<Coordinate>()
    val state = currentState.map { (location, value) ->
      location to when (val newValue = value + 1) {
        10 -> 0.also { queue.add(location) }
        else -> newValue
      }
    }.toMap().toMutableMap()
    while (queue.isNotEmpty()) {
      val current = queue.pop()
      adjacentCircularCoordinates(current)
        .filter { it in state.keys }
        .forEach { coordinate ->
          state[coordinate] = when (val newValue = state[coordinate]!! + 1) {
            10 -> 0.also { queue.add(coordinate) }
            1 -> 0
            else -> newValue
          }
        }
    }
    state
  }
}
