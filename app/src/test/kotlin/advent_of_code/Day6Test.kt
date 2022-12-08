package advent_of_code

import kotlin.test.*

class Day6Test {

    @Test
    fun canIdentifyAStartOfPacketSequence() {
        val day6 = Day6()

        assertFalse(day6.isStartOfPacket(""))
        assertFalse(day6.isStartOfPacket("m"))
        assertFalse(day6.isStartOfPacket("mj"))
        assertFalse(day6.isStartOfPacket("mjq"))
        assertFalse(day6.isStartOfPacket("mjqj"))
        assertTrue(day6.isStartOfPacket("jqpm"))
    }

    @Test
    fun canIdentifyAStartOfMessageSequence() {
        val day6 = Day6()

        assertTrue(day6.isStartOfMessage("abcdefghijklmn"))
        assertFalse(day6.isStartOfMessage("abcdefghijklm"))
        assertFalse(day6.isStartOfMessage("abcdefghijklmm"))
    }

    @Test
    fun canReportDistanceToStartOfPacket() {
        val day6 = Day6()

        assertEquals(4, day6.distanceToStartOfPacket("jqpm"))
        assertEquals(5, day6.distanceToStartOfPacket("jjqpm"))
        assertEquals(6, day6.distanceToStartOfPacket("jjjqpm"))
        assertEquals(14, day6.distanceToStartOfPacket("jjqqppmmjjjqpm"))
    }
}
