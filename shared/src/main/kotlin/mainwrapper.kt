import java.io.BufferedReader

/**
 * @author verwoerd
 * @since 06-11-20
 */
object Resource

inline fun withResourceFile(problem: (BufferedReader) -> Any) {
  val startTime = System.currentTimeMillis()
  try {
    val result = Resource::class.java.getResourceAsStream("input")!!.bufferedReader().run(problem)
    println(result)
  } catch (e: Throwable) {
    println("Caught exception ${e.localizedMessage}")
    e.printStackTrace()
  } finally {
    val endTime = System.currentTimeMillis()
    println("Runtime: ${endTime - startTime}ms")
  }
}

