package converter
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.util.*

enum class Level {
    ONE, TWO, EXIT
}

var currentLevel = Level.ONE

var sourceBase: BigInteger = BigInteger.ZERO
var targetBase: BigInteger = BigInteger.ZERO

fun main() {

    when (currentLevel) {
        Level.ONE -> firstLevel()
        Level.TWO -> secondLevel()
        else -> {
            return
        }
    }

    main()
}

fun secondLevel() {
    println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back")

    val hasInputBack = readln()
    if (hasInputBack == "/back") {
        currentLevel = Level.ONE
        return
    }

    if (!hasInputBack.contains(".")) {
        val intResult: String = integerNumberBaseConverter(hasInputBack)
        println("Conversion result: $intResult")
        return
    }

    val intResult: String = integerNumberBaseConverter(hasInputBack.substringBefore("."))
    val decimalResult: String = fractionalNumberBaseConverter(hasInputBack)

    val result = "$intResult.$decimalResult"
    println("Conversion result: $result\n")

}

fun fractionalNumberBaseConverter(numberString: String): String {
    val fractionalPart = getFractionalPart(numberString)
    val decimalConversion = convertFractionalPartToDecimal(fractionalPart)
    return convertFractionalPart(decimalConversion, targetBase)
}

fun convertFractionalPartToDecimal(fractionalPart: String): BigDecimal {
    val numbers = fractionalPart.substringAfter(".")
    var result = BigDecimal.ZERO
    for (i in numbers.indices) {
        val basePow = sourceBase.pow((i + 1)).toBigDecimal()
        val baseValue = 1 / basePow.toDouble()
        result += fromHex(numbers[i]).toBigDecimal() * baseValue.toBigDecimal()
    }
    return result.setScale(5, RoundingMode.CEILING)
}

fun convertFractionalPart(fractionalPart: BigDecimal, targetBase: BigInteger): String {
    var result = ""
    var current = fractionalPart

    for (i in 1..5) {
        current *= targetBase.toBigDecimal()
        val decimalPart = current.toString().substringBefore(".")
        val decimalBigInteger = decimalPart.replace("-", "").toBigInteger()
        result += hexConverter(decimalBigInteger)
        current = getFractionalPart(current.toString()).toBigDecimal()
    }

    return result
}

private fun getFractionalPart(current: String) =
    ("0." + current.substringAfter("."))

fun integerNumberBaseConverter(numberToConvert: String): String {
    val decimal = getDecimals(numberToConvert, sourceBase)
    return convertDecimals(BigInteger(decimal), targetBase)
}


fun firstLevel() {
    val welcomeMessage = "Enter two numbers in format: {source base} {target base} (To quit type /exit"
    println(welcomeMessage)

    val scanner = Scanner(System.`in`)

    val hasInputExit = scanner.next()
    if (hasInputExit == "/exit") {
        currentLevel = Level.EXIT
        return
    }

    sourceBase = BigInteger(hasInputExit)
    targetBase = BigInteger(scanner.next())

    currentLevel = Level.TWO
}

fun getDecimals(source: String, sourceBase: BigInteger): String {
    var result = BigInteger.ZERO
    for (i in source.indices) {

        val indexReversed = source.length - i - 1
        val baseConversion = sourceBase.pow(i)
        val valueFromString = BigInteger.valueOf(fromHex(source[indexReversed]).toLong())

        result += valueFromString * BigInteger.valueOf(baseConversion.toLong())
    }
    return result.toString()
}

fun convertDecimals(number: BigInteger, base: BigInteger): String {
    val result = mutableListOf<String>()

    fun helperConvertDecimals(number: BigInteger) {
        if (number == BigInteger.ONE) {
            result.add(number.toString())
            return
        } else if (number == BigInteger.ZERO) {
            return
        }

        result.add(hexConverter(number % base))
        helperConvertDecimals(number / base)
    }

    helperConvertDecimals(number)
    return result.reversed().joinToString(separator = "")
}

fun hexConverter(number: BigInteger): String {
    if (number >= BigInteger.TEN) {
        return ('A'.code + number.toInt() - 10).toChar().toString()
    }

    return number.toString()
}

fun fromHex(string: Char): Int {
    if (string.code >= 'a'.code && string.code <= 'z'.code) {
        return (string.code - 'a'.code + 10)
    }

    return string.code - '0'.code
}
