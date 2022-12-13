package advent_of_code

import advent_of_code.day7.*
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
        assertEquals(LSResult.LSResultLine.DIR, result[0].type)
        assertEquals("a", result[0].name)
        assertEquals(LSResult.LSResultLine.FILE, result[2].type)
        assertEquals("c.dat", result[2].name)
        assertEquals(8504156, result[2].size)
    }

    @Test
    fun canParseLSResultLine() {
        var resultLine = LSResult.LSResultLine("14848514 b.txt")

        assertEquals(LSResult.LSResultLine.FILE, resultLine.type)
        assertEquals(14848514, resultLine.size)
        assertEquals("b.txt", resultLine.name)

        resultLine = LSResult.LSResultLine("dir e")

        assertEquals(LSResult.LSResultLine.DIR, resultLine.type)
        assertEquals(0, resultLine.size)
        assertEquals("e", resultLine.name)
    }

    @Test
    fun failsToParseInvalidCommands() {
        val day7 = Day7()

        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("cd ..") }
        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("$ cp ..") }
        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("$ cd") }
        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("$ cd .. /") }
        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("$ ls ..") }
        assertFailsWith(IllegalArgumentException::class) { day7.parseCommand("\$ls") }
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
        assertEquals(2, tree.size())

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

    @Test
    fun processASmallFilesystemCorrectly() {
        val day7 = Day7()
        val tree = day7.processFilesystem(
            listOf(
                "$ ls",
                "dir fbmww",
                "dir jsst",
                "206602 shlhgj.cln",
                "$ cd fbmww",
                "$ ls",
                "179734 fll",
                "$ cd ..",
                "$ cd jsst",
                "$ ls",
                "dir flp",
                "$ cd flp",
                "$ ls",
                "32274 gctgt.stn",
                "67650 ggvj.bwz"
            )
        )

        assertEquals(8, tree.size())
        assertEquals(3, tree.root.children.size)

        val file = tree.root.children[0] as FileNode
        assertEquals("shlhgj.cln", file.name)
        assertEquals(206602, file.size)

        val dir1 = tree.root.children[1]
        assertEquals("fbmww", dir1.name)
        assertEquals(1, dir1.children.size)
        assertEquals(179734, dir1.contentsSize)

        val dir2 = tree.root.children[2]
        assertEquals("jsst", dir2.name)
        assertEquals(1, dir2.children.size)

        val subDir1 = dir2.children[0]
        assertEquals("flp", subDir1.name)
        assertEquals(2, subDir1.children.size)
        assertEquals(32274 + 67650, subDir1.contentsSize)
        assertEquals(32274 + 67650, dir2.contentsSize)

        assertEquals(file.size + dir1.contentsSize + dir2.contentsSize, tree.root.contentsSize)
    }
}
