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
    fun canParseAValidLSResult() {
        val day7 = Day7()

        val result = day7.parseResult(listOf("dir a", "14848514 b.txt", "8504156 c.dat", "dir d"))
        assertEquals(4, result.size)
        assertEquals(LSResultLine.DIR, result[0].type)
        assertEquals("a", result[0].name)
        assertEquals(LSResultLine.FILE, result[1].type)
        assertEquals("b.txt", result[1].name)
        assertEquals(14848514, result[1].size)
    }

    @Test
    fun canParseLSResultLine() {
        var resultLine = LSResultLine("14848514 b.txt")

        assertEquals(LSResultLine.FILE, resultLine.type)
        assertEquals(14848514, resultLine.size)
        assertEquals("b.txt", resultLine.name)

        resultLine = LSResultLine("dir e")

        assertEquals(LSResultLine.DIR, resultLine.type)
        assertEquals(0, resultLine.size)
        assertEquals("e", resultLine.name)
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

    @Test
    fun cdCommandsKeepTheirArguments() {
        val day7 = Day7()

        assertEquals(CDCommand.PARENT, (day7.parseCommand("$ cd ..") as CDCommand).argument)
        assertEquals(CDCommand.ROOT, (day7.parseCommand("$ cd /") as CDCommand).argument)
        assertEquals("foo", (day7.parseCommand("$ cd foo") as CDCommand).argument)
    }
}
