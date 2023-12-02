fun main() {
  fun part1(input: List<String>): Int {
    val requirements = mapOf("red" to 12, "green" to 13, "blue" to 14)
    return input.map {
      val split = it.split(":")
      val gameName = split[0]
      val gameStats = split[1]
      val gameId = Regex("\\d+").find(gameName)!!.value.toInt()
      val isValidGame: Boolean = gameStats.split(";")
        .all { gamePart ->
          val allParts = gamePart
            .split(",")
            .map { it.trim() }
          allParts
            .map {
              val parts = it.split(" ")
              parts[1] to parts[0].toInt()
            }
            .all { requirements.getOrDefault(it.first, 0) >= it.second }
        }
      if(isValidGame){
        gameId
      }else {
        0
      }
    }.sum()
  }

  fun part2(input: List<String>): Int {
    val requirements = mapOf("red" to 12, "green" to 13, "blue" to 14)
    return input.map {
      val split = it.split(":")
      val gameName = split[0]
      val gameStats = split[1]
      val gameId = Regex("\\d+").find(gameName)!!.value.toInt()
      var minGreen = 1
      var minBlue = 1
      var minRed = 1
      gameStats.split(";")
        .forEach { gamePart ->

          val allParts = gamePart
            .split(",")
            .map { it.trim() }
          allParts
            .map {
              val parts = it.split(" ")
              parts[1] to parts[0].toInt()
            }.forEach {
              when(it.first){
                "red" -> if(it.second> minRed) minRed = it.second
                "green" -> if(it.second> minGreen) minGreen = it.second
                "blue" -> if(it.second> minBlue) minBlue = it.second
              }
            }
        }
      minGreen * minBlue * minRed
    }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 8)

  val input = readInput("Day02")
  part1(input).println()

  val test2Input = readInput("Day02_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 2286)
  part2(input).println()
}
