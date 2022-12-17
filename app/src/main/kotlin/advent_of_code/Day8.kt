package advent_of_code

import java.io.File
import java.lang.Integer.min

class Day8 {
    fun countVisibleTrees(treeGrid: List<String>): Int {
        return treeGrid.size * treeGrid[0].length
    }

    fun findVisibleTreesInRow(treeRow: String): Int {
        var treeHeights = treeRow.map { it.code }.toMutableList()
        val smallestBoundaryHeight = min(treeHeights.first(), treeHeights.last())
        treeHeights = treeHeights.dropWhile { it < smallestBoundaryHeight }.toMutableList()
        var tree1Index = 0
        var tree2Index = 1
        val visibleTrees = mutableListOf<Int>()
        visibleTrees.add(treeHeights.first())
        while (tree2Index < treeHeights.size) {
            if (treeHeights[tree1Index] < treeHeights[tree2Index]) {
                visibleTrees.add(treeHeights[tree2Index])
                tree1Index++
                tree2Index++
            } else {
                treeHeights.removeAt(tree2Index)
            }
        }

        visibleTrees.add(treeHeights.last())

        return min(visibleTrees.size, treeRow.length)
    }

    fun findVisibleTreesInColumn(treeGrid: List<String>, columnNumber: Int): Int {
        require (columnNumber in 0 until(treeGrid[0].length))

        val treeColumn = treeGrid.map { treeRow -> treeRow[columnNumber]}.joinToString("")

        return findVisibleTreesInRow(treeColumn)
    }
}

fun main() {
    val treeGrid = File("day8-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day8()


}
