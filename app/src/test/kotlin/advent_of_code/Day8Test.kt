package advent_of_code

import advent_of_code.day8.TreeHeight
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Day8Test {
    val day8 = Day8()

    @Test
    fun canSatisfyResultFromSampleInput() {
        assertEquals(21, day8.countVisibleTrees(listOf("30373",
                "25512",
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
        assertEquals(listOf(TreeHeight(0, 0, 3)), day8.findVisibleTreesInRow("3"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeRow() {
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(0, 1, 3)), day8.findVisibleTreesInRow("33"))
    }

    @Test
    fun allTreesAreVisibleInATwoTreeColumn() {
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(1, 0, 3)), day8.findVisibleTreesInColumn(listOf("3", "3"), 0))
        assertEquals(listOf(TreeHeight(0, 1, 1), TreeHeight(1, 1, 3)), day8.findVisibleTreesInColumn(listOf("01", "23"), 1))
        assertEquals(listOf(TreeHeight(0, 2, 5), TreeHeight(1, 2, 3)), day8.findVisibleTreesInColumn(listOf("215", "333"), 2))
    }

    @Test
    fun treeColumnMustBeValid() {
        assertFailsWith<IllegalArgumentException> { day8.findVisibleTreesInColumn(listOf("3", "3"), -1) }
        assertFailsWith<IllegalArgumentException> { day8.findVisibleTreesInColumn(listOf("3", "3"), 1) }
    }

    @Test
    fun someTreesCanBeHiddenInAThreeTreeRow() {
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(0, 2, 3)), day8.findVisibleTreesInRow("313"))
    }

    @Test
    fun someTreesCanBeHiddenInLongerRows() {
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(0, 3, 3)), day8.findVisibleTreesInRow("3133"))
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(0, 1, 4), TreeHeight(0, 3, 3)), day8.findVisibleTreesInRow("3413"))
        assertEquals(listOf(TreeHeight(0, 0, 2), TreeHeight(0, 1, 5), TreeHeight(0, 2, 5), TreeHeight(0, 4, 2)), day8.findVisibleTreesInRow("25512"))
        assertEquals(listOf(TreeHeight(0, 0, 3), TreeHeight(0, 2, 5), TreeHeight(0, 4, 9)), day8.findVisibleTreesInRow("33549"))
        assertEquals(listOf(TreeHeight(0, 0, 9), TreeHeight(0, 2, 5), TreeHeight(0, 4, 3)), day8.findVisibleTreesInRow("94533"))
    }

    @Test
    fun allTreesAreVisibleInAGridOf4Trees() {
        assertEquals(4, day8.countVisibleTrees(listOf("12", "34")))
    }

    @Test
    fun someTreesCanBeHiddenInAGridOf9Trees() {
        assertEquals(8, day8.countVisibleTrees(listOf("456", "312", "789")))
    }

    @Test
    fun treeHeightCoordsCanBeFlipped() {
        assertEquals(TreeHeight(2, 1, 3), TreeHeight(1, 2, 3).flip())
        assertEquals(TreeHeight(1, 2, 4), TreeHeight(2, 1, 4).flip())
    }

    @Test
    fun canCalculateScenicScoreOfATreeInAGrid() {
        val grid = listOf("30373", "25512", "65332", "33549", "35390")

        assertEquals(4, day8.scenicScoreOf(TreeHeight(1, 2, 5), grid))
        assertEquals(8, day8.scenicScoreOf(TreeHeight(3, 2, 5), grid))
    }
}
