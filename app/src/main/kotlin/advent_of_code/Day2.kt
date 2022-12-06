package advent_of_code

import java.io.File

enum class Choice(val label: String) {
    ROCK("X"), PAPER("Y"), SCISSORS("Z");

    companion object {
        infix fun from(value: String): Choice = Choice.values().firstOrNull { it.label == value }!!
    }
}

enum class Result(val result: String, val baseScore: Int) {
    WIN("Z", 6), LOSS("X", 0), DRAW("Y", 3);

    companion object {
        infix fun from(value: String): Result = Result.values().firstOrNull { it.result == value }!!
    }
}

enum class OpponentChoice(val label: String) {
    ROCK("A") {
        override fun resultAgainst(choice: Choice): Result =
            mapOf(Choice.ROCK to Result.DRAW, Choice.PAPER to Result.WIN).getOrDefault(choice, Result.LOSS)

        override fun choiceForResult(result: Result): Choice =
            mapOf(Result.DRAW to Choice.ROCK, Result.WIN to Choice.PAPER, Result.LOSS to Choice.SCISSORS)[result]!!
    },

    PAPER("B") {
        override fun resultAgainst(choice: Choice): Result =
            mapOf(Choice.PAPER to Result.DRAW, Choice.SCISSORS to Result.WIN).getOrDefault(choice, Result.LOSS)

        override fun choiceForResult(result: Result): Choice =
            mapOf(Result.DRAW to Choice.PAPER, Result.WIN to Choice.SCISSORS, Result.LOSS to Choice.ROCK)[result]!!

    },

    SCISSORS("C") {
        override fun resultAgainst(choice: Choice): Result =
            mapOf(Choice.SCISSORS to Result.DRAW, Choice.ROCK to Result.WIN).getOrDefault(choice, Result.LOSS)

        override fun choiceForResult(result: Result): Choice =
            mapOf(Result.DRAW to Choice.SCISSORS, Result.WIN to Choice.ROCK, Result.LOSS to Choice.PAPER)[result]!!
    };

    abstract fun resultAgainst(choice: Choice): Result
    abstract fun choiceForResult(result: Result): Choice

    companion object {
        infix fun from(value: String): OpponentChoice = OpponentChoice.values().firstOrNull { it.label == value }!!
    }
}

class Day2 {
    fun scoreForYou(yourChoice: Choice): Int {
        val baseScores = mapOf(Choice.ROCK to 1, Choice.PAPER to 2, Choice.SCISSORS to 3)

        return baseScores[yourChoice]!!
    }

    fun roundScore(opponent: OpponentChoice, you: Choice): Int {
        val result = result(opponent, you)

        return result.baseScore + scoreForYou(you)
    }

    fun result(opponent: OpponentChoice, you: Choice): Result {
        return opponent.resultAgainst(you)
    }

    fun scoreForNeededResult(opponent: OpponentChoice, result: Result): Int {
        val choice = choiceForResult(opponent, result)

        return roundScore(opponent, choice)
    }

    fun choiceForResult(opponent: OpponentChoice, result: Result): Choice {
        return opponent.choiceForResult(result)
    }
}

fun main() {
    val rounds = File("day2-input.txt").inputStream().readBytes().toString(Charsets.UTF_8).trim().split("\n")
    val version1Scores = rounds.map {
        val choices = it.split(" ")
        Day2().roundScore(OpponentChoice.from(choices[0]), Choice.from(choices[1]))
    }

    println("Version 1 ${version1Scores.sum()}")

    val version2Scores = rounds.map {
        val fields = it.split(" ")
        val opponentChoice = fields.first()
        val expectedResult = fields.last()
        Day2().scoreForNeededResult(OpponentChoice.from(opponentChoice), Result.from(expectedResult))
    }

    println("Version 2 ${version2Scores.sum()}")
}
