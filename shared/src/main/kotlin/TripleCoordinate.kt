import kotlin.math.abs

/*
 * Copyright (c) 2021 Keystone Strategic b.v.
 *
 * All rights reserved.
 */

/**
 * @author verwoerd
 * @since 25-11-21
 */
data class TripleCoordinate(val x: Long, val y: Long, val z: Long) {
  constructor(xVal: Int, yVal: Int, zVal: Int) : this(xVal.toLong(), yVal.toLong(), zVal.toLong())
  operator fun minus(other: TripleCoordinate) = TripleCoordinate(x - other.x, y - other.y, z - other.z)
  operator fun plus(other: TripleCoordinate) = TripleCoordinate(x + other.x, y + other.y, z + other.z)
  operator fun plus(i: Number) = plus(TripleCoordinate(i.toLong(), i.toLong(), i.toLong()))
  operator fun times(i: Number) = this * TripleCoordinate(i.toLong(), i.toLong(), i.toLong())
  operator fun times(other: TripleCoordinate) = TripleCoordinate(x * other.x, y * other.y, z * other.z)
  operator fun div(other: TripleCoordinate) = TripleCoordinate(x / other.x, y / other.y, z / other.z)
  infix fun plusY(i: Number) = plus(TripleCoordinate(0, i.toLong(), 0))
  infix fun plusX(i: Number) = plus(TripleCoordinate(i.toLong(), 0, 0))
  infix fun plusZ(i: Number) = plus(TripleCoordinate(0, 0, i.toLong()))
}

fun manhattanDistance(a: TripleCoordinate, b: TripleCoordinate = tripleOrigin) =
  abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)

val tripleOrigin = TripleCoordinate(0, 0, 0)

