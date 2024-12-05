package day04

import java.io.File


fun main() {
    val lines = File("src/day04/input.txt").readLines().toMutableList()
    val forward = lines.toMutableList()
    val backward = lines.map { it.reversed() }
    val up = lines
        .map { line -> line.withIndex() }
        .flatten()
        .groupBy(keySelector = { it.index }, valueTransform = { it.value })
        .map { it.value.joinToString("") }
    val down = up.map { it.reversed() }


    val columns = forward.first().length
    val primaryDiagonal = mutableListOf<String>()
    val secondaryDiagonal = mutableListOf<String>()

    // from top to bottom
    for (i in 0 ..< forward.size) {
        val tempPrimaryDiagonal = mutableListOf<Char>()
        val tempSecondaryDiagonal = mutableListOf<Char>()
        for (j in 0 ..< columns - i ) {
            tempPrimaryDiagonal.add(forward[i+j][j])
            tempSecondaryDiagonal.add(backward[i+j][j])
        }
        primaryDiagonal.add(tempPrimaryDiagonal.joinToString(""))
        secondaryDiagonal.add(tempSecondaryDiagonal.joinToString(""))
    }

    // from left to right
    for (j in 1 ..< columns) {
        val tempPrimaryDiagonal = mutableListOf<Char>()
        val tempSecondaryDiagonal = mutableListOf<Char>()
        for (i in 0 ..< forward.size - j) {
            tempPrimaryDiagonal.add(forward[i][i+j])
            tempSecondaryDiagonal.add(backward[i][i+j])
        }
        primaryDiagonal.add(tempPrimaryDiagonal.joinToString(""))
        secondaryDiagonal.add(tempSecondaryDiagonal.joinToString(""))
    }

    val primaryDiagonalReversed = primaryDiagonal.map { it.reversed() }
    val secondaryDiagonalReversed = secondaryDiagonal.map { it.reversed() }

    println("forward: $forward")
    println("backward: $backward")
    println("up: $up")
    println("down: $down")
    println("primary diagonal: $primaryDiagonal")
    println("primary diagonal reversed: $primaryDiagonalReversed")
    println("secondary diagonal: $secondaryDiagonal")
    println("secondary diagonal reversed: $secondaryDiagonalReversed")

    val combinedList =
        forward.asSequence()
            .plus(backward)
            .plus(up)
            .plus(down)
            .plus(primaryDiagonal)
            .plus(primaryDiagonalReversed)
            .plus(secondaryDiagonal)
            .plus(secondaryDiagonalReversed)
            .toList()

    val regex = Regex("XMAS")
    val part1 = combinedList.sumOf { regex.findAll(it).count() }
    println(part1)

    // part2 brute force?
    val res = lines.withIndex().map { line ->
        line.value.withIndex().map { pos ->
            findXRightDown(lines, pos.index, line.index)
        }
    }.flatten().sum()
    println(res)
}


fun findXRightDown(lines: List<String>, posIdx: Int, lineIdx: Int): Int {
    if (lines.size <= lineIdx+2 || lines.first().length <= posIdx+2) {
        return 0;
    }

    val primDiag = listOf(lines[lineIdx][posIdx], lines[lineIdx+1][posIdx+1], lines[lineIdx+2][posIdx+2]).joinToString("")
    val secondDiag = listOf(lines[lineIdx+2][posIdx], lines[lineIdx+1][posIdx+1], lines[lineIdx][posIdx+2]).joinToString("")

    val regex = Regex("(SAM|MAS)")
    if (regex.matches(primDiag) && regex.matches(secondDiag)) {
        return 1
    }

    return 0
}