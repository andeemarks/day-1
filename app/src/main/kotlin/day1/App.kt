package day1

import java.io.File

class App {
    fun day1(calories: String): Int {
        val groups = groupByElf(calories)
        val sums = sumByElf(groups)

        return sums.maxOrNull()!!
    }

    fun day2(calories: String): Int {
        val groups = groupByElf(calories)
        val sums = sumByElf(groups)

        return sums.sortedDescending().take(3).sum()
    }

    fun sumCalories(calories: List<Int>): Int {
        return calories.sum()
    }

    fun groupByElf(calories: String): List<List<Int>> {
        val groups = calories.trim().split("\n\n")

        return groups.map { it -> it.split("\n").map { it.toInt()}}
    }

    fun sumByElf(groups: List<List<Int>>): List<Int> {
        return groups.map { sumCalories(it) }
    }
}

fun main() {
    val calorieList = File("input.txt").inputStream().readBytes().toString(Charsets.UTF_8)
    println(App().day1(calorieList))
    println(App().day2(calorieList))
}
