package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test fun findsMostCalorificElf() {
        val app = App()
        val mostCalories = app.solve("1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000\n")

        assertEquals(24000, mostCalories)
    }

    @Test fun controlBreaksOnNewline() {
        val app = App()
        val groups = app.groupByElf("1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000\n")

        assertEquals(5, groups.size)
        assertEquals(listOf(1000, 2000, 3000), groups[0])
        assertEquals(listOf(4000), groups[1])
        assertEquals(listOf(5000, 6000), groups[2])
        assertEquals(listOf(7000, 8000, 9000), groups[3])
        assertEquals(listOf(10000), groups[4])
    }

    @Test fun sumsEachElfCalories() {
        val app  = App()

        val elfSums = app.sumByElf(listOf(listOf(1000, 2000, 3000), listOf(4000), listOf(5000, 6000), listOf(7000, 8000, 9000), listOf(10000)))

        assertEquals(listOf(6000, 4000, 11000, 24000, 10000), elfSums)
    }

    @Test fun sumsListCaloriesForOneElf() {
        val app  = App()
        val calorieSum = app.sumCalories(listOf(1000, 2000, 3000))

        assertEquals(6000, calorieSum)
    }
}
