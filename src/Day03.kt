fun main() {

  data class CharPosition(val x: Int, val y: Int)
  data class NumberPositions(val number: Int, val positions: List<CharPosition>)

  fun part1(input: List<String>): Int {
    val charPositions = mutableListOf<CharPosition>()
    val numberPositions = mutableListOf<NumberPositions>()
    input.map { it.split("").filter { it.isNotEmpty() } }
      .forEachIndexed { y, line ->
        var number = ""
        line.forEachIndexed { x, char ->
          when{
            (char.toIntOrNull() != null) -> number += char
            else -> {
              if(number.isNotEmpty()){
                val length = number.length
                val numberPosition = NumberPositions(number.toInt(), (x - length until x).map { CharPosition(it, y) })
                numberPositions.add(numberPosition)
                number = ""
              }
              if(char != "."){
                charPositions.add(CharPosition(x, y))
              }
            }

      } }
        if(number.isNotEmpty()){
          val length = number.length
          val numberPosition = NumberPositions(number.toInt(), (line.size - length until line.size).map { CharPosition(it, y) })
          numberPositions.add(numberPosition)
          number = ""
        }
      }
    return numberPositions.filter { it.positions.any { numberPosition ->
      charPositions
        .map { charPosition -> CharPosition(numberPosition.x - charPosition.x, numberPosition.y - charPosition.y)  }
        .any { it.x in (-1 .. 1) && (it.y) in (-1 .. 1) }} }
      .map { it.number }
      .sum()
  }

  fun part2(input: List<String>): Int {
    val charPositions = mutableListOf<CharPosition>()
    val numberPositions = mutableListOf<NumberPositions>()
    input.map { it.split("").filter { it.isNotEmpty() } }
      .forEachIndexed { y, line ->
        var number = ""
        line.forEachIndexed { x, char ->
          when{
            (char.toIntOrNull() != null) -> number += char
            else -> {
              if(number.isNotEmpty()){
                val length = number.length
                val numberPosition = NumberPositions(number.toInt(), (x - length until x).map { CharPosition(it, y) })
                numberPositions.add(numberPosition)
                number = ""
              }
              if(char != "."){
                charPositions.add(CharPosition(x, y))
              }
            }

          } }
        if(number.isNotEmpty()){
          val length = number.length
          val numberPosition = NumberPositions(number.toInt(), (line.size - length until line.size).map { CharPosition(it, y) })
          numberPositions.add(numberPosition)
          number = ""
        }
      }
    val charWithNumbers = charPositions
      .map { charPosition -> numberPositions
        .filter {
          it.positions.map { numberPosition ->
            CharPosition(numberPosition.x - charPosition.x, numberPosition.y - charPosition.y)
          }.any { it.x in (-1..1) && (it.y) in (-1..1) }
        }
    }.filter { it.size == 2 }
      .map { it.map { it.number }.fold(1){ start, next -> start * next}  }
      .sum()

    return charWithNumbers
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 4361)

  val input = readInput("Day03")
  part1(input).println()

  val test2Input = readInput("Day03_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 467835)
  part2(input).println()
}
