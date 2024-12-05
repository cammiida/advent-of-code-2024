package day05

import java.io.File
import kotlin.math.ceil

fun main() {
    val input = File("src/day05/input.txt")
        .readLines().filter { it.isNotBlank() }.groupBy { it.contains("|") }.map { it.value }
    val rules = input[0].map { rule -> rule.split("|").map { it.toInt() } }
    val updates = input[1].map { update -> update.split(",").map { it.toInt() } }

    val validUpdates = updates.filter { isValidUpdate(rules, it) }
    val middleNums = validUpdates.map {
        it[ceil(it.size.toDouble() / 2 - 1).toInt()]
    }
    validUpdates.forEach { println(it) }
    val res = middleNums.sum()
    println(res)
}

fun isValidUpdate(rules: List<List<Int>>, update: List<Int>): Boolean {
    for (rule in rules) {
        if (!matchesRule(rule, update)) {
            return false
        }
    }

    return true
}

fun matchesRule(rule: List<Int>, update: List<Int>): Boolean {
    if (!update.containsAll(rule)) {
        return true
    }

    val ruleFirstPageIdx = update.indexOfFirst { it == rule[0] }
    val ruleSecondPageIdx = update.indexOfFirst { it == rule[1] }

    return ruleFirstPageIdx < ruleSecondPageIdx
}