package advent_of_code

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    @Test
    fun understandsBaseScoreForEachChoice() {
        assertEquals(2, Day2().scoreForYou(Choice.PAPER))
        assertEquals(1, Day2().scoreForYou(Choice.ROCK))
        assertEquals(3, Day2().scoreForYou(Choice.SCISSORS))
    }

    @Test
    fun understandsWhichChoicesBeatOthers() {
        assertEquals(Result.LOSS, Day2().result(OpponentChoice.PAPER, Choice.ROCK))
        assertEquals(Result.DRAW, Day2().result(OpponentChoice.SCISSORS, Choice.SCISSORS))
        assertEquals(Result.WIN, Day2().result(OpponentChoice.ROCK, Choice.PAPER))
    }

    @Test
    fun calculatesScoreForWinningRoundBasedOnChoices() {
        assertEquals(6 + 2, Day2().roundScore(OpponentChoice.ROCK, Choice.PAPER))
        assertEquals(6 + 3, Day2().roundScore(OpponentChoice.PAPER, Choice.SCISSORS))
        assertEquals(6 + 1, Day2().roundScore(OpponentChoice.SCISSORS, Choice.ROCK))
    }

    @Test
    fun calculatesScoreForLosingRoundBasedOnChoices() {
        assertEquals(1, Day2().roundScore(OpponentChoice.PAPER, Choice.ROCK))
        assertEquals(3, Day2().roundScore(OpponentChoice.ROCK, Choice.SCISSORS))
        assertEquals(2, Day2().roundScore(OpponentChoice.SCISSORS, Choice.PAPER))
    }

    @Test
    fun calculatesScoreForDrawnRoundBasedOnChoices() {
        assertEquals(3 + 2, Day2().roundScore(OpponentChoice.PAPER, Choice.PAPER))
        assertEquals(3 + 1, Day2().roundScore(OpponentChoice.ROCK, Choice.ROCK))
        assertEquals(3 + 3, Day2().roundScore(OpponentChoice.SCISSORS, Choice.SCISSORS))
    }

    @Test
    fun calculatesChoiceNeededToGetResultBasedOnOpponentChoice() {
        assertEquals(Choice.SCISSORS, Day2().choiceForResult(OpponentChoice.PAPER, Result.WIN))
        assertEquals(Choice.PAPER, Day2().choiceForResult(OpponentChoice.PAPER, Result.DRAW))
        assertEquals(Choice.ROCK, Day2().choiceForResult(OpponentChoice.PAPER, Result.LOSS))

        assertEquals(Choice.PAPER, Day2().choiceForResult(OpponentChoice.ROCK, Result.WIN))
        assertEquals(Choice.ROCK, Day2().choiceForResult(OpponentChoice.ROCK, Result.DRAW))
        assertEquals(Choice.SCISSORS, Day2().choiceForResult(OpponentChoice.ROCK, Result.LOSS))

        assertEquals(Choice.ROCK, Day2().choiceForResult(OpponentChoice.SCISSORS, Result.WIN))
        assertEquals(Choice.SCISSORS, Day2().choiceForResult(OpponentChoice.SCISSORS, Result.DRAW))
        assertEquals(Choice.PAPER, Day2().choiceForResult(OpponentChoice.SCISSORS, Result.LOSS))
    }
}
