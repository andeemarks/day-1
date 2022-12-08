package advent_of_code

import java.io.File
import java.lang.IllegalStateException

private const val START_OF_PACKET_LENGTH = 4
private const val START_OF_MESSAGE_LENGTH = 14

class Day6 {
    fun distanceToStartOfPacket(sequence: String): Int {
        return distanceToStartOfSequence(sequence, START_OF_PACKET_LENGTH, ::isStartOfPacket)
    }

    fun distanceToStartOfMessage(sequence: String): Int {
        return distanceToStartOfSequence(sequence, START_OF_MESSAGE_LENGTH, ::isStartOfMessage)
    }

    private fun distanceToStartOfSequence(sequence: String, sequenceLength: Int, sequenceFinder: (sequence: String) -> Boolean): Int {
        for (i in 0..sequence.length - sequenceLength) {
            if (sequenceFinder(sequence.substring(i, i + sequenceLength))) {
                return i + sequenceLength
            }
        }
        throw IllegalStateException()
    }

    fun isStartOfPacket(sequence: String): Boolean {
        return isUniqueSequenceOfLength(sequence, START_OF_PACKET_LENGTH)
    }

    fun isStartOfMessage(sequence: String): Boolean {
        return isUniqueSequenceOfLength(sequence, START_OF_MESSAGE_LENGTH)
    }

    private fun isUniqueSequenceOfLength(sequence: String, expectedLength: Int) =
        (sequence.length == expectedLength) && sequence.toSet().size == expectedLength

}

fun main() {
    val stream = File("day6-input.txt").inputStream().readBytes().toString(Charsets.UTF_8)
    val day6 = Day6()

    val packetDistance = day6.distanceToStartOfPacket(stream)
    println("Distance to start of packet $packetDistance")

    val messageDistance = day6.distanceToStartOfMessage(stream)
    println("Distance to start of message $messageDistance")
}