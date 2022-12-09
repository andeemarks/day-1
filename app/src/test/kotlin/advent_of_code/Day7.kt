package advent_of_code

import java.lang.IllegalArgumentException

interface Command {
    fun build(arguments: List<String>): Command

}

class CDCommand : Command {
    override fun build(arguments: List<String>): Command {
        if (arguments.size != 1) {
            throw IllegalArgumentException()
        }
        return CDCommand()
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

class Day7 {
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

}
