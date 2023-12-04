fun main() {

  fun part1(input: List<String>): Int {
    return input
        .map {
          val split = it.substringAfter(":").split("|")
          val winningNumbers =
              split[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

          val elfNumbers = split[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

          val intersect = winningNumbers.intersect(elfNumbers)
          if (intersect.size > 0) {
            1 shl (intersect.size - 1)
          } else {
            0
          }
        }
        .sum()
  }

  fun part2(input: List<String>): Int {
    var allCards: MutableList<Int> = MutableList(input.size) { 1 }
    input.forEachIndexed { index, card ->
      val copies = allCards[index]
      val split = card.substringAfter(":").split("|")
      val winningNumbers = split[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

      val elfNumbers = split[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

      val intersect = winningNumbers.intersect(elfNumbers)
      (index + 1 until index + 1 + intersect.size).forEach { nextIndex ->
        if (nextIndex <= allCards.size) {
          allCards.set(nextIndex, allCards.get(nextIndex) + 1 * copies)
        }
      }
    }

    return allCards.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 13)

  val input = readInput("Day04")
  part1(input).println()

  val test2Input = readInput("Day04_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 30)
  part2(input).println()
}
