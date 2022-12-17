package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day8Test {
    val day8 = Day8()

    @Test
    fun canSatisfyResultFromSampleInput() {
        assertEquals(21, day8.countVisibleTrees(listOf("30373",
                "65332",
                "33549",
                "35390")))
    }

    @Test
    fun allTreesAreVisibleInASingleTreeGrid() {
        assertEquals(1, day8.countVisibleTrees(listOf("3")))
    }

    @Test
    fun allTreesAreVisibleInASingleTreeRow() {
        assertEquals(1, day8.findVisibleTreesInRow("3"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeRow() {
        assertEquals(2, day8.findVisibleTreesInRow("33"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeColumn() {
        assertEquals(2, day8.findVisibleTreesInColumn(listOf("3", "3"), 0))
        assertEquals(2, day8.findVisibleTreesInColumn(listOf("01", "23"), 1))
        assertEquals(2, day8.findVisibleTreesInColumn(listOf("215", "333"), 2))
    }

    @Test
    fun treeColumnMustBeValid() {
        assertFailsWith<IllegalArgumentException> { day8.findVisibleTreesInColumn(listOf("3", "3"), -1) }
        assertFailsWith<IllegalArgumentException> { day8.findVisibleTreesInColumn(listOf("3", "3"), 1) }
    }

    @Test
    fun someTreesCanBeHiddenInAThreeTreeRow() {
        assertEquals(2, day8.findVisibleTreesInRow("313"))
    }

    @Test
    fun someTreesCanBeHiddenInAFourTreeRow() {
        assertEquals(2, day8.findVisibleTreesInRow("3133"))
        assertEquals(3, day8.findVisibleTreesInRow("3143"))
    }

    @Test
    fun allTreesAreVisibleInAGridOf4Trees() {
        assertEquals(4, day8.countVisibleTrees(listOf("12", "34")))
    }

    @Test
    fun someTreesCanBeHiddenInAGridOf9Trees() {
        assertEquals(8, day8.countVisibleTrees(listOf("456", "123", "789")))
    }
}
