package advent_of_code

import kotlin.test.*

class Day6Test {

    @Test
    fun canIdentifyAStartOfPacketSequence() {
        assertFalse(Day6().isStartOfPacket(""))
        assertFalse(Day6().isStartOfPacket("m"))
        assertFalse(Day6().isStartOfPacket("mj"))
        assertFalse(Day6().isStartOfPacket("mjq"))
        assertFalse(Day6().isStartOfPacket("mjqj"))
        assertTrue(Day6().isStartOfPacket("jqpm"))
    }

    @Test
    fun canReportDistanceToStartOfPacket() {
        assertEquals(0, Day6().distanceToStartOfPacket("jqpm"))
        assertEquals(1, Day6().distanceToStartOfPacket("jjqpm"))
        assertEquals(2, Day6().distanceToStartOfPacket("jjjqpm"))
        assertEquals(10, Day6().distanceToStartOfPacket("jjqqppmmjjjqpm"))
    }
}
