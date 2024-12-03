package day03

import java.io.File

fun main() {
    val text = File("src/day03/input.txt").readText().filterNot { it.isWhitespace() }
    println(part1(text))
    println(part2(text))
}

fun part1(text: String): Int {
    val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

    val matches = regex.findAll(text).map { it.value }.toList()
    return calculateMuls(matches)
}

fun part2(text: String): Int {
    val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")

    val matches = regex.findAll(text).map { it.value }.toList()

    val stringMuls = mutableListOf<String>()

    var enabled = true
    for (match in matches) {
        if (match == "do()") {
            enabled = true
        } else if (match == "don't()") {
            enabled = false
        } else if (enabled) {
            stringMuls.add(match)
        }
    }

    return calculateMuls(stringMuls)
}


fun calculateMuls(stringMuls: List<String>): Int {
    val multiplications = stringMuls.map { mul ->
        mul
            .split("mul(", ",", ")")
            .filterNot { it == "mul(" || it == "," || it == ")" || it.isEmpty() }
            .map { it.toInt() }
    }.toList()

    return multiplications.sumOf { it.reduce { acc, int -> acc * int } }
}
