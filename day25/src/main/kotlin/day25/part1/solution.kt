package day25.part1

import Coordinate
import FourDirections
import xRange
import yRange
import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 25/12/21
 */
fun day25Part1(input: BufferedReader): Any {
  val grid = input.readInput().toMutableMap()
  yMax = grid.yRange().second + 1
  xMax = grid.xRange().second + 1
  var i = 1
  while (grid.simulate()) {
    i++
  }
  return i
}

var yMax: Int = 0
var xMax: Int = 0

fun MutableMap<Coordinate, FourDirections>.simulate(): Boolean {
  var changed = filter { it.value == FourDirections.RIGHT }
    .filter { get(Coordinate((it.key.x + 1) % xMax, it.key.y)) == null }
    .count { (t) ->
      set(Coordinate((t.x + 1) % xMax, t.y), FourDirections.RIGHT)
      remove(t) != null
    }
  changed += filter { it.value == FourDirections.DOWN }
    .filter { get(Coordinate(it.key.x, (it.key.y + 1) % yMax)) == null }
    .count { (t) ->
      set(Coordinate(t.x, (t.y + 1) % yMax), FourDirections.DOWN)
      remove(t) != null
    }
  return changed > 0
}


fun BufferedReader.readInput() =
  lineSequence().flatMapIndexed { y, line ->
    line.mapIndexed { x, c ->
      if (c == '.')
        null
      else
        Coordinate(x, y) to when (c) {
          '>' -> FourDirections.RIGHT
          'v' -> FourDirections.DOWN
          else -> error("invalid input")
        }
    }.filterNotNull()
  }.toMap()
