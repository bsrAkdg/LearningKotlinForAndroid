package Basics

/**
 * This page contains Control flow(if, when, for, while) on kotlin language
 *
 * https://kotlinlang.org/docs/reference/control-flow.html
 * */

fun main() {

    // TODO if expression
    // if is an expression, i.e. it returns a value. Therefore there is no ternary operator (condition ? then : else),
    //  because ordinary if works fine in this role.

    println("Traditional usage : ${getMaxValue(2, 9)}")
    println("With else usage : ${getMaxValueLong(2, 9)}")
    println("As expression : ${getMaxValueShort(2, 9)}")

    // TODO when expression
    println("When expression : ${isSingle(3)}")

    println("which Range : ${whichRange(6)}")
    println("which Range : ${whichRange(13)}")
    println("which Range : ${whichRange(25)}")

    println("Is string of 1000 start with zero ? - ${isStartWithZero("1000")}")
    println("Is string of 0001 start with zero ? - ${isStartWithZero("0001")}")
    println("Is int of 1000 start with zero ? - ${isStartWithZero(1000)}")

    // TODO for loops
    // for loops provides an iterator: iterator(), next(), hasNext()
    println("Is Bursa a big city in Turkey : - ${getBigCities("Bursa")}")

    // TODO while loops
    println("First five characters of your name :")
    getCharacters("Mehmet Ali")
}


fun getMaxValue(a: Int, b: Int): Int {
    var max = a
    if (a < b) max = b
    return max
}

fun getMaxValueLong(a: Int, b: Int): Int {
    // With else
    val max: Int
    if (a > b)
        max = a
    else
        max = b
    return max
}

fun getMaxValueShort(a: Int, b: Int): Int {
    val max = if (a < b) b else a
    return max
}

fun isSingle(a: Int): String {
    return when (a) {
        2, 4 -> "Double"
        1, 3, 5 -> "Single"
        else -> { // Note the block
            "Unknow"
        }
    }
}

fun whichRange(a: Int): String {
    return when (a) {
        in 0..10 -> "Between 0 - 10 "
        !in 10..20 -> "Outside the 10 -20 range "
        else -> { // Note the block
            "None of the above"
        }
    }
}

fun isStartWithZero(x: Any) = when (x) { // when (val x = getValueX()) is possible since kotlin 1.3
    is String -> if (x.startsWith("0")) "Start with zero" else "No start with zero"
    is Int -> if (x.toString().startsWith("0")) "It is zero" else "It is not zero"
    else -> "Unknow"
}


fun getBigCities(city: String): String {
    val cityList = listOf("Bursa", "Ankara", "İstanbul")
    for (c in cityList) {
        if (c.equals(city)) {
            return "Yes, it is"
        }
    }
    return "No, it isn't"
}


fun getCharacters(name: String) {
    var x = 0
    do {
        println(name[x])
        x++
    } while (x < 6)
}
