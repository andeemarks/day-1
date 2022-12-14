package advent_of_code.day7

class LSResult(resultLines: List<String>) {
    private var lines: List<Line>
    var size: Int

    operator fun get(i: Int): Line {
        return lines[i]
    }

    init {
        lines = resultLines.map { Line(it) }
        size = lines.size
    }

    fun files(): Iterable<Line> {
        return lines.asIterable()
    }

    class Line(line: String) {

        val size: Int
        val name: String
        val type: String

        init {
            val lineParts = line.split(" ")
            if (lineParts[0] == DIR) {
                this.type = DIR
                this.size = 0
                this.name = lineParts[1]
            } else {
                this.type = FILE
                this.size = lineParts[0].toInt()
                this.name = lineParts[1]
            }
        }

        companion object {
            const val DIR: String = "dir"
            const val FILE: String = "file"
        }
    }

}