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
        assertEquals(LSResultLine.FILE, result[2].type)
        assertEquals("c.dat", result[2].name)
        assertEquals(8504156, result[2].size)
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

    @Test
    fun treeStartsWithOnlyTheRootDirectory() {
        val day7 = Day7()

        assertNotNull(day7.tree.root)
        assertEquals(day7.tree.current, day7.tree.root)
        assertEquals(1, day7.tree.size())
    }

    @Test
    fun treeAddsRelativeDirectoriesWhenNavigatingToNewDir() {
        val day7 = Day7()

        val command = day7.parseCommand("$ cd foo") as CDCommand
        day7.pushDirectory(command)

        val tree = day7.tree
        assertEquals(1, tree.root.children.size)
        assertEquals(2, tree.size())
        assertEquals(Node("foo", tree.current.level + 1), tree.current)
        assertEquals(tree.root, tree.root.children[0].parent)
    }

    @Test
    fun treeChangesCurrentDirectoryWhenNavigatingToRoot() {
        val day7 = Day7()

        day7.pushDirectory(day7.parseCommand("$ cd foo") as CDCommand)
        val tree = day7.tree
        assertEquals(Node("foo", tree.current.level + 1), tree.current)

        day7.pushDirectory(day7.parseCommand("$ cd /") as CDCommand)
        assertEquals(tree.root, tree.current)
        assertEquals(3, tree.size())

    }

    @Test
    fun treeChangesCurrentDirectoryWhenNavigatingToParent() {
        val day7 = Day7()

        day7.pushDirectory(day7.parseCommand("$ cd foo") as CDCommand)
        day7.pushDirectory(day7.parseCommand("$ cd bar") as CDCommand)
        val tree = day7.tree
        assertEquals(Node("bar", tree.current.level + 1), tree.current)

        day7.pushDirectory(day7.parseCommand("$ cd ..") as CDCommand)
        assertEquals(Node("foo", tree.current.level + 1), tree.current)
        assertEquals(3, tree.size())

    }

    @Test
    fun treeAddsRelativeDirectoriesToCurrentNode() {
        val day7 = Day7()

        day7.pushDirectory(day7.parseCommand("$ cd foo") as CDCommand)
        day7.pushDirectory(day7.parseCommand("$ cd bar") as CDCommand)

        val tree = day7.tree
        assertEquals(1, tree.root.children.size)
        assertEquals(Node("bar", tree.current.level + 1), tree.current)
        assertEquals(Node("foo", tree.current.level + 1), tree.current.parent)
        assertEquals(3, tree.size())

    }

    @Test
    fun treeAddsDirectoryContentsToCurrentNode() {
        val day7 = Day7()

        day7.pushDirectoryContents(day7.parseResult(listOf("dir a", "14848514 b.txt", "8504156 c.dat", "dir d")))

        val tree = day7.tree
        val contents = tree.root.children
        assertEquals(4, contents.size)
        assertTrue(contents.contains(Node("a", tree.current.level + 1)))
        assertTrue(contents.contains(Node("b.txt", tree.current.level + 1)))
        assertTrue(contents.contains(Node("c.dat", tree.current.level + 1)))
        assertTrue(contents.contains(Node("d", tree.current.level + 1)))
        assertEquals(5, tree.size())

    }
}
