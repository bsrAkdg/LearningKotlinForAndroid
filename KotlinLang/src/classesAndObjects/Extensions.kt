package classesAndObjects

/**
 * Kotlin provides the ability to extend a class with new functionality without having to inherit from the class or
 * use design patterns such as Decorator. This is done via special declarations called extensions.
 * For example, you can write new functions for a class from a third-party library that you can't modify.
 * Such functions are available for calling in the usual way as if they were methods of the original class.
 * This mechanism is called extension functions.
 * There are also extension properties that let you define new properties for existing classes.
 *
 * https://kotlinlang.org/docs/reference/extensions.html
 * */

fun main() {

    // TODO Extension functions
    extensionFunctions()

    println("\n****************************\n")

    // TODO Extensions are resolved statically
    extensionsResolvedStatically()

    // TODO Nullable receiver
    nullableReceiver()
}

// or use Generic
// fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun extensionFunctions() {
    val list = mutableListOf(1, 2, 3)
    println("Normally list : $list")

    list.swap(0, 2) // 'this' inside 'swap()' will hold the value of 'list'

    println("Swap list : $list")
}

fun extensionsResolvedStatically() {

    // Extensions do not actually modify classes they extend. By defining an extension, you do not insert new members
    // into a class, but merely make new functions callable with the dot-notation on variables of this type.


    // We would like to emphasize that extension functions are dispatched statically, i.e. they are not virtual by
    // receiver type. This means that the extension function being called is determined by the type of the expression on
    // which the function is invoked, not by the type of the result of evaluating that expression at runtime.

    open class Shape

    class Rectangle : Shape()

    fun Shape.getName() = "Shape"

    fun Rectangle.getName() = "Rectangle"

    fun printClassName(s: Shape) {
        println(s.getName())
    }

    printClassName(Rectangle())

    println("\n****************************\n")

    // This example prints "Shape", because the extension function being called depends only on the declared type of
    // the parameter "s", which is the Shape class.

    // If a class has a member function, and an extension function is defined which has the same receiver type,
    // the same name, and is applicable to given arguments, the member always wins.

    class Example {
        fun printFunctionType() {
            println("Class method")
        }
    }

    println("Without extension function :")
    Example().printFunctionType()

    fun Example.printFunctionType() {
        println("Extension function")
    }

    println("With extension function :")
    Example().printFunctionType()

    // However, it's perfectly OK for extension functions to overload member functions which have the same name
    // but a different signature:

    fun Example.printFunctionType(id: Int) {
        println("Extension function")
    }

    println("With overload extension function :")
    Example().printFunctionType(1)
}

fun nullableReceiver() {

}