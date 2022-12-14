package advent_of_code

import advent_of_code.day7.*
import java.io.File

class Day7 {
    val tree: FilesystemTree = FilesystemTree()

    private fun String.isCDCommand(): Boolean {
        return this.startsWith("$ cd")
    }

    private fun String.isLSCommand(): Boolean {
        return this.startsWith("$ ls")
    }

    private fun String.isCommand(): Boolean {
        return this.startsWith("$")
    }

    private fun String.isDirEntry(): Boolean {
        return this.startsWith("dir ")
    }

    fun parseCommand(command: String): Command {
        val commandParts = command.split(" ")

        if (command.isCDCommand()) {
            return CDCommand().build(commandParts.drop(2))
        } else if (command.isLSCommand()) {
            return LSCommand().build(commandParts.drop(2))
        }

        throw IllegalArgumentException("Could not identify command '$command' with parts '$commandParts'")
    }

    fun parseResult(resultLines: List<String>): LSResult {
        return LSResult(resultLines)
    }

    fun processFilesystem(commands: List<String>): FilesystemTree {
        val input = commands.filter { !it.isDirEntry() }
        input.forEach {
            if (it.isCommand()) {
                val command = parseCommand(it)
                if (command is CDCommand) {
                    tree.changeDirectory(command)
                }
            } else {
                val commandOutput = parseResult(listOf(it))
                tree.addFilesToCurrentDirectory(commandOutput)
            }
        }

        return tree
    }
}

fun main() {
    val commands = File("day7-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n")
    val app = Day7()

    val tree = app.processFilesystem(commands)
    println(tree.toString())

    val smallDirs = tree.findSmallDirs()
    println("Size of small dirs ${smallDirs.sumOf { it.contentsSize }}")

    println("Number of entries ${app.tree.size()}")
    println("Size of entire tree ${app.tree.root.contentsSize}")

    val amountToDelete = app.tree.root.contentsSize - 30000000
    println("Finding smallest directory containing at least $amountToDelete")
    val toDelete = tree.findSmallestDirGreaterThan(amountToDelete)
    println("Deleting $toDelete")


}
