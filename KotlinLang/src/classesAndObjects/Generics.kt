package classesAndObjects

/**
 * https://kotlinlang.org/docs/reference/generics.html
 * */

fun main() {

    // TODO Overview
    overview()
}

fun overview() {
    // As in Java, classes in Kotlin may have type parameters:

    class Box<T>(t: T) {
        var value = t
    }

    // In general, to create an instance of such a class, we need to provide the type arguments:
    val musicBox: Box<String> = Box("Music")
    println("My ${musicBox.value} box is empty now")

    // But if the parameters may be inferred, e.g. from the constructor arguments or by some other means,
    // one is allowed to omit the type arguments:

    val cookBox= Box("Cook")
    println("My ${cookBox.value} box is empty now")

}