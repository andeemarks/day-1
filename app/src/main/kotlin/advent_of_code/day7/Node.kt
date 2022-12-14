package advent_of_code.day7

open class Node(val name: String, val parent: Node? = null) {
    var contentsSize: Int = 0
        private set
    val children: MutableList<Node> = mutableListOf()

    override fun toString(): String {
        val indent = " ".repeat(depth())
        val sizeStartPos = 49 - indent.length - name.length

        return String.format("%s└%s [%${sizeStartPos}d]", indent, name.uppercase(), contentsSize)
    }

    private fun increaseContentSize(additionalContentSize: Int) {
        contentsSize += additionalContentSize
        parent?.increaseContentSize(additionalContentSize)
    }

    fun addFile(file: FileNode) {
        children.add(file)
        increaseContentSize(file.size)
    }

    override fun equals(other: Any?): Boolean {
        val otherNode = other as Node

        return name == otherNode.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)

        return result
    }

    protected fun depth(): Int {
        return findDepth(this, 0)
    }

    private fun findDepth(current: Node, depth: Int): Int {
        if (current.parent == null) return depth

        return findDepth(current.parent, depth + 1)
    }
}

class DirNode(name: String, parent: Node? = null) : Node(name, parent)

class FileNode(name: String, val size: Int, parent: Node) : Node(name, parent) {
    override fun toString(): String {
        val indent = " ".repeat(depth())
        val sizeStartPos = 50 - indent.length - name.length

        return String.format("%s└%s %${sizeStartPos}d", indent, name, size)
    }

}
