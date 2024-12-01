import java.util.SortedMap
import kotlin.math.abs

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val leftLocationIDs = sortedMapOf<Int, Int>()
    val rightLocationIDs = sortedMapOf<Int, Int>()

    input.lines().forEach { line ->
        val (left, right) = line.split("   ")
        leftLocationIDs.put(left.toInt(), leftLocationIDs.computeIfAbsent(left.toInt()) { t -> 0 } + 1)
        rightLocationIDs.put(right.toInt(), rightLocationIDs.computeIfAbsent(right.toInt()) { t -> 0 } + 1)
    }

    val expandedLeftLocationIDs = expand(leftLocationIDs)
    val expandedRightLocationIDs = expand(rightLocationIDs)
    val zippedLocations = expandedLeftLocationIDs.zip(expandedRightLocationIDs)
    val distance = zippedLocations.map { (a, b) -> abs(a - b) }.reduce { acc, diff -> acc + diff }

    return distance
}

fun expand(map: SortedMap<Int, Int>): Sequence<Int> {
    return map.asSequence().flatMap { (key, value) -> generateSequence { key }.take(value) }
}

fun part2(): Any {
    val leftLocationIDs = mutableListOf<Int>()
    val rightLocationIDs = hashMapOf<Int, Int>()

    input.lines().forEach { line ->
        val (left, right) = line.split("   ")
        leftLocationIDs.add(left.toInt())
        rightLocationIDs.put(right.toInt(), rightLocationIDs.computeIfAbsent(right.toInt()) { t -> 0 } + 1)
    }

    return leftLocationIDs.fold(0) { acc, left -> acc + left * rightLocationIDs.getOrDefault(left, 0) }
}

private val input: String by lazy { readInput() }

private fun readInput(): String {
    return object {}.javaClass.getResource("Day1.input").readText()
}

