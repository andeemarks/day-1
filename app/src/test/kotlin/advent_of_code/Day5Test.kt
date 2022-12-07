package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class Day5Test {

    @Test
    fun v9000canMoveMultipleCratesBetweenStacks() {
        val fromStack = ArrayDeque(listOf("N", "Z"))
        val toStack = ArrayDeque(listOf("D", "C", "M"))
        Day5().moveCratesV9000(2, fromStack, toStack)

        assertTrue(fromStack.isEmpty())
        assertEquals(listOf("Z", "N", "D", "C", "M"), toStack)

    }

    @Test
    fun v9001canMoveMultipleCratesBetweenStacks() {
        val fromStack = ArrayDeque(listOf("N", "Z"))
        val toStack = ArrayDeque(listOf("D", "C", "M"))
        Day5().moveCratesV9001(2, fromStack, toStack)

        assertTrue(fromStack.isEmpty())
        assertEquals(listOf("N", "Z", "D", "C", "M"), toStack)

    }

    @Test
    fun canMoveASingleCrateBetweenStacks() {
        val fromStack = ArrayDeque(listOf("N", "Z"))
        val toStack = ArrayDeque(listOf("D", "C", "M"))
        Day5().moveCrate(fromStack, toStack)

        assertEquals(listOf("Z"), fromStack)
        assertEquals(listOf("N", "D", "C", "M"), toStack)

    }

    @Test
    fun canMoveASingleCrateToAnEmptyStack() {
        val fromStack = ArrayDeque(listOf("N", "Z"))
        val toStack = ArrayDeque<String>()
        Day5().moveCrate(fromStack, toStack)

        assertEquals(listOf("Z"), fromStack)
        assertEquals(listOf("N"), toStack)

    }

    @Test
    fun cannotMoveACrateFromAnEmptyStack() {
        val fromStack = ArrayDeque<String>()
        val toStack = ArrayDeque<String>()

        assertFailsWith<NoSuchElementException> { Day5().moveCrate(fromStack, toStack) }

    }

    @Test
    fun knowsWhatCrateIsOnTheTopOfAStack() {
        assertEquals("N", Day5().topOfStack(ArrayDeque(listOf("N", "Z"))))
        assertEquals("Z", Day5().topOfStack(ArrayDeque(listOf("Z"))))
    }

    @Test
    fun canExtractComponentsOfStep() {
        assertEquals(Triple(2, 2, 1), Day5().parseStep("move 2 from 2 to 1"))
        assertEquals(Triple(1, 1, 2), Day5().parseStep("move 1 from 1 to 2"))
    }

    @Test
    fun understandsStackLayout() {
        val stackLayout = listOf(
            "    [P]                 [Q]     [T]\n",
            "[F] [N]             [P] [L]     [M]\n",
            "[H] [T] [H]         [M] [H]     [Z]\n",
            "[M] [C] [P]     [Q] [R] [C]     [J]\n",
            "[T] [J] [M] [F] [L] [G] [R]     [Q]\n",
            "[V] [G] [D] [V] [G] [D] [N] [W] [L]\n",
            "[L] [Q] [S] [B] [H] [B] [M] [L] [D]\n",
            "[D] [H] [R] [L] [N] [W] [G] [C] [R]\n",
            " 1   2   3   4   5   6   7   8   9\n")
        val stacks: Map<String, ArrayDeque<String>> = Day5().parseStacks(stackLayout)

        assertEquals(9, stacks.size)
        assertEquals(ArrayDeque(listOf("F", "H", "M", "T", "V", "L", "D")), stacks["1"])
        assertEquals(ArrayDeque(listOf("P", "N", "T", "C", "J", "G", "Q", "H")), stacks["2"])
        assertEquals(ArrayDeque(listOf("F", "V", "B", "L")), stacks["4"])
    }

    @Test
    fun canParseStackRows() {
        val stackRow = Day5().parseStackRow("[M] [C] [P]     [Q] [R] [C]     [J]\n")
        val expected = listOf("M", "C", "P", "", "Q", "R", "C", "", "J")

        assertEquals(expected, stackRow)
    }
}
