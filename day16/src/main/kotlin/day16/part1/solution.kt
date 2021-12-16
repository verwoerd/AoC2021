package day16.part1

import toInt
import java.io.BufferedReader
import java.util.*

/**
 * @author verwoerd
 * @since 16/12/21
 */
fun day16Part1(input: BufferedReader): Any {
  val packages = input.readInput()
  val packet = packages.parsePacket()
  return packet.versionSum()
}

fun BufferedReader.readInput() = readLine().asSequence()
  .map {
    when (it) {
      in '0'..'9' -> it - '0'
      else -> it - 'A' + 10
    }
  }
  .flatMap {
    listOf(
      it and 0b1000 != 0,
      it and 0b0100 != 0,
      it and 0b0010 != 0,
      it and 0b0001 != 0
    )
  }.toCollection(LinkedList())

fun LinkedList<Boolean>.parsePackets(): List<Packet> {
  val packets = mutableListOf<Packet>()
  while (isNotEmpty() && !all { !it }) {
    packets.add(parsePacket())
  }
  return packets
}

fun LinkedList<Boolean>.parsePacket(): Packet {
  val version = takeLong(3)
  val type = takeLong(3)
  if (type == 4L) {
    var next: Long
    var value = 0L
    do {
      next = takeLong(5)
      value = (value shl 4) + (next and 0b1111)
    } while (next and 0b10000 != 0L)
    return Packet(version, type, value)
  } else {
    val lengtId = removeFirst()
    return if (!lengtId) {
      val length = takeLong(15)
      Packet(
        version, type, 0,
        take(length.toInt()).toCollection(LinkedList()).parsePackets()
      ).also { removeFirst(length.toInt()) }
    } else {
      val length = takeLong(11)
      val subPacket = mutableListOf<Packet>()
      repeat(length.toInt()) {
        subPacket.add(parsePacket())
      }
      Packet(version, type, 0, subPacket)
    }
  }
}

private fun LinkedList<Boolean>.takeLong(length: Int) =
  take(length).fold(0L) { acc, it -> (acc shl 1) + it.toInt() }
    .also { removeFirst(length) }

private fun LinkedList<Boolean>.removeFirst(length: Int) {
  repeat(length) { removeFirst() }
}

data class Packet(
  val version: Long,
  val id: Long,
  val value: Long,
  val subPackets: List<Packet> = emptyList()
) {
  fun versionSum(): Long = version + subPackets.sumOf { it.versionSum() }
  val realValue: Long by lazy { calculateValue() }


  private fun calculateValue(): Long {
    return when (id) {
      0L -> subPackets.sumOf { it.realValue }
      1L -> subPackets.fold(1L) { acc, packet -> acc * packet.realValue }
      2L -> subPackets.minOf { it.realValue }
      3L -> subPackets.maxOf { it.realValue }
      4L -> value
      5L -> (subPackets.first().realValue > subPackets.last().realValue).toInt().toLong()
      6L -> (subPackets.first().realValue < subPackets.last().realValue).toInt().toLong()
      7L -> (subPackets.first().realValue == subPackets.last().realValue).toInt().toLong()
      else -> error("invalud id code $id")
    }
  }
}
