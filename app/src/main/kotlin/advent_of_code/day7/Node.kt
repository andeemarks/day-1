package advent_of_code.day7

open class Node(val name: String, val level: Int = 0, val parent: Node? = null) {
    var contentsSize: Int = 0
        private set
    val children: MutableList<Node> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        val otherNode = other as Node

        return name == otherNode.name
    }

    override fun toString(): String {
        val indent = " ".repeat(level)
        val sizeStartPos = 49 - indent.length - name.length
        return String.format("%s└%s [%${sizeStartPos}d]", indent, name.uppercase(), contentsSize)
    }

    private fun increaseContentSize(additionalContentSize: Int) {
        contentsSize += additionalContentSize
        parent?.increaseContentSize(additionalContentSize)
    }

    fun add(file: FileNode) {
        children.add(file)
        increaseContentSize(file.size)

    }
}

class DirNode(name: String, level: Int, parent: Node? = null) : Node(name, level, parent)

class FileNode(name: String, level: Int, val size: Int, parent: Node? = null) : Node(name, level, parent) {
    override fun toString(): String {
        val indent = " ".repeat(level)
        val sizeStartPos = 50 - indent.length - name.length
        return String.format("%s└%s %${sizeStartPos}d", indent, name.uppercase(), size)
    }

}
