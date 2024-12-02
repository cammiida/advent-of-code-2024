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
}

fun part1(reports: List<List<Int>>): Int {
    val safeReports = reports.filter { isValidReport(it) }

    return safeReports.size
}


fun isValidReport(report: List<Int>): Boolean {
    val sortedReportAsc = report.sorted()
    val sortedReportDesc = report.sortedDescending()
    if (report == sortedReportAsc || report == sortedReportDesc) {
        return validateDiffs(report)
    }

    return false
}


fun validateDiffs(report: List<Int>): Boolean {
    for (i in 1..<report.size) {
        val diff = abs(report[i] - report[i-1])
        if (diff < 1 || diff > 3){
            return false
        }
    }

    return true
}