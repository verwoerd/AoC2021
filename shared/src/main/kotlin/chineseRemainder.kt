import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

/**
 * @author verwoerd
 * @since 06-10-21
 */

// input (modulo, remainder)
fun List<Pair<BigInteger, BigInteger>>.chineseRemainderTheorem(): BigInteger {
  val product = fold(ONE) { acc, (number, _) -> acc * number }
  val result = map { (number, remainder) ->
    val pp = product / number
    remainder * pp.moduloInverse(number) * pp
  }.fold(ZERO, BigInteger::plus)
  return result.rem(product).abs()
}

// Modulare multiplicative inverse with negative results(BigInteger.modInverse does not do this)
fun BigInteger.moduloInverse(modulo: BigInteger): BigInteger {
  if (modulo == ONE) return ZERO
  var m = modulo
  var a = this
  var x0 = ZERO
  var x1 = ONE
  var temp: BigInteger
  while (a > ONE) {
    val quotient = a / m
    temp = m
    m = a % m
    a = temp
    temp = x0
    x0 = x1 - quotient * x0
    x1 = temp
  }
  return x1
}
