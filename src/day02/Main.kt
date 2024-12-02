package day02


import java.io.File
import kotlin.math.abs

fun main() {
    val reports = File("src/day02/input.txt")
         .readLines()
         .map { report ->
             report.split(" ").map { level -> level.toInt() }
         }

    println(part1(reports))
    println(part2(reports))
}

fun part1(reports: List<List<Int>>): Int {
    val safeReports = reports.filter { isValidReport(it) }

    return safeReports.size
}

fun part2(reports: List<List<Int>>): Int {
    val safeReports = reports.filter { isValidReport(it, true) }

    return safeReports.size
}


fun isValidReport(report: List<Int>, dampener: Boolean = false): Boolean {
    val isAscending = if (report.first() < report.last()) 1 else -1

    for (i in 1..< report.size) {
        val diff = (report[i] - report[i-1]) * isAscending
        if (diff < 1 || diff > 3) {
            if (dampener) {
                val filteredReport = report.toMutableList()
                filteredReport.removeAt(i-1)
                return isValidReport(report.filterIndexed { index, _ -> index != i-1 }, false) ||
                        isValidReport(report.filterIndexed { index, _ -> index != i }, false)
            }
            return false
        }
    }

    val sortedAscending = report.sorted()
    val sortedDescending = report.sortedDescending()

    val isValid = report == sortedAscending || report == sortedDescending
    return isValid
}

