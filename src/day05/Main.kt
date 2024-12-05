package day05

import java.io.File
import kotlin.math.ceil

fun main() {
    val input = File("src/day05/input.txt")
        .readLines().filter { it.isNotBlank() }.groupBy { it.contains("|") }.map { it.value }
    val rules = input[0].map { rule -> rule.split("|").map { it.toInt() } }
    val updates = input[1].map { update -> update.split(",").map { it.toInt() } }

    println("part 1: ${part1(rules, updates)}")
    println("part 2: ${part2(rules, updates)}")
}

fun part2(rules: List<List<Int>>, updates: List<List<Int>>): Int {
    val inValidUpdates = updates.filter { !isValidUpdate(rules, it) }

    val correctedUpdates = inValidUpdates.map {
        update ->
            var updateCopy = update.toMutableList()
            while (!isValidUpdate(rules, updateCopy)) {
                updateCopy = fixIncorrectUpdate(rules, updateCopy)
            }
            updateCopy
    }
    val middleNums = correctedUpdates.map {
        it[ceil(it.size.toDouble() / 2 - 1).toInt()]
    }
    return middleNums.sum()
}

fun fixIncorrectUpdate(rules: List<List<Int>>, update: List<Int>): MutableList<Int> {
    val updateCopy = update.toMutableList()
    for (rule in rules) {
        if (!matchesRule(rule, updateCopy)) {
            val prePage = updateCopy.withIndex().first { it.value == rule[0] }
            val postPage = updateCopy.withIndex().first { it.value == rule[1] }

            val removedPage = updateCopy.removeAt(postPage.index)
            if (prePage.index + 1 > updateCopy.size - 1) {
                updateCopy.add(removedPage)
            } else {
                updateCopy.add(prePage.index + 1, removedPage)
            }
        }
    }

    return updateCopy
}

fun part1(rules: List<List<Int>>, updates: List<List<Int>>): Int {
    val validUpdates = updates.filter { isValidUpdate(rules, it) }
    val middleNums = validUpdates.map {
        it[ceil(it.size.toDouble() / 2 - 1).toInt()]
    }
    val res = middleNums.sum()
    return res
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