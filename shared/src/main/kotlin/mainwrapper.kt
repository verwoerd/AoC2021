import java.io.BufferedReader
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Paths

/**
 * @author verwoerd
 * @since 06-11-20
 */

inline fun withResourceFile(problem: (BufferedReader) -> Any) {
  val startTime = System.currentTimeMillis()
  try {
    val input = openInput()
    println(input.run(problem))
  } catch (e: Throwable) {
    println("Caught exception ${e.localizedMessage}")
    e.printStackTrace()
  } finally {
    val endTime = System.currentTimeMillis()
    println("Runtime: ${endTime - startTime}ms")
  }
}

fun openInput() : BufferedReader {
  val file = Paths.get("src/main/resources/input").toAbsolutePath().toFile()
  if(file.exists()) {
    return file.bufferedReader()
  }
  print("Downloading input file...")
  file.createNewFile()
  val output = FileOutputStream(file).channel
  val year = System.getenv("AOC_YEAR") ?: error("Please define AOC_YEAR")
  val day =System.getenv("AOC_DAY") ?: error("Please define AOC_DAY")
  val session = System.getenv("AOC_SESSION") ?: error("Please define AOC_SESSION")
  val url = URL("https://adventofcode.com/$year/day/$day/input").openConnection()
  url.setRequestProperty("Cookie", "session=$session")
  val input = Channels.newChannel(url.getInputStream())
  output.transferFrom(input, 0, Long.MAX_VALUE)
  println("complete")
  return file.bufferedReader()
}
