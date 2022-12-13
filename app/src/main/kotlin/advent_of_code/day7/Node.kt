package advent_of_code.day7

open class Node(val name: String, val level: Int = 0) {
    var contentsSize: Int = 0
    var parent: Node? = null
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
        if (parent != null) parent!!.increaseContentSize(additionalContentSize)
    }

    fun add(file: FileNode) {
        children.add(file)
        increaseContentSize(file.size)

    }
}

class DirNode(name: String, level: Int) : Node(name, level)

class FileNode(name: String, level: Int, val size: Int) : Node(name, level) {
    override fun toString(): String {
        val indent = " ".repeat(level)
        val sizeStartPos = 50 - indent.length - name.length
        return String.format("%s└%s %${sizeStartPos}d", indent, name.uppercase(), size)
    }

}
