package advent_of_code

import advent_of_code.day7.*
import java.io.File

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

        throw IllegalArgumentException("Could not identify command '$command' with parts '$commandParts'")
    }

    fun parseResult(resultLines: List<String>): LSResult {
        return LSResult(resultLines)
    }

    fun processFilesystem(commands: List<String>): DirTree {
        val input = commands.filter { !it.startsWith("dir") }
        input.forEach {
            if (it.startsWith("$")) {
                val command = parseCommand(it)
                if (command is CDCommand) {
                    tree.pushDirectory(command)
                }
            } else {
                val result = parseResult(listOf(it))
                tree.pushDirectoryContents(result)
            }
        }

        return tree
    }
}

fun main() {
    val commands = File("day7-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day7()

    val tree = app.processFilesystem(commands)
    tree.show()

    val smallDirs = tree.findSmallDirs()
    println("Size of small dirs ${smallDirs.sumOf { it.contentsSize }}")

    println("Number of entries ${app.tree.size()}")
    println("Size of entire tree ${app.tree.root.contentsSize}")
}
