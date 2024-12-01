package day01

import java.io.File
import kotlin.math.abs

data class LocationIdLists(val list1: List<Int>, val list2: List<Int>)

fun main() {
    val locationIdLists = readAndSortLocationIds()

    println(part1(locationIdLists))
    println(part2(locationIdLists))
}

fun part1(locationIdLists: LocationIdLists): Int {
    val (locationIds1, locationIds2) = locationIdLists

    val diffs = mutableListOf<Int>()

    locationIds1.zip(locationIds2).forEach { pair ->
        diffs.add(abs(pair.first - pair.second))
    }

    return diffs.sum()
}

fun part2(locationIdLists: LocationIdLists): Int {
    val (locationIds1, locationIds2) = locationIdLists

    val similarityScores = mutableListOf<Int>()
    locationIds1.forEach { locationId1 ->
        val occurrences = locationIds2.count{it == locationId1}

        similarityScores.add(occurrences * locationId1)
    }

    return similarityScores.sum()
}

fun readAndSortLocationIds(): LocationIdLists {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    File("src/day01/input.txt").forEachLine { line ->
        list1.add(line.split(" ").first().toInt())
        list2.add(line.split(" ").last().toInt())
    }

    val sortedList1 = list1.sorted()
    val sortedList2 = list2.sorted()

    return LocationIdLists(sortedList1, sortedList2)
}
