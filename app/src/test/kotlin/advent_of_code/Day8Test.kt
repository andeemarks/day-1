package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day8Test {

    @Test
    fun canSatisfyResultFromSampleInput() {
        val day8 = Day8()

        assertEquals(21, day8.countVisibleTrees(listOf("30373",
                "65332",
                "33549",
                "35390")))
    }

    @Test
    fun allTreesAreVisibleInASingleTreeGrid() {
        val day8 = Day8()

        assertEquals(1, day8.countVisibleTrees(listOf("3")))
    }

    @Test
    fun allTreesAreVisibleInASingleTreeRow() {
        val day8 = Day8()

        assertEquals(1, day8.countVisibleTreesInRow("3"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeRow() {
        val day8 = Day8()

        assertEquals(2, day8.countVisibleTreesInRow("33"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeColumn() {
        val day8 = Day8()

        assertEquals(2, day8.countVisibleTreesInColumn(listOf("3", "3"), 0))
        assertEquals(2, day8.countVisibleTreesInColumn(listOf("1", "3"), 0))
        assertEquals(2, day8.countVisibleTreesInColumn(listOf("5", "3"), 0))
    }

    @Test
    fun treeColumnMustBeValid() {
        val day8 = Day8()

        assertFailsWith<IllegalArgumentException> { day8.countVisibleTreesInColumn(listOf("3", "3"), -1) }
        assertFailsWith<IllegalArgumentException> { day8.countVisibleTreesInColumn(listOf("3", "3"), 1) }
    }

    @Test
    fun someTreesCanBeHiddenInAThreeTreeRow() {
        val day8 = Day8()

        assertEquals(2, day8.countVisibleTreesInRow("313"))
    }

    @Test
    fun someTreesCanBeHiddenInAFourTreeRow() {
        val day8 = Day8()

        assertEquals(2, day8.countVisibleTreesInRow("3133"))
        assertEquals(3, day8.countVisibleTreesInRow("3143"))
    }

    @Test
    fun allTreesAreVisibleInAGridOf4Trees() {
        val day8 = Day8()

        assertEquals(4, day8.countVisibleTrees(listOf("12", "34")))
    }

    @Test
    fun someTreesCanBeHiddenInAGridOf9Trees() {
        val day8 = Day8()

        assertEquals(8, day8.countVisibleTrees(listOf("456", "123", "789")))
    }
}
