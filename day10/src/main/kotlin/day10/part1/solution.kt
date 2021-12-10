package day10.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 10/12/21
 */
fun day10Part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map{parseLine(it) }
    .filter { it.first != ' ' }
    .sumOf {(char)-> when(char){
      ')' -> 3L
      ']' -> 57L
      '}' -> 1197L
      '>' -> 25137L
      else -> error("invalid char $char")
    } }
}

fun parseLine(line: String): Pair<Char, MutableList<Char>> {
  val stack = mutableListOf<Char>()
  var illegalChar = ' '
  val left = line.fold(stack) { acc, c ->
    if(illegalChar !=  ' ') {
      return@fold acc
    }
    when(c) {
      '{','[','(','<' -> acc.add(c)
      '}' -> when(acc.last()) {
        '{' -> acc.removeLast()
        else -> illegalChar = c
      }
      '>' -> when(acc.last()) {
        '<' -> acc.removeLast()
        else -> illegalChar = c
      }
      ']' -> when(acc.last()) {
        '[' -> acc.removeLast()
        else -> illegalChar = c
      }
      ')' -> when(acc.last()) {
        '(' -> acc.removeLast()
        else -> illegalChar = c
      }
      else -> error("illegal char in sequence $c")
    }
    acc
  }
  return  illegalChar to left
}
