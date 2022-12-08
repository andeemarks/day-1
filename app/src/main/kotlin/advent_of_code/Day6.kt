package advent_of_code

import java.io.File
import java.lang.IllegalStateException

private const val START_OF_PACKET_LENGTH = 4
private const val START_OF_MESSAGE_LENGTH = 14

class Day6 {
    fun isStartOfPacket(sequence: String): Boolean {
        return isUniqueSequenceOfLength(sequence, START_OF_PACKET_LENGTH)
    }

    private fun isUniqueSequenceOfLength(sequence: String, expectedLength: Int) =
        (sequence.length == expectedLength) && sequence.toSet().size == expectedLength

    fun distanceToStartOfPacket(sequence: String): Int {
        for (i in 0..sequence.length - START_OF_PACKET_LENGTH) {
            if (isStartOfPacket(sequence.substring(i, i + START_OF_PACKET_LENGTH))) {
                return i + START_OF_PACKET_LENGTH // URGH - so many off by one errors here :-(
            }
        }
        throw IllegalStateException()
    }

    fun distanceToStartOfMessage(sequence: String): Int {
        for (i in 0..sequence.length - START_OF_MESSAGE_LENGTH) {
            if (isStartOfMessage(sequence.substring(i, i + START_OF_MESSAGE_LENGTH))) {
                return i + START_OF_MESSAGE_LENGTH
            }
        }
        throw IllegalStateException()
    }

    fun isStartOfMessage(sequence: String): Boolean {
        return isUniqueSequenceOfLength(sequence, START_OF_MESSAGE_LENGTH)
    }

}

fun main() {
    val stream = File("day6-input.txt").inputStream().readBytes().toString(Charsets.UTF_8)
    val day6 = Day6()

    val packetDistance = day6.distanceToStartOfPacket(stream)
    println("Distance to start of packet $packetDistance")

    val messageDistance = day6.distanceToStartOfMessage(stream)
    println("Distance to start of message $messageDistance")
}