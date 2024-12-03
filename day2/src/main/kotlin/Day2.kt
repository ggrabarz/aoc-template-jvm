import kotlin.math.sign

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return input.sumOf(::isReportSafe)
}

fun part2(): Any {
    return input.sumOf(::isReportSafeWithDampenerN2)
}

fun isReportSafe(report: IntArray): Int {
    if (report.size < 2) return 1
    var monotonicity: Boolean? = null

    for (i in 1 until report.size) {
        val diff = report[i] - report[i - 1]
        if (diff < -3 || diff == 0 || diff > 3) return 0

        if (monotonicity == null) {
            monotonicity = diff > 0
        } else if ((monotonicity && diff < 0) || (!monotonicity && diff > 0)) {
            return 0
        }
    }
    return 1
}

fun isReportSafeWithDampenerN2(report: IntArray): Int {
    if (isReportSafe(report) == 1) return 1

    for (i in 0 until report.size) {
        val reportDampened = report.filterIndexed { index, _ -> index != i }.toIntArray()
        if (isReportSafe(reportDampened) == 1) {
            return 1
        }
    }

    return 0
}

/* got beaten by this solution */
fun isReportSafeWithDampener(report: IntArray): Int {
    var alreadySkipped = false
    if (report.size < 2) return 1
    var i = 0
    var sign: Int = getMonotonicity(report)

    var removedIndex: Int? = null

    while (i < report.size - 1) {
        val right = report[i + 1]
        val fistToTheLeftIndex = fistToTheLeft(i + 1, removedIndex)
        val left = report[fistToTheLeftIndex]
        val diff = right - left

        if (!diffWithinBounds(diff)) {
            if (alreadySkipped) return 0
            alreadySkipped = true
            i++
            removedIndex = i
            continue
        }
        i++

        if ((diff.sign != sign)) {
            if (alreadySkipped) return 0
            alreadySkipped = true
            removedIndex = if (sign > 0) i else i + i
            i++
            continue
        }
    }
    return 1
}

inline fun fistToTheLeft(x: Int, y: Int? = null): Int {
    for (i in x - 1 downTo 0) {
        if (y == null || i != y) return i
    }
    throw IllegalStateException("No valid index found")
}

inline fun diffWithinBounds(diff: Int): Boolean {
    return diff in -3..3 && diff != 0;
}

inline fun getMonotonicity(report: IntArray): Int {
    val (one, two, three, four) = report
    return ((two - one).sign + (three - one).sign + (four - one).sign).sign
}

private val inputLazy: String by lazy { readInput() }

private val input: Array<IntArray> = inputLazy.lines().map { line ->
    line.split(" ").map { it.toInt() }.toIntArray()
}.toTypedArray()

private fun readInput(): String {
    return object {}.javaClass.getResource("Day2.input").readText()
}
