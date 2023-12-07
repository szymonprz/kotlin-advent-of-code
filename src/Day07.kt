import kotlin.math.sign
import kotlin.time.measureTime

enum class Hand(val order: Int) {
  FiveOfAKind(7) {
    override fun elevateWithJoker(jokers: Int): Hand {
      return FiveOfAKind
    }
  },
  FourOfAKind(6) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 1 || jokers == 4) {
        return FiveOfAKind
      }
      return FourOfAKind
    }
  },
  FullHouse(5) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 2 || jokers == 3) {
        return FiveOfAKind
      }
      return FullHouse
    }
  },
  ThreeOfAKind(4) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 2) {
        return FiveOfAKind
      } else if (jokers == 1 || jokers == 3) {
        return FourOfAKind
      }
      return ThreeOfAKind
    }
  },
  TwoPair(3) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 2) {
        return FourOfAKind
      } else if (jokers == 1) {
        return FullHouse
      } else return TwoPair
    }
  },
  OnePair(2) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 1 || jokers == 2) {
        return ThreeOfAKind
      }
      return OnePair
    }
  },
  HighCard(1) {
    override fun elevateWithJoker(jokers: Int): Hand {
      if (jokers == 1) {
        return OnePair
      }
      return HighCard
    }
  };

  abstract fun elevateWithJoker(jokers: Int): Hand
}

fun main() {

  fun part1(input: List<String>): Int {

    val cardScore =
        mapOf(
            "A" to 15,
            "K" to 14,
            "Q" to 13,
            "J" to 12,
            "T" to 10,
            "9" to 9,
            "8" to 8,
            "7" to 7,
            "6" to 6,
            "5" to 5,
            "4" to 4,
            "3" to 3,
            "2" to 2,
        )

    data class Card(val text: String, val bid: Int) : Comparable<Card> {
      val hand = calculateHand(text)
      val cardScores = text.split("").filter { it.isNotEmpty() }.map { it to cardScore[it]!! }

      private fun calculateHand(text: String): Hand {
        val split: List<String> = text.split("").filter { it.isNotEmpty() }
        val grouped = split.groupBy { it }
        return when {
          grouped.size == 1 -> Hand.FiveOfAKind
          grouped.size == 2 -> {
            if (grouped.entries.any { it.value.size == 4 }) {
              Hand.FourOfAKind
            } else {
              Hand.FullHouse
            }
          }
          grouped.size == 3 -> {
            if (grouped.entries.any { it.value.size == 3 }) {
              Hand.ThreeOfAKind
            } else {
              Hand.TwoPair
            }
          }
          grouped.size == 4 -> Hand.OnePair
          else -> Hand.HighCard
        }
      }

      override fun compareTo(other: Card): Int {
        return if (this.hand.order > other.hand.order) {
          1
        } else if (this.hand.order == other.hand.order) {
          this.cardScores
              .zip(other.cardScores)
              .map { sign(it.first.second.toDouble() - it.second.second).toInt() }
              .firstOrNull { it != 0 } ?: 0
        } else {
          -1
        }
      }
    }

    val sum =
        input
            .map {
              val cardText = it.substringBefore(" ")
              val bid = it.substringAfter(" ").trim().toInt()
              Card(cardText, bid)
            }
            .sorted()
            .mapIndexed { index, card -> (index + 1) * card.bid }
            .sum()

    return sum
  }

  fun part2(input: List<String>): Int {
    val cardScore =
        mapOf(
            "A" to 15,
            "K" to 14,
            "Q" to 13,
            "T" to 10,
            "9" to 9,
            "8" to 8,
            "7" to 7,
            "6" to 6,
            "5" to 5,
            "4" to 4,
            "3" to 3,
            "2" to 2,
            "J" to 1,
        )

    data class Card(val text: String, val bid: Int) : Comparable<Card> {
      val cardScores = text.split("").filter { it.isNotEmpty() }.map { it to cardScore[it]!! }
      val hand = calculateHand(text)

      private fun calculateHand(text: String): Hand {
        val cards: List<String> = text.split("").filter { it.isNotEmpty() }
        val jokers = cards.filter { it == "J" }
        val grouped = cards.groupBy { it }
        val handWithoutJoker =
            when {
              grouped.size == 1 -> Hand.FiveOfAKind
              grouped.size == 2 -> {
                if (grouped.entries.any { it.value.size == 4 }) {
                  Hand.FourOfAKind
                } else {
                  Hand.FullHouse
                }
              }
              grouped.size == 3 -> {
                if (grouped.entries.any { it.value.size == 3 }) {
                  Hand.ThreeOfAKind
                } else {
                  Hand.TwoPair
                }
              }
              grouped.size == 4 -> Hand.OnePair
              else -> Hand.HighCard
            }
        return handWithoutJoker.elevateWithJoker(jokers.size)
      }

      override fun compareTo(other: Card): Int {
        return if (this.hand.order > other.hand.order) {
          1
        } else if (this.hand.order == other.hand.order) {
          this.cardScores
              .zip(other.cardScores)
              .map { sign(it.first.second.toDouble() - it.second.second).toInt() }
              .firstOrNull { it != 0 } ?: 0
        } else {
          -1
        }
      }
    }

    val sum =
        input
            .map {
              val cardText = it.substringBefore(" ")
              val bid = it.substringAfter(" ").trim().toInt()
              Card(cardText, bid)
            }
            .sorted()
            .map {
              println(it.text + " " + it.bid + " " + it.hand)
              it
            }
            .mapIndexed { index, card -> (index + 1) * card.bid }
            .sum()

    return sum
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
  val part1Test = part1(testInput)
  part1Test.println()
  check(part1Test == 6440)

  val input = readInput("Day07")
  part1(input).println()

  val test2Input = readInput("Day07_test")
  val part2Test = part2(test2Input)
  part2Test.println()
  check(part2Test == 5905)
  val time = measureTime { part2(input).println() }

  println("Measured time: " + time.inWholeSeconds)
}
