package advent_of_code.day8

class TreeHeight(val treeRow: Int, val treeColumn: Int, val treeHeight: Int) {
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

    fun swapRowAndColumn(): TreeHeight {
        return TreeHeight(treeColumn, treeRow, treeHeight)
    }
}