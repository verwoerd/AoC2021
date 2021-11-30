import kotlin.experimental.and

/**
 * @author verwoerd
 * @since 06-10-21
 */
fun ByteArray.asHexString() = asHexBytes().joinToString(separator = "") { it.toInt().toChar().toString() }

fun ByteArray.asHexBytes() = flatMap {
  val cleaned = 0xFF and it.toInt()
  listOf(
    ((cleaned shr 4).toByte().toLowercaseHex()),
    (it and 0x0F).toLowercaseHex()
  )
}.toByteArray()

fun Byte.toLowercaseHex() = when (this and 0x0F) {
  in (0..9) -> this + '0'.code
  else -> this - 10 + 'a'.code
}.toByte()
