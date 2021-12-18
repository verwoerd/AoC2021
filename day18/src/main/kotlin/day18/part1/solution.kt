package day18.part1

import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 18/12/21
 */
fun day18Part1(input: BufferedReader): Any {
  return input.lineSequence()
    .map(::parseTree)
    .reduce { a, b -> a + b }.magnitude()
}

operator fun TreePair.plus(other: TreePair): TreePair {
  val result = TreePair(parent, this, other)
  if (parent != null && parent?.left == this) {
    parent!!.left = result
  } else if (parent != null && parent?.right == this) {
    parent!!.right = result
  }
  this.parent = result
  other.parent = result
  result.reduce()
  return result
}

fun TreePair.reduce() {
  do {
    val changed = explode() || split()
  } while (changed)
}


fun TreePair.explode(depth: Int = 0): Boolean {
  if (depth == 4) {
    // find left value
    walkUpLeft()

    // find right value
    walkUpRight()

    if (parent!!.left == this) {
      parent!!.left = TreeValue(parent!!, 0)
    } else {
      parent!!.right = TreeValue(parent!!, 0)
    }
    return true
  }
  val leftValue = left!!
  val rightValue = right!!
  if (leftValue is TreePair && leftValue.explode(depth + 1)) {
    return true
  }
  return rightValue is TreePair && rightValue.explode(depth + 1)
}

fun TreePair.walkUpLeft() {
  var current = this
  while (current.parent != null && current.parent!!.left == current) {
    current = current.parent!!
  }
  if (current.parent == null) {
    return
  }
  val stack = mutableListOf<TreeNode>(current.parent!!.left!!)
  while (stack.isNotEmpty()) {
    when (val next = stack.removeLast()) {
      is TreeValue -> {
        next.value += (this.left!! as TreeValue).value
        return
      }
      is TreePair -> {
        stack.add(next.left!!)
        stack.add(next.right!!)
      }
    }
  }
}

fun TreePair.walkUpRight() {
  var current = this
  while (current.parent != null && current.parent!!.right == current) {
    current = current.parent!!
  }
  if (current.parent == null) {
    return
  }
  val stack = mutableListOf<TreeNode>(current.parent!!.right!!)
  while (stack.isNotEmpty()) {
    when (val next = stack.removeLast()) {
      is TreeValue -> {
        next.value += (this.right!! as TreeValue).value
        return
      }
      is TreePair -> {
        stack.add(next.right!!)
        stack.add(next.left!!)
      }
    }
  }
}

fun parseTree(input: String): TreePair {
  val stack = mutableListOf<TreePair>()
  val root = TreePair()
  var current = root
  input.split(",")
    .map {
      var value = it
      while (value.isNotBlank()) {
        when {
          value[0] == '[' -> {
            value = value.substring(1)
            val next = TreePair(current)
            if (current.left == null) {
              current.left = next
            } else {
              current.right = next
            }
            stack.add(current)
            current = next
          }
          value[0] == ']' -> {
            value = value.substring(1)
            if (stack.isNotEmpty()) {
              current = stack.removeLast()
            }
          }
          else -> {
            val index = value.indexOf(']')
            var number: String
            if (index != -1) {
              number = value.substring(0, index)
              value = value.substring(index)
            } else {
              number = value
              value = ""
            }
            if (current.left == null) {
              current.left = TreeValue(current, number.toLong())
            } else {
              current.right = TreeValue(current, number.toLong())
            }
          }
        }
      }
    }
  return (root.left!! as TreePair).also { it.parent = null }
}

sealed interface TreeNode {
  var parent: TreePair?
  fun split(): Boolean
  fun magnitude(): Long
  fun copyNode(): TreeNode
}

data class TreePair(
  override var parent: TreePair? = null,
  var left: TreeNode? = null,
  var right: TreeNode? = null,
) : TreeNode {
  override fun toString(): String {
    return "[$left, $right]"
  }

  override fun split(): Boolean {
    return left!!.split() || right!!.split()
  }

  override fun magnitude(): Long {
    return 3 * left!!.magnitude() + 2 * right!!.magnitude()
  }

  override fun copyNode(): TreeNode {
    val base = TreePair(null)
    base.left = left!!.copyNode().also { it.parent = base }
    base.right = right!!.copyNode().also { it.parent = base }
    return base
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    return false
  }

  override fun hashCode(): Int {
    var result = left?.hashCode() ?: 0
    result = 31 * result + (right?.hashCode() ?: 0)
    return result
  }
}

data class TreeValue(
  override var parent: TreePair?,
  var value: Long
) : TreeNode {
  override fun split(): Boolean {
    if (this.value < 10) {
      return false
    }
    val next = TreePair(parent)
    next.left = TreeValue(next, value / 2)
    next.right = TreeValue(next, value / 2 + value % 2)
    if (parent!!.left == this) {
      parent!!.left = next
    } else {
      parent!!.right = next
    }
    return true
  }

  override fun magnitude() = value
  override fun copyNode(): TreeNode {
    return TreeValue(null, value)
  }

  override fun toString(): String {
    return "$value"
  }
}
