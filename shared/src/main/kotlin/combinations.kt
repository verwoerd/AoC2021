/**
 * @author verwoerd
 * @since 07-11-20
 */
fun <T> combinations(n: Int, list: Collection<T>): List<List<T>> =
  if (n == 0) listOf(emptyList())
  else list.flatMapTails { sublist ->
    combinations(n-1, sublist.drop(1)).map { (it + sublist.first()) }
  }

fun <T> Collection<T>.flatMapTails(f: (Collection<T>) -> (List<List<T>>)): List<List<T>> =
  if (isEmpty()) emptyList()
  else f(this) + this.drop(1).flatMapTails(f)

fun <T> nonRepeatingPermutations(length: Int, components: Collection<T>): List<List<T>> =
  if (components.isEmpty() || length <= 0) listOf(emptyList())
  else nonRepeatingPermutations(length - 1, components).flatMap { sublist ->
    components.filter { it !in sublist }.map { sublist + it }
  }

fun <T> allPermutations(set: Set<T>): Set<List<T>> {
  if (set.isEmpty()) return emptySet()

   return realAllPermutations(set.toList())
}

private fun <T> realAllPermutations(list: List<T>): Set<List<T>> {
  if (list.isEmpty()) return setOf(emptyList())

  val result: MutableSet<List<T>> = mutableSetOf()
  for (i in list.indices) {
    realAllPermutations(list - list[i]).forEach{
        item -> result.add(item + list[i])
    }
  }
  return result
}
