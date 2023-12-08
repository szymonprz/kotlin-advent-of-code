import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTime

fun main() {

  fun part1(input: List<String>): Int {

    val instructions = input.first().split("").filter { it.isNotEmpty() }

    data class Node(val key: String, val left: String, val right: String)

    val map =
        input
            .drop(1)
            .filter { it.isNotEmpty() }
            .map {
              val key = it.substringBefore(" =")
              val firstNode = it.substringAfter("(").substringBefore(",")
              val secondNode = it.substringAfter(", ").substringBefore(")")
              key to Node(key, firstNode, secondNode)
            }
            .toMap()
    val root = map.get("AAA")!!
    var current = root
    var index = 0
    var steps = 0
    while (current.key != "ZZZ") {
      val instruction = instructions[index % instructions.size]
      val nextKey =
          when (instruction) {
            "L" -> current.left
            else -> current.right
          }
      current = map[nextKey]!!
      steps++
      index++
    }

    return steps
  }

    fun lcm(number1: Long, number2: Long): Long{
        if (number1 == 0L || number2 == 0L) {
            return 0
        }
        val absNumber1 = abs(number1)
        val absNumber2 = abs(number2)
        val absHigherNumber = max(absNumber1.toDouble(), absNumber2.toDouble()).toLong()
        val absLowerNumber = min(absNumber1.toDouble(), absNumber2.toDouble()).toLong()
        var lcm = absHigherNumber
        while (lcm % absLowerNumber != 0L) {
            lcm += absHigherNumber
        }
        return lcm
    }

  fun part2(input: List<String>): Long {
      val instructions = input.first().split("").filter { it.isNotEmpty() }

      data class Key(val value: String, val end: Char)
      data class Node(val key: String, val left: Key, val right: Key)
      val map =
          input
              .drop(1)
              .filter { it.isNotEmpty() }
              .map {
                  val key = it.substringBefore(" =")
                  val end = key.last()
                  val firstNode = it.substringAfter("(").substringBefore(",")
                  val firstNodeEnd = firstNode.last()
                  val secondNode = it.substringAfter(", ").substringBefore(")")
                  val secondNodeEnd = secondNode.last()
                  Key(key, end) to Node(key, Key(firstNode, firstNodeEnd), Key(secondNode, secondNodeEnd))
              }
              .toMap()
      val root = map.filterKeys { it.end == 'A' }
      var current = root
      var index = 0
      var steps = 0L
      var stepsForZ = mutableListOf<Long>()
      while (current.isNotEmpty()) {
          val instruction = instructions[index]
          steps++
          current = current.entries.associate {
              val nextKey = when (instruction) {
                  "L" -> it.value.left
                  else -> it.value.right
              }
              nextKey to map[nextKey]!!
          }
          current.filterKeys { it.end == 'Z' }.forEach { stepsForZ.add(steps) }
          current = current.filterKeys { it.end != 'Z' }
          index = ((index + 1) % instructions.size)
      }
      stepsForZ.println()
      return stepsForZ.fold(1L){acc, next -> lcm(acc, next)}
  }





  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 6)

  val input = readInput("Day08")
  part1(input).println()

  val test2Input = readInput("Day08_test2")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 6L)
  val time = measureTime { part2(input).println() }

  println("Measured time: " + time.inWholeSeconds)
}
