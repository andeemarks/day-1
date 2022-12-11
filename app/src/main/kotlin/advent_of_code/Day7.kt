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

class Node(val name: String, val level: Int = 0) {
    var parent: Node? = null
    val children: MutableList<Node> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        val otherNode = other as Node

        return name == otherNode.name
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
            println("${"  ".repeat(node.level)}* ${node.name}")
        }
    }

    private fun visitNode(node: Node, visitor: NodeVisitor) {
        visitor.visit(node)
        val children = node.children
        children.forEach { visitNode(it, visitor) }
    }

    fun show() {
        val printer = NodePrinter()
        visitNode(root, printer)
    }

    val root: Node = Node("/")
    var current: Node = this.root
}

class Day7(val commands: List<String> = emptyList()) {
    val tree: DirTree = DirTree()

    fun  parseCommand(command: String): Command {
        val commandParts = command.split(" ")

        if (commandParts[0] != "$") throw IllegalArgumentException()

        if (commandParts[1] == "cd") {
            return CDCommand().build(commandParts.drop(2))
        } else if (commandParts[1] == "ls") {
            return LSCommand().build(commandParts.drop(2))
        }

        throw IllegalArgumentException()
    }

    fun parseResult(resultLines: List<String>): LSResult {
        return LSResult(resultLines)
    }

    fun pushDirectory(cdCommand: CDCommand): Node {
        if (cdCommand.argument == CDCommand.PARENT) {
            tree.current = tree.current.parent!!
        } else {
            val dir = Node(cdCommand.argument, tree.current.level + 1)
            dir.parent = tree.current
            tree.current.children.add(dir)
            tree.current = dir
        }

        return tree.current

    }

    fun pushDirectoryContents(contents: LSResult) {
        for (i: Int in 0 until contents.size) {
            tree.current.children.add(Node(contents[i].name, tree.current.level + 1))
        }
    }

}

fun main() {
    val app = Day7(File("day7-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n"))

    val input = app.commands.filter { it.startsWith("$") }
    input.forEach {
        val command = app.parseCommand(it)
        if (command is CDCommand) {
            app.pushDirectory(command)
//        } else if (command is LSCommand) {
//            println("ls")
        }
    }

    app.tree.show()

    println("Number of directories ${app.tree.size()}")
}