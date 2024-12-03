package day03

import java.io.File

fun main() {
    val text = File("src/day03/input.txt").readText()
    println(part1(text))
}

fun part1(text: String): Int {
    val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

    val matches = regex.findAll(text.filterNot { it.isWhitespace() })
    val multiplications = matches.map { mul ->
        mul.value
            .split("mul(", ",", ")").filterNot { it == "mul(" || it == "," || it == ")" || it.isEmpty() }
            .map { it.toInt() }
    }.toList()

    return multiplications.sumOf { it.reduce { acc, int -> acc * int } }
}

