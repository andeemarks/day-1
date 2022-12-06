package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {

    @Test
    fun splitsRucksackCompartmentContentsEvenly() {
        assertEquals(Pair("vJrwpWtwJgWr", "hcsFMMfFFhFp"), Day3().compartmentContentsFor("vJrwpWtwJgWrhcsFMMfFFhFp"))
        assertEquals(
            Pair("jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL"),
            Day3().compartmentContentsFor("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")
        )
    }

    @Test
    fun findsCommonSupplies() {
        assertEquals('p', Day3().commonSuppliesIn("vJrwpWtwJgWrhcsFMMfFFhFp"))
        assertEquals('L', Day3().commonSuppliesIn("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"))
    }

    @Test
    fun findsSupplyPriorities() {
        assertEquals(1, Day3().supplyPriority('a'))
        assertEquals(26, Day3().supplyPriority('z'))
        assertEquals(27, Day3().supplyPriority('A'))
        assertEquals(52, Day3().supplyPriority('Z'))
    }

    @Test
    fun findsCommonBadgesAcrossThreeRucksacks() {
        assertEquals(
            'r', Day3().commonBadgeAcross
                ("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg")
        )
        assertEquals(
            'Z', Day3().commonBadgeAcross
                ("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw")
        )
    }
}
