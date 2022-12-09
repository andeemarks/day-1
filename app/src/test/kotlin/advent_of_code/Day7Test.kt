package advent_of_code

import java.lang.IllegalArgumentException
import kotlin.test.*

class Day7Test {

    @Test
    fun canParseAValidCDCommand() {
        val day7 = Day7()

        assertIs<CDCommand>(day7.parseCommand("$ cd .."))
        assertIs<CDCommand>(day7.parseCommand("$ cd /"))
        assertIs<CDCommand>(day7.parseCommand("$ cd foo"))
        assertIs<LSCommand>(day7.parseCommand("$ ls"))
    }

    @Test
    fun failsToParseInvalidCommands() {
        val day7 = Day7()

        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("cd ..")}
        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("$ cp ..")}
        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("$ cd")}
        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("$ cd .. /")}
        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("$ ls ..")}
        assertFailsWith(IllegalArgumentException::class) {day7.parseCommand("\$ls")}
    }
}
