package Basics

/**
 * Kotlin has three structural jump expressions: return, break, continue.
 * The type of these expressions is the Nothing type.
 *
 * https://kotlinlang.org/docs/reference/returns.html#returns-and-jumps
 * */

fun main() {

    // TODO Break and Continue
    println("BREAK OUTPUT : ")
    for (i in 1..10) {
        if (i == 5)
            break
        println("$i")
    }

    println("CONTINUE OUTPUT : ")
    for (i in 1..10) {
        if (i == 5)
            continue //prints all item without 5
        println("$i")
    }

    print("RETURN OUTPUT : ")
    returnWithoutLabel()

    // TODO Break and Continue Labels
    println("LABEL WITH BREAK OUTPUT : ")
    breakLabel@ for (i in 1..10) {
        for (j in 1..5) {
            if (j == 3)
                break@breakLabel //Shows all i elements until j==3.
            println("j value is $j for $i")
        }
    }

    println("LABEL WITH CONTINUE OUTPUT : ")
    continueLabel@ for (i in 1..10) {
        for (j in 1..5) {
            if (j == 3)
                continue@continueLabel //Shows all i elements without j==3.
            println("j value is $j for $i")
        }
    }

    println("LABEL WITH RETURN OUTPUT : ")
    returnWithLabel()
    returnOtherWithLabel()
}

fun returnWithoutLabel() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 4) return // Loop stops to print after return
        println(it)
    }
    println("returnWithoutLabel ended")
}

fun returnWithLabel() {
    listOf(1, 2, 3, 4, 5).forEach returnLabel@{
        if (it == 4) return@returnLabel // Loop continues to print after return
        println(it)
    }
    println("returnWithLabel ended")
}

fun returnOtherWithLabel() {
    run returnLabel@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 4) return@returnLabel // Loop stops to print after return
            println(it)
        }
    }
    println("returnOtherWithLabel ended")
}
