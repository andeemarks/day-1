package advent_of_code

import java.io.File

class Day5(val input: List<String> = emptyList()) {
    fun moveCrate(fromStack: ArrayDeque<String>, toStack: ArrayDeque<String>) {
        val crate = fromStack.removeFirst()
        toStack.addFirst(crate)
    }

    fun moveCrates(numberOfCrates: Int, fromStack: ArrayDeque<String>, toStack: ArrayDeque<String>) {
        repeat(numberOfCrates) {
            moveCrate(fromStack, toStack)
        }
    }

    fun topOfStack(stack: ArrayDeque<String>): String {
        return stack.first()
    }

    fun parseStep(step: String): Triple<Int, Int, Int> {
        val components = step.split(" ")

        return Triple(components[1].toInt(), components[3].toInt(), components[5].toInt())
    }

    fun parseStacks(stackLayout: List<String>): Map<String, ArrayDeque<String>> {
        val stacks = mutableMapOf<String, ArrayDeque<String>>()

        val layout = stackLayout.reversed()
        val stackNames = layout.first().trim().split(" ").filter { it.isNotEmpty() }
        stackNames.forEach {
            stacks[it.trim()] = ArrayDeque()
        }

        val stackRows = layout.subList(1, layout.size)
        stackRows.forEach {
            val rowContents = parseStackRow(it)
            rowContents.forEachIndexed { i, crate ->
                val stackName = (i + 1).toString()
                if (crate.trim().isNotEmpty()) {
                    stacks[stackName]!!.addFirst(crate)
                }
            }
        }

        return stacks
    }

    fun parseStackRow(stackRow: String): List<String> {
        return stackRow.chunked(4).map { it.trim().drop(1).dropLast(1) }
    }

    fun printStacks(stacks: Map<String, ArrayDeque<String>>) {
        stacks.keys.forEach { key ->
            println("$key -> ${stacks[key]}")
        }
    }

    fun printStackTops(stacks: Map<String, ArrayDeque<String>>) {
        stacks.keys.forEach { key ->
            println("$key -> ${topOfStack(stacks[key]!!)}")
        }
    }
}

fun main() {
    val day5 = Day5(File("day5-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).split("\n"))

    val stackDescriptionEndPosition = day5.input.indexOf("")
    val stackDescription = day5.input.subList(0, stackDescriptionEndPosition)
    val stacks = day5.parseStacks(stackDescription)
    val instructions = day5.input.subList(stackDescriptionEndPosition + 1, day5.input.size - 1)

    instructions.forEachIndexed { i, it ->
        processStep(i, day5, it, stacks)
    }
    day5.printStackTops(stacks)
}

private fun processStep(stepNumber: Int, day5: Day5, step: String, stacks: Map<String, ArrayDeque<String>>) {
    val stepComponents: Triple<Int, Int, Int> = day5.parseStep(step)
    val fromStack = stacks[stepComponents.second.toString()]!!
    val toStack = stacks[stepComponents.third.toString()]!!
    val crateCount = stepComponents.first

    try {
        day5.moveCrates(crateCount, fromStack, toStack)
    } catch (e: Exception) {
        println("Error attempting to execute ($stepNumber) $step on $fromStack and $toStack")
    }
}