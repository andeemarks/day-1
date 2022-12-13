package advent_of_code.day7

interface Command {
    fun build(arguments: List<String>): Command

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
