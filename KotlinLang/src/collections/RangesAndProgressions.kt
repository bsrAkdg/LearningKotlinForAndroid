package collections

/**
 * https://kotlinlang.org/docs/reference/ranges.html
 * */

fun main() {

    // TODO Ranges and Progressions Overview
    rangesAndProgressionsOverview()

    println("\n****************************\n")

    // TODO Range
    range()

    println("\n****************************\n")

    // TODO Progression
    progression()
}

fun rangesAndProgressionsOverview() {
    /* Kotlin lets you easily create ranges of values using the rangeTo() function from the kotlin.ranges package and
    its operator form ... Usually, rangeTo() is complemented by in or !in functions.

       Integral type ranges (IntRange, LongRange, CharRange) have an extra feature: they can be iterated over.
    These ranges are also progressions of the corresponding integral types. Such ranges are generally used for
    iteration in the for loops.
    */

    for (i in 1..10) {
        print("$i ")
    }

    println("\n--------------\n")

    // To iterate numbers in reverse order, use the downTo function instead of ...
    for (i in 4 downTo 1) print("$i ")

    println("\n--------------\n")

    // It is also possible to iterate over numbers with an arbitrary step (not necessarily 1).
    // This is done via the step function.

    for (i in 1..10 step 2) print("$i ")
    println()
    for (i in 10 downTo 1 step 2) print("$i ")

    println("\n--------------\n")

    // To iterate a number range which does not include its end element, use the until function:
    for (i in 1 until 10) {       // i in [1, 10), 10 is excluded
        print("$i ")
    }

    println("\n--------------\n")

    for (ch in 'a'.rangeTo('e')) {
        println(ch)
    }

    println("\n--------------\n")

    (2..5).forEach(::println)
}

class Version(val major: Int, val minor: Int) : Comparable<Version> {

    override fun compareTo(other: Version): Int {
        if (this.major != other.major) {
            return this.major - other.major
        }
        return this.minor - other.minor
    }
}

fun range() {
    /* A range defines a closed interval in the mathematical sense: it is defined by its two endpoint values
    which are both included in the range. Ranges are defined for comparable types: having an order, you can define
    whether an arbitrary instance is in the range between two given instances. The main operation on ranges is contains,
    which is usually used in the form of in and !in operators.

    To create a range for your class, call the rangeTo() function on the range start value and provide the end value as
    an argument. rangeTo() is often called in its operator form ... */

    val versionRange = Version(1, 11)..Version(1, 30)
    println(Version(0, 9) in versionRange)
    println(Version(1, 20) in versionRange)
}

fun progression() {
    /* As shown in the examples above, the ranges of integral types, such as Int, Long, and Char, can be treated as
    arithmetic progressions of them. In Kotlin, these progressions are defined by special types: IntProgression,
    LongProgression, and CharProgression.

    Progressions have three essential properties: the first element, the last element, and a non-zero step.
    The first element is first, subsequent elements are the previous element plus a step. Iteration over a progression
    with a positive step is equivalent to an indexed for loop in Java/JavaScript.

    When you create a progression implicitly by iterating a range, this progression's first and last elements are the
    range's endpoints, and the step is 1. */

    for (i in 1..10) print("$i ")

    println("\n--------------\n")

    // To define a custom progression step, use the step function on a range.

    for (i in 1..8 step 2) print(i)

    println("\n--------------\n")

    /* The last element of the progression is calculated this way:
        - For a positive step: the maximum value not greater than the end value such that (last - first) % step == 0.
        - For a negative step: the minimum value not less than the end value such that (last - first) % step == 0. */

    // Thus, the last element is not always the same as the specified end value.

    for (i in 1..9 step 3) print(i) // the last element is 7

    println("\n--------------\n")

    // To create a progression for iterating in reverse order, use downTo instead of .. when defining the range for it.

    for (i in 4 downTo 1) print(i)

    println("\n--------------\n")

    /* Progressions implement Iterable<N>, where N is Int, Long, or Char respectively, so you can use them in various
    collection functions like map, filter, and other. */

    println((1..10).filter { it % 2 == 0 })
}