package advent_of_code

import java.io.File
import java.lang.IllegalStateException

private const val START_OF_PACKET_LENGTH = 4

class Day6 {
    fun isStartOfPacket(sequence: String): Boolean {
        return (sequence.length == START_OF_PACKET_LENGTH) && sequence.toSet().size == START_OF_PACKET_LENGTH
    }

    fun distanceToStartOfPacket(sequence: String): Int {
        for (i in 0..sequence.length - START_OF_PACKET_LENGTH) {
            if (isStartOfPacket(sequence.substring(i, i + START_OF_PACKET_LENGTH))) {
                return i + START_OF_PACKET_LENGTH // URGH - so many off by one errors here :-(
            }
        }
        throw IllegalStateException()
    }

}

fun main() {
    val stream = File("day6-input.txt").inputStream().readBytes().toString(Charsets.UTF_8)
    val day6 = Day6()

    val distance = day6.distanceToStartOfPacket(stream)

    println("Distance to start of packet $distance")
}