package advent_of_code

import advent_of_code.day8.TreeHeight
import java.io.File

class Day8 {
    fun findVisibleTreesInRow(treeRow: String, rowIndex: Int = 0): List<TreeHeight> {
        val treeHeights = treeRow.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
        val visibleTrees = mutableSetOf<TreeHeight>()
        visibleTrees.add(TreeHeight(rowIndex, 0, treeHeights.first()))
        visibleTrees.addAll(findVisibleTreesFromLeft(treeHeights, rowIndex))
        visibleTrees.addAll(findVisibleTreesFromRight(treeHeights, rowIndex))
        visibleTrees.add(TreeHeight(rowIndex, treeHeights.size - 1, treeHeights.last()))

        return visibleTrees.toMutableList()
    }

    private fun findVisibleTreesFromLeft(treeHeights: List<Int>, rowIndex: Int): Set<TreeHeight> {
        var leftTreeIndex = 0
        var rightTreeIndex = leftTreeIndex + 1
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

        val treeColumn = columnToRow(treeGrid, columnNumber)

        return findVisibleTreesInRow(treeColumn, columnNumber).map { it.flip() }
    }

    private fun columnToRow(treeGrid: List<String>, columnNumber: Int) =
        treeGrid.map { treeRow -> treeRow[columnNumber] }.joinToString("")

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

    fun scenicScoreOf(tree: TreeHeight, treeGrid: List<String>): Int {
        val treeRow = tree.treeRow
        val treeColumn = tree.treeColumn
        val rowRight = treeGrid[treeRow].substring(treeColumn + 1)
        val rowLeft = treeGrid[treeRow].substring(0, treeColumn).reversed()
        val rowUp = columnToRow(treeGrid, treeColumn).substring(0, treeRow).reversed()
        val rowDown = columnToRow(treeGrid, treeColumn).substring(treeRow + 1)
        val treeHeight = tree.treeHeight

        return scenicScoreOfRow(rowRight, treeHeight) *
                scenicScoreOfRow(rowLeft, treeHeight) *
                scenicScoreOfRow(rowUp, treeHeight) *
                scenicScoreOfRow(rowDown, treeHeight)
    }

    private fun scenicScoreOfRow(treeRow: String, treeHeight: Int): Int {
        val treeHeights = treeRow.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
        val numberOfBlockingTrees = treeHeights.indexOfFirst { it >= treeHeight }

        return if (numberOfBlockingTrees == -1) treeHeights.size else numberOfBlockingTrees + 1
    }

    fun countScenicScores(treeGrid: List<String>): List<Int> {
        val scenicScores = mutableListOf<Int>()
        treeGrid.forEachIndexed { rowIndex, treeRow ->
            val treeHeights = treeRow.split("").filter { it.isNotEmpty() }.map { it.toInt() }.toMutableList()
            treeHeights.forEachIndexed { columnIndex, treeHeight ->
                scenicScores.add(scenicScoreOf(TreeHeight(rowIndex, columnIndex, treeHeight), treeGrid))
            }
        }

        return scenicScores
    }
}

fun main() {
    val treeGrid = File("day8-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day8()

    println("Number of visible trees: ${app.countVisibleTrees(treeGrid)}")
    println("Best scenic score: ${app.countScenicScores(treeGrid).maxOrNull()}")
}
