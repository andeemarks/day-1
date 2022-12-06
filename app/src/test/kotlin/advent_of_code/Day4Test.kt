package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Test {

    @Test
    fun canBuildRangesFromSectionAssignments() {
        var range: IntRange = Day4().rangeFrom("2-4")
        assertEquals( 2, range.first)
        assertEquals( 4, range.last)

        range = Day4().rangeFrom("42-79")
        assertEquals( 42, range.first)
        assertEquals( 79, range.last)
    }

    @Test
    fun canParseASetOfAssignments() {
        val assignments = Day4().parse("1-93,1-2")
        assertEquals( IntRange(1, 93), assignments.first)
        assertEquals( IntRange(1, 2), assignments.second)
    }

    @Test
    fun knowsWhenRangesOverlap() {
        assertTrue(Day4().haveCompleteOverlap(IntRange(7, 9), IntRange(7, 8)))
        assertTrue(Day4().haveCompleteOverlap(IntRange(7, 8), IntRange(7, 9)))
    }

    @Test
    fun knowsWhenRangesHaveNoOverlap() {
        assertFalse(Day4().haveCompleteOverlap(IntRange(7, 9), IntRange(6, 8)))
        assertFalse(Day4().haveCompleteOverlap(IntRange(6, 8), IntRange(7, 9)))
    }

    @Test
    fun knowsWhenRangesPartiallyOverlap() {
        assertTrue(Day4().havePartialOverlap(IntRange(5, 7), IntRange(7, 9)))
        assertTrue(Day4().havePartialOverlap(IntRange(7, 9), IntRange(5, 7)))
        assertTrue(Day4().havePartialOverlap(IntRange(2, 8), IntRange(3, 7)))
        assertTrue(Day4().havePartialOverlap(IntRange(2, 6), IntRange(4, 8)))
    }

    @Test
    fun knowsWhenRangesHaveNoPartialOverlap() {
        assertFalse(Day4().havePartialOverlap(IntRange(2, 4), IntRange(6, 8)))
        assertFalse(Day4().havePartialOverlap(IntRange(4, 5), IntRange(2, 3)))
    }
}
