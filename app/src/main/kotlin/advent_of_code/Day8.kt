package advent_of_code

import advent_of_code.day8.TreeHeight
import java.io.File

class Day8 {
    fun countVisibleTrees(treeGrid: List<String>): Int {
        val visibleTrees = treeGrid.mapIndexed { i, it -> findVisibleTreesInRow(it, i) }.flatten().toMutableSet()
        for (i in 0 until (treeGrid[0].length)) {
            visibleTrees.addAll(findVisibleTreesInColumn(treeGrid, i))
        }

        return visibleTrees.size
    }

    fun findVisibleTreesInRow(treeRow: String, rowIndex: Int = 0): List<TreeHeight> {
        val treeHeights = treeHeights(treeRow)
        val visibleTrees = mutableSetOf<TreeHeight>()
        visibleTrees.add(TreeHeight(rowIndex, 0, treeHeights.first()))
        visibleTrees.addAll(findVisibleTreesFromLeft(treeHeights, rowIndex))
        visibleTrees.addAll(findVisibleTreesFromRight(treeHeights, rowIndex))
        visibleTrees.add(TreeHeight(rowIndex, treeHeights.size - 1, treeHeights.last()))

        return visibleTrees.toMutableList()
    }

    fun findVisibleTreesInColumn(treeGrid: List<String>, columnNumber: Int): List<TreeHeight> {
        require(columnNumber in 0 until (treeGrid[0].length))

        val treeColumn = columnToRow(treeGrid, columnNumber)

        return findVisibleTreesInRow(treeColumn, columnNumber).map { it.swapRowAndColumn() }
    }

    private fun findVisibleTreesFromLeft(treeHeights: List<Int>, row: Int): Set<TreeHeight> {
        var leftTreeColumn = 0
        var rightTreeColumn = leftTreeColumn + 1
        val visibleTrees = mutableSetOf<TreeHeight>()
        while (rightTreeColumn < treeHeights.size) {
            if (treeHeights[leftTreeColumn] < treeHeights[rightTreeColumn]) {
                visibleTrees.add(TreeHeight(row, rightTreeColumn, treeHeights[rightTreeColumn]))
                leftTreeColumn = rightTreeColumn
                rightTreeColumn = leftTreeColumn + 1
            } else {
                rightTreeColumn++
            }
        }

        return visibleTrees
    }

    private fun findVisibleTreesFromRight(treeHeights: MutableList<Int>, row: Int): Set<TreeHeight> {
        var rightTreeColumn = treeHeights.size - 1
        var leftTreeColumn = rightTreeColumn - 1
        val visibleTrees = mutableSetOf<TreeHeight>()
        while (leftTreeColumn >= 0) {
            if (treeHeights[rightTreeColumn] < treeHeights[leftTreeColumn]) {
                visibleTrees.add(TreeHeight(row, leftTreeColumn, treeHeights[leftTreeColumn]))
                rightTreeColumn = leftTreeColumn
                leftTreeColumn = rightTreeColumn - 1
            } else {
                leftTreeColumn--
            }
        }

        return visibleTrees
    }

    fun countScenicScores(treeGrid: List<String>): List<Int> {
        val scenicScores = mutableListOf<Int>()
        treeGrid.forEachIndexed { row, treeRow ->
            val treeHeights = treeHeights(treeRow)
            treeHeights.forEachIndexed { column, height ->
                scenicScores.add(scenicScoreOf(TreeHeight(row, column, height), treeGrid))
            }
        }

        return scenicScores
    }

    fun scenicScoreOf(tree: TreeHeight, treeGrid: List<String>): Int {
        val treeRow = tree.treeRow
        val treeColumn = tree.treeColumn
        val rowRightOfTree = treeGrid[treeRow].substring(treeColumn + 1)
        val rowLeftOfTree = treeGrid[treeRow].substring(0, treeColumn).reversed()
        val rowAboveTree = columnToRow(treeGrid, treeColumn).substring(0, treeRow).reversed()
        val rowBelowTree = columnToRow(treeGrid, treeColumn).substring(treeRow + 1)
        val treeHeight = tree.treeHeight

        return scenicScoreOfRow(rowRightOfTree, treeHeight) *
                scenicScoreOfRow(rowLeftOfTree, treeHeight) *
                scenicScoreOfRow(rowAboveTree, treeHeight) *
                scenicScoreOfRow(rowBelowTree, treeHeight)
    }

    private fun scenicScoreOfRow(treeRow: String, treeHeight: Int): Int {
        val treeHeights = treeHeights(treeRow)
        val numberOfBlockingTrees = treeHeights.indexOfFirst { it >= treeHeight }

        return if (numberOfBlockingTrees == -1) treeHeights.size else numberOfBlockingTrees + 1
    }

    private fun columnToRow(treeGrid: List<String>, columnNumber: Int) =
        treeGrid.map { treeRow -> treeRow[columnNumber] }.joinToString("")

    private fun treeHeights(treeRow: String) =
        treeRow.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
}

fun main() {
    val treeGrid = File("day8-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day8()

    println("Number of visible trees: ${app.countVisibleTrees(treeGrid)}")
    println("Best scenic score: ${app.countScenicScores(treeGrid).maxOrNull()}")
}
