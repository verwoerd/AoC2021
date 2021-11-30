import java.util.*

/**
 * @author verwoerd
 * @since 24-11-21
 */
fun <T> LinkedList<T>.rotate(times: Int): LinkedList<T> {
  repeat(times % size) {
    addLast(removeFirst())
  }
  return this
}

fun <T> LinkedList<T>.rotateBack(times: Int): LinkedList<T> {
  repeat(times % size) {
    addFirst(removeLast())
  }
  return this
}

fun <T> LinkedList<T>.swap(a: Int, b: Int): LinkedList<T> {
  val indexes = listOf(a, b).sorted()
  rotate(indexes[0])
  val first = removeFirst()
  rotate(indexes[1] - indexes[0] - 1)
  val second = removeFirst()
  addFirst(first)
  rotateBack(indexes[1] - indexes[0] - 1)
  addFirst(second)
  rotateBack(indexes[0])
  return this
}

fun <T> LinkedList<T>.swapValues(a: T, b: T): LinkedList<T> {
  var switch = 0
  while (peek() != a && peek() != b) {
    if (switch > size) {
      error("can't find target $a or $b")
    }
    rotate(1)
    switch++
  }
  val swap = removeFirst()
  var diff = 0
  while (peek() != a && peek() != b) {
    if (diff > size) {
      error("can't find target $a or $b")
    }
    rotate(1)
    diff++
  }
  val other = removeFirst()
  addFirst(swap)
  rotateBack(diff)
  addFirst(other)
  rotateBack(switch)
  return this
}

fun <T> linkedListOf(vararg elements: T) =
  elements.toCollection(LinkedList())
