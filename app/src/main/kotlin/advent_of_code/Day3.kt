package advent_of_code

import java.io.File

class Day3(val rucksacks: List<String> = emptyList()) {
    fun compartmentContentsFor(rucksackContents: String): Pair<String, String> {
        val compartments = rucksackContents.chunked(rucksackContents.length / 2)

        return Pair(compartments.first(), compartments.last())
    }

    fun commonSuppliesIn(rucksackContents: String): Char {
        val compartments = compartmentContentsFor(rucksackContents)

        val compartment1Supplies = compartments.first.toCharArray()
        val compartment2Supplies = compartments.second.asIterable()

        return compartment1Supplies.intersect(compartment2Supplies.toSet()).first()
    }

    fun supplyPriority(supply: Char): Int {
        val lowerChars = 'a'..'z'

        if (lowerChars.contains(supply)) {
            return lowerChars.indexOf(supply) + 1
        }

        return supplyPriority(supply.lowercaseChar()) + 26
    }

    fun commonBadgeAcross(rucksack1: String, rucksack2: String, rucksack3: String): Char {
        val rucksack1Supplies = rucksack1.toCharArray()
        val rucksack2Supplies = rucksack2.asIterable()
        val rucksack3Supplies = rucksack3.asIterable()

        return rucksack1Supplies.intersect(rucksack2Supplies.toSet()).intersect(rucksack3Supplies.toSet()).first()
    }

}

fun main() {
    val day3 = Day3(File("day3-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).trim().split("\n"))

    val priorities = day3.rucksacks.map {
        day3.supplyPriority(day3.commonSuppliesIn(it))
    }

    println("Sum of priorities ${priorities.sum()}")

    val rucksackTriplets = day3.rucksacks.chunked(3)
    val badgePriorities = rucksackTriplets.map {
        day3.supplyPriority(day3.commonBadgeAcross(it.component1(), it.component2(), it.component3()))
    }

    println("Sum of badge priorities ${badgePriorities.sum()}")

}

