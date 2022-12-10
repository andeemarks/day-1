package advent_of_code

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
        val DIR: String = "dir"
        val FILE: String = "file"
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
        val ROOT: String = "/"
        val PARENT: String = ".."
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

class Node(val name: String) {
    var parent: Node? = null
    val children: MutableList<Node> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        val otherNode = other as Node

        return name == otherNode.name
    }
}

class DirTree {
    val root: Node = Node("/")
    var current: Node = this.root
}

class Day7 {
    val tree: DirTree = DirTree()

    fun parseCommand(command: String): Command {
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

    fun pushDirectory(cdCommand: CDCommand) {
        val dir = Node(cdCommand.argument)
        dir.parent = tree.current
        tree.current.children.add(dir)
        tree.current = dir

    }

    fun pushDirectoryContents(contents: LSResult) {
        for (i: Int in 0 until contents.size) {
            tree.current.children.add(Node(contents[i].name))
        }
    }

}
