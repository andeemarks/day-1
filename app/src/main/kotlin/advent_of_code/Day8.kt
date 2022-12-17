package advent_of_code

import java.io.File

class TreeHeight(private val treeRow: Int, private val treeColumn: Int, private val treeHeight: Int) {
    val value = Pair(Pair(treeColumn, treeRow), treeHeight)

    override fun toString(): String {
        return "@($treeRow,$treeColumn) $treeHeight"
    }

    override fun equals(other: Any?): Boolean {
        val otherOne = other as TreeHeight

        return treeRow == otherOne.treeRow &&
                treeColumn == otherOne.treeColumn &&
                treeHeight == otherOne.treeHeight
    }

    override fun hashCode(): Int {
        var result = treeRow
        result = 31 * result + treeColumn
        result = 31 * result + treeHeight
        return result
    }

    fun flip(): TreeHeight {
        return TreeHeight(treeColumn, treeRow, treeHeight)
    }

}

class Day8 {


    fun findVisibleTreesInRow(treeRow: String, rowIndex: Int = 0): List<TreeHeight> {
        val treeHeights = treeRow.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
        val visibleTrees = mutableSetOf<TreeHeight>()
        visibleTrees.add(TreeHeight(rowIndex, 0, treeHeights.first()))
        visibleTrees.addAll(findVisibleTreesFromLeft(treeHeights, rowIndex))
        visibleTrees.addAll(findVisibleTreesFromRight(treeHeights, rowIndex))

        if (treeHeights.size >= 2) {
            visibleTrees.add(TreeHeight(rowIndex, treeHeights.size - 1, treeHeights.last()))
        }

        return visibleTrees.toMutableList()
    }

    private fun findVisibleTreesFromLeft(treeHeights: MutableList<Int>, rowIndex: Int): MutableSet<TreeHeight> {
        var leftTreeIndex = 0
        var rightTreeIndex = 1
        val visibleTrees = mutableSetOf<TreeHeight>()
        while (rightTreeIndex < treeHeights.size) {
            if (treeHeights[leftTreeIndex] < treeHeights[rightTreeIndex]) {
                visibleTrees.add(TreeHeight(rowIndex, rightTreeIndex, treeHeights[rightTreeIndex]))
                leftTreeIndex = rightTreeIndex
                rightTreeIndex = leftTreeIndex + 1
            } else {
                rightTreeIndex++
            }
        }

        return visibleTrees
    }

    private fun findVisibleTreesFromRight(treeHeights: MutableList<Int>, rowIndex: Int): MutableSet<TreeHeight> {
        var rightTreeIndex = treeHeights.size - 1
        var leftTreeIndex = rightTreeIndex - 1
        val visibleTrees = mutableSetOf<TreeHeight>()
        while (leftTreeIndex >= 0) {
            if (treeHeights[rightTreeIndex] < treeHeights[leftTreeIndex]) {
                visibleTrees.add(TreeHeight(rowIndex, leftTreeIndex, treeHeights[leftTreeIndex]))
                rightTreeIndex = leftTreeIndex
                leftTreeIndex = rightTreeIndex - 1
            } else {
                leftTreeIndex--
            }
        }

        return visibleTrees
    }

    fun findVisibleTreesInColumn(treeGrid: List<String>, columnNumber: Int): List<TreeHeight> {
        require(columnNumber in 0 until (treeGrid[0].length))

        val treeColumn = treeGrid.map { treeRow -> treeRow[columnNumber] }.joinToString("")

        return findVisibleTreesInRow(treeColumn, columnNumber).map { it.flip() }
    }


    fun countVisibleTrees(treeGrid: List<String>): Int {
        val visibleFromSide = treeGrid.mapIndexed { i, it -> findVisibleTreesInRow(it, i) }.flatten().toSet()
        val visibleTrees = mutableSetOf<TreeHeight>()
        visibleTrees.addAll(visibleFromSide)
        for (i in 0 until (treeGrid[0].length)) {
            val visibleTreesInColumn = findVisibleTreesInColumn(treeGrid, i)
            visibleTrees.addAll(visibleTreesInColumn)
        }

        return visibleTrees.size
    }
}

fun main() {
    val treeGrid = File("day8-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day8()

    println("Number of visible trees: ${app.countVisibleTrees(treeGrid)}")


}
