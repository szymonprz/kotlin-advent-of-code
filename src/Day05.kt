import java.util.stream.LongStream
import kotlin.time.measureTime

fun main() {

  fun part1(input: List<String>): Long {

    val seeds =
        input.get(0).substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
    seeds.println()
    val maps: MutableList<List<String>> = mutableListOf()
    var currentMapList: MutableList<String> = mutableListOf()
    input
        .filter { it.isNotEmpty() }
        .drop(1)
        .forEach {
          if (it.contains(":")) {
            if (currentMapList.isNotEmpty()) {
              maps.add(currentMapList)
            }
            currentMapList = mutableListOf()
          } else {
            currentMapList.add(it)
          }
        }
    maps.add(currentMapList)

    var input = seeds
    for (map in maps) {
      input =
          input.map { inputValue ->
            var newValue = inputValue
            for (mapElement in map) {
              val mapLine = mapElement.split(" ").filter { it.isNotEmpty() }
              val sourceStart = mapLine.get(1).toLong()
              val destStart = mapLine.get(0).toLong()
              val range = mapLine.get(2).toLong()
              val destEnd = destStart + range - 1
              val sourceEnd = sourceStart + range - 1

              val contains = sourceStart <= newValue && sourceEnd >= newValue
              if (contains) {
                val diff = newValue - sourceStart
                newValue = destStart + diff
                break
              }
            }
            newValue
          }
    }
    return input.min()
  }

  fun part2(input: List<String>): Long {

    data class MapElement(val sourceStart: Long, val destStart: Long, val range: Long)

    val seeds =
        input
            .get(0)
            .substringAfter(":")
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
            .windowed(2, 2)
    val maps: MutableList<List<String>> = mutableListOf()
    var currentMapList: MutableList<String> = mutableListOf()
    input
        .filter { it.isNotEmpty() }
        .drop(1)
        .forEach {
          if (it.contains(":")) {
            if (currentMapList.isNotEmpty()) {
              maps.add(currentMapList)
            }
            currentMapList = mutableListOf()
          } else {
            currentMapList.add(it)
          }
        }
    maps.add(currentMapList)

    val parsedMaps: MutableList<List<MapElement>> = mutableListOf()
    for (map in maps) {
      val parsedMap = mutableListOf<MapElement>()
      for (mapElement in map) {
        val mapLine = mapElement.split(" ").filter { it.isNotEmpty() }
        val sourceStart = mapLine.get(1).toLong()
        val destStart = mapLine.get(0).toLong()
        val range = mapLine.get(2).toLong()
        parsedMap.add(MapElement(sourceStart, destStart, range))
      }
      parsedMaps.add(parsedMap)
    }

    var min = Long.MAX_VALUE
    seeds.parallelStream().forEach {
      val start = it.first()
      val range = it.last()
      LongStream.range(start, start + range).parallel().forEach { inputValue ->
        var newValue = inputValue
        for (map in parsedMaps) {
          for (mapElement in map) {
            val sourceStart = mapElement.sourceStart
            val destStart = mapElement.destStart
            val range = mapElement.range
            val sourceEnd = sourceStart + range - 1

            val contains = sourceStart <= newValue && sourceEnd >= newValue
            if (contains) {
              val diff = newValue - sourceStart
              newValue = destStart + diff
              break
            }
          }
        }
        if (newValue < min) {
          min = newValue
        }
      }
    }
    return min
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 35L)

  val input = readInput("Day05")
  part1(input).println()

  val test2Input = readInput("Day05_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 46L)
  val time = measureTime { part2(input).println() }

  println("Measured time: " + time.inWholeSeconds)
}
