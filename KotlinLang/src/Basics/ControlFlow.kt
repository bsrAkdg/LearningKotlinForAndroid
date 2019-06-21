package Basics

/**
 * This page contains Control flow(if, when, for, while) on kotlin language
 *
 * https://kotlinlang.org/docs/reference/control-flow.html
 * */

fun main() {

    // TODO If expression
    // if is an expression, i.e. it returns a value. Therefore there is no ternary operator (condition ? then : else),
    //  because ordinary if works fine in this role.

    println("Traditional usage ${getMaxValue(2, 9)}")
    println("With else usage ${getMaxValueLong(2, 9)}")
    println("As expression ${getMaxValueShort(2, 9)}")

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