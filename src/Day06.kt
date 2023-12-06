import kotlin.time.measureTime

fun main() {

  fun part1(input: List<String>): Int {
    val times =
        input.first().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    val distances =
        input.last().substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
    times.println()
    distances.println()
    val result =
        times
            .zip(distances)
            .map { (time, distance) ->
              (0..time)
                  .map { holdTime -> holdTime.times(time - holdTime) }
                  .filter { it > distance }
                  .count()
            }
            .fold(1) { acc, next -> acc * next }
    return result
  }

  fun part2(input: List<String>): Int {
    val time =
        input
            .first()
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotEmpty() }
            .joinToString("")
            .toLong()
    val distance =
        input
            .last()
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotEmpty() }
            .joinToString("")
            .toLong()
time.println()
    distance.println()
    val count =
        (0..time)
            .map { holdTime -> holdTime.times(time - holdTime) }
            .filter { it > distance }
            .count()

    return count
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 288)

  val input = readInput("Day06")
  part1(input).println()

  val test2Input = readInput("Day06_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 71503)
  val time = measureTime { part2(input).println() }

  println("Measured time: " + time.inWholeSeconds)
}
