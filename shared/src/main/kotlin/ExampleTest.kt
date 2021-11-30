import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.io.BufferedReader

abstract class ExampleTest(examples: Int, part: Int, func: (BufferedReader) -> Any) : FreeSpec(
  {
    "Part $part" - {
      (1..examples).map {
        "test $it" - {
          val input = this::class.java.getResourceAsStream("/part$part/example$it.input").bufferedReader()
          val output = this::class.java.getResourceAsStream("/part$part/example$it.output").bufferedReader().readText()
          val result = func(input)
          result.toString() shouldBe output
        }
      }
    }
  })
