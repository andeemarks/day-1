package advent_of_code

import java.io.File

class Day4(val assignments: List<String> = emptyList()) {
    fun rangeFrom(sectionAssignment: String): IntRange {
        val assignment = sectionAssignment.trim().split("-")

        return IntRange(assignment.first().toInt(), assignment.last().toInt())
    }

    fun haveCompleteOverlap(range1: IntRange, range2: IntRange): Boolean {
        val range1OverlapsRange2 = range1.intersect(range2).size == range2.count()
        val range2OverlapsRange1 = range2.intersect(range1).size == range1.count()

        return range2OverlapsRange1 || range1OverlapsRange2
    }

    fun parse(assignments: String): Pair<IntRange, IntRange> {
        val assignmentPair = assignments.split(",")

        return Pair(rangeFrom(assignmentPair[0]), rangeFrom(assignmentPair[1]))
    }

    fun havePartialOverlap(range1: IntRange, range2: IntRange): Boolean {
        val range1OverlapsRange2 = range1.intersect(range2).isNotEmpty()
        val range2OverlapsRange1 = range2.intersect(range1).isNotEmpty()

        return range2OverlapsRange1 || range1OverlapsRange2
    }

}

fun main() {
    val day4 = Day4(File("day4-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).trim().split("\n"))

    val assignments = day4.assignments.map { day4.parse(it) }

    val fullyOverlappingAssignments = assignments.filter { day4.haveCompleteOverlap(it.first, it.second)}
    println("Count of fully overlapping assignments ${fullyOverlappingAssignments.size}")

    val partiallyOverlappingAssignments = assignments.filter { day4.havePartialOverlap(it.first, it.second)}
    println("Count of partially overlapping assignments ${partiallyOverlappingAssignments.size}")

}