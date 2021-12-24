package day24.part1

import linkedListOf
import java.io.BufferedReader
import java.util.*
import kotlin.streams.toList

/**
 * @author verwoerd
 * @since 24/12/21
 */
fun day24Part1(input: BufferedReader): Any {
  // program groups is a repetition of blocks of 18 llines. Z is a constant state between them. 3 values change every block.
  val groups = input.parseProgramParameters()
  return groups.smartSearch(0, 0).maxOf { it.toLong() }
}

fun BufferedReader.parseProgramParameters() = lineSequence().windowed(18, step = 18)
  .map { Triple(it[4].drop(6).toLong(), it[5].drop(6).toLong(), it[15].drop(6).toLong()) }
  .toList().also {
    maxZValues = it.foldRight(mutableListOf<Long>()) { (z), acc ->
      when {
        acc.isEmpty() -> acc.add(z)
        else -> acc.add(z * acc.last())
      }
      acc
    }.reversed()
  }

// every block z gets divided by 1 or 26. So stop if z can never reach 0
lateinit var maxZValues: List<Long>

val cache = mutableMapOf<Pair<Int, Long>, List<String>>()

fun List<Triple<Long, Long, Long>>.smartSearch(index: Int, z: Long): List<String> {
  if (index to z in cache) {
    return cache[index to z]!!
  }
  if (index == 14) {
    return when (z) {
      0L -> listOf("").also { cache[index to 0] = it }
      else -> emptyList<String>().also { cache[index to z] = it }
    }
  }
  if (z > maxZValues[index]) return emptyList()
  val (zDiv: Long, xAdd: Long, yAdd: Long) = get(index)
  return (1..9).flatMap { w ->
    val nextZ = checkDigit(w.toLong(), z, xAdd, zDiv, yAdd)
    smartSearch(index + 1, nextZ).map { "$w$it" }
  }.also { cache[index to z] = it }
}

fun checkDigit(w: Long, zVal: Long, xAdd: Long, zDiv: Long, yAdd: Long): Long {
  val x = xAdd + zVal.mod(26).toLong()
  var z = zVal / zDiv
  if (x != w) {
    z = z * 26 + w + yAdd
  }
  return z
}

// BF does not work :(
fun day24Part1Old(input: BufferedReader): Any {
  val program = input.lines().map { Instruction.fromString(it) }.toList()
  println(program.joinToString("\n"))
  val alu = ALU(program)
  (9 downTo 1).map { x1 ->
    (9 downTo 1).map { x2 ->
      (9 downTo 1).map { x3 ->
        (9 downTo 1).map { x4 ->
          (9 downTo 1).map { x5 ->
            (9 downTo 1).map { x6 ->
              (9 downTo 1).map { x7 ->
                (9 downTo 1).map { x8 ->
                  (9 downTo 1).map { x9 ->
                    (9 downTo 1).map { x10 ->
                      (9 downTo 1).map { x11 ->
                        (9 downTo 1).map { x12 ->
                          (9 downTo 1).map { x13 ->
                            (9 downTo 1).map { x14 ->
                              val inp = linkedListOf(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14)
//                              if (x10 == 9 && x11 == 9 && x12 == 9 && x13 == 8 && x14 == 8) {
//                                println("debug")
//                              }
                              val (success, registers) = alu.executeProgram(inp.toCollection(LinkedList()))

                              if (success) println("valid: " + inp.joinToString(""))

                              if (success && registers.z == 0L) {
                                println(registers)
                                return inp.joinToString("")
                              }
//                              println(registers)
//                              if (x10 == 9 && x11 == 9 && x12 == 9 && x13 == 8 && x14 == 8) {
//                                println(alu.jumpMap)
//                                return "stop"
//                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  error("no result found")
}

data class ALU(
  val program: List<Instruction>,
  val jumpMap: MutableMap<Pair<Int, List<Int>>, Pair<Int, Registers>> = mutableMapOf()
) {
  fun executeProgram(input: LinkedList<Int>, registers: Registers = Registers()): Pair<Boolean, Registers> {
    var r = registers
    val read = mutableListOf<Int>()
    var pc = 0
    var startPc = 0

    while (pc < program.size) {
      val it = program[pc]
      when (it.operand) {
        Operand.INP -> {
          if (pc != 0 && startPc to read !in jumpMap) {
            jumpMap[startPc to read.toList()] = pc to r.copy()
          }
          val new = input.removeFirst()
          read.add(new)
          if (pc to read in jumpMap) {
            // this is broken somehow :-(
            val (newPc, reg) = jumpMap[pc to read]!!
            startPc = pc
            pc = newPc
            r = reg.copy()
            continue
          }
          startPc = pc
          r.setValue(it.left, new.toLong())

        }
        Operand.ADD -> r.setValue(it.left, r.getValue(it.left) + r.getValue(it.right))
        Operand.MUL -> r.setValue(it.left, r.getValue(it.left) * r.getValue(it.right))
        Operand.DIV -> r.setValue(
          it.left,
          r.getValue(it.left) / (r.getValue(it.right).takeIf { b -> b > 0 } ?: return false to r))
        Operand.MOD -> r.setValue(
          it.left,
          (r.getValue(it.left).takeIf { a -> a >= 0 } ?: return false to r).mod(
            r.getValue(it.right).takeIf { b -> b > 0 }
              ?: return false to r))
        Operand.EQL -> r.setValue(it.left, if (r.getValue(it.left) == r.getValue(it.right)) 1L else 0L)
      }
      pc++
    }
    return true to r
  }


}

data class Registers(
  var w: Long = 0,
  var x: Long = 0,
  var y: Long = 0,
  var z: Long = 0
) {
  fun setValue(register: Char, value: Long) {
    when (register) {
      'w' -> w = value
      'x' -> x = value
      'y' -> y = value
      'z' -> z = value
      else -> error("invalid register $register")
    }
  }

  fun getValue(from: Char) = when (from) {
    'w' -> w
    'x' -> x
    'y' -> y
    'z' -> z
    else -> error("invalid register $from")
  }

  fun getValue(from: String): Long {
    return when (from) {
      "w" -> w
      "x" -> x
      "y" -> y
      "z" -> z
      else -> from.toLong()
    }
  }
}

enum class Operand() {
  INP, ADD, MUL, DIV, MOD, EQL;
}

data class Instruction(
  val operand: Operand,
  val left: Char,
  val right: String = ""
) {
  companion object {
    fun fromString(line: String): Instruction {
      return when {
        line.startsWith("inp") -> Instruction(Operand.INP, line[4])
        else -> Instruction(Operand.valueOf(line.take(3).uppercase()), line[4], line.drop(6))
      }
    }
  }
}



