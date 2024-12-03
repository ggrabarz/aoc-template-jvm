fun main() {
    val fetch = input.length
    println(part1())
    println(part2())
}

fun part1(): Any {
    val numbers = Regex("(\\d+)")
    val matches = Regex("(mul\\(\\d+,\\d+\\))").findAll(input)
    return matches.map { stringToMul(numbers, it) }.sum()
}

class Result(val compute: Boolean, val value: Int)

fun part2(): Any {
    val DO = "do()"
    val DONT = "don't()"
    val numbers = Regex("(\\d+)")
    val matches = Regex("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))").findAll(input)

    return matches.fold(Result(true, 0)) { result, match ->
        when (match.value) {
            DO -> Result(true, result.value)
            DONT -> Result(false, result.value)
            else -> Result(
                result.compute, if (result.compute) result.value + stringToMul(numbers, match) else result.value
            )
        }
    }.value
}

fun stringToMul(regex: Regex, input: MatchResult): Int {
    return regex.findAll(input.value).map { it.value.toInt() }.fold(1) { acc, num -> acc * num }
}

private val inputLazy: String by lazy { readInput() }

private val input = inputLazy.toString()

private fun readInput(): String {
    return object {}.javaClass.getResource("Day3.input").readText()
}
