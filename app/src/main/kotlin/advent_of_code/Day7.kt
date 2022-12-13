package advent_of_code

import java.io.File

interface Command {
    fun build(arguments: List<String>): Command

}

class LSResultLine(line: String) {

    val size: Int
    val name: String
    val type: String

    init {
        val lineParts = line.split(" ")
        if (lineParts[0] == DIR) {
            this.type = DIR
            this.size = 0
            this.name = lineParts[1]
        } else {
            this.type = FILE
            this.size = lineParts[0].toInt()
            this.name = lineParts[1]
        }
    }

    companion object {
        const val DIR: String = "dir"
        const val FILE: String = "file"
    }
}

class LSResult(resultLines: List<String>) {

    operator fun get(i: Int): LSResultLine {
        return lines[i]
    }

    private var lines: List<LSResultLine>
    var size: Int

    init {
        lines = resultLines.map { LSResultLine(it) }
        size = lines.size
    }

}

class CDCommand(val argument: String = "") : Command {

    override fun build(arguments: List<String>): Command {
        if (arguments.size != 1) {
            throw IllegalArgumentException()
        }

        return CDCommand(arguments[0])
    }

    companion object {
        const val ROOT: String = "/"
        const val PARENT: String = ".."
    }
}

class LSCommand : Command {
    override fun build(arguments: List<String>): Command {
        if (arguments.isNotEmpty()) {
            throw IllegalArgumentException()
        }
        return LSCommand()
    }
}

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

    fun increaseContentSize(additionalContentSize: Int) {
        println("Increasing $name size by $additionalContentSize")
        contentsSize += additionalContentSize
        if (parent != null) parent!!.increaseContentSize(additionalContentSize)
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

class DirTree {
    fun size(): Int {
        val visitor = NodeCounter()
        visitNode(root, visitor)

        return visitor.nodesVisited
    }

    interface NodeVisitor {
        fun visit(node: Node)
    }

    class NodeCounter : NodeVisitor {
        var nodesVisited = 0
        override fun visit(node: Node) {
            nodesVisited++
        }
    }

    class NodePrinter : NodeVisitor {
        override fun visit(node: Node) {
            println(node.toString())
        }
    }

    class SmallDirFinder : NodeVisitor {
        var smallDirs = mutableListOf<DirNode>()

        override fun visit(node: Node) {
            if (node is DirNode) {
                if (node.contentsSize <= 100000) {
                    smallDirs.add(node)
                }
            }
        }
    }

    private fun visitNode(node: Node, visitor: NodeVisitor) {
        visitor.visit(node)
        val children = node.children
        children.forEach { visitNode(it, visitor) }
    }

    fun show() {
        visitNode(root, NodePrinter())
    }

    fun findSmallDirs(): List<DirNode> {
        val smallDirFinder = SmallDirFinder()
        visitNode(root, smallDirFinder)

        return smallDirFinder.smallDirs
    }

    val root: Node = Node("/")
    var current: Node = this.root
}

class Day7(val commands: List<String> = emptyList()) {
    val tree: DirTree = DirTree()

    fun parseCommand(command: String): Command {
        val commandParts = command.split(" ")

        if (commandParts[0] != "$") throw IllegalArgumentException()

        if (commandParts[1] == "cd") {
            return CDCommand().build(commandParts.drop(2))
        } else if (commandParts[1] == "ls") {
            return LSCommand().build(commandParts.drop(2))
        }

        throw IllegalArgumentException("Could not identify command '$command' with parts '$commandParts'")
    }

    fun parseResult(resultLines: List<String>): LSResult {
        return LSResult(resultLines)
    }

    fun pushDirectory(cdCommand: CDCommand): Node {
        if (cdCommand.argument == CDCommand.ROOT) {
            return tree.current
        }

        if (cdCommand.argument == CDCommand.PARENT) {
            tree.current = tree.current.parent!!
        } else {
            val dir = DirNode(cdCommand.argument, tree.current.level + 1)
            dir.parent = tree.current
            tree.current.children.add(dir)
            tree.current = dir
        }

        return tree.current

    }

    fun pushDirectoryContents(contents: LSResult) {
        val currentNode = tree.current
        for (i: Int in 0 until contents.size) {
            currentNode.children.add(FileNode(contents[i].name, currentNode.level + 1, contents[i].size))
            currentNode.increaseContentSize(contents[i].size)
        }
    }

    fun processFilesystem(commands: List<String>): DirTree {
        val input = commands.filter { !it.startsWith("dir") }
        input.forEach {
            if (it.startsWith("$")) {
                val command = parseCommand(it)
                if (command is CDCommand) {
                    pushDirectory(command)
                }
            } else {
                val result = parseResult(listOf(it))
                pushDirectoryContents(result)
            }

        }

        return tree
    }
}

fun main() {
    val app = Day7(File("day7-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n"))

    val tree = app.processFilesystem(app.commands)
    tree.show()
    val smallDirs = tree.findSmallDirs()
    println("Size of small dirs ${smallDirs.sumOf { it.contentsSize }}")

    println("Number of entries ${app.tree.size()}")
    println("Size of entire tree ${app.tree.root.contentsSize}")
}
