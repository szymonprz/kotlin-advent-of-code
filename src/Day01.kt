fun main() {
  fun part1(input: List<String>): Int {
    return input
        .asSequence()
        .map {
          val digits = it.split("").mapNotNull { it.toIntOrNull() }
          digits.firstOrNull() to digits.lastOrNull()
        }
        .filter { (firstNumber, lastNumber) -> firstNumber != null && lastNumber != null }
        .map { (firstNumber, lastNumber) -> (firstNumber.toString()) + lastNumber.toString() }
        .map { it.toInt() }
        .sum()
  }

  fun findAllIndices(it: Map.Entry<String, Int>, line: String) =
      Regex(it.key).findAll(line).map { it.range.first }.toList()

  fun part2(input: List<String>): Int {
    val numbers =
        mapOf(
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9)
    val result =
        input
            .asSequence()
            .map { line ->
              val allNumbersWithIndices =
                  numbers.entries
                      .map { it.value to findAllIndices(it, line) }
                      .flatMap { (value, indices) -> indices.map { value to it } }
                      .filter { (_, index) -> index != -1 }
              val firstNumber = allNumbersWithIndices.minByOrNull { (_, index) -> index }?.first
              val lastNumber = allNumbersWithIndices.maxByOrNull { (_, index) -> index }?.first
              firstNumber to lastNumber
            }
            .filter { (firstNumber, lastNumber) -> firstNumber != null && lastNumber != null }
            .map { (firstNumber, lastNumber) -> (firstNumber.toString()) + lastNumber.toString() }
    return result.map { it.toInt() }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 142)

  val input = readInput("Day01")
  part1(input).println()

  val test2Input = readInput("Day01_test_2")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 369)
  part2(input).println()
}
