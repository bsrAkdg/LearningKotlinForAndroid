package classesAndObjects

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

/**
 *
 * https://kotlinlang.org/docs/reference/enum-classes.html
 *
 **/


fun main() {
    // TODO Overview
    enumOverview()

    // TODO Initialization
    initialization()

    println("\n****************************\n")

    // TODO Anonymous Classes
    anonymousClasses()

    println("\n****************************\n")

    // TODO Implementing Interfaces in Enum Classes
    implementingInterfacesInEnumClasses()

    // TODO Working with Enum Constants
    workingWithEnumConstants()
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun enumOverview() {
    // The most basic usage of enum classes is implementing type-safe enums:
    // Look at Direction class

    // Each enum constant is an object. Enum constants are separated with commas.
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

fun initialization() {
    // Since each enum is an instance of the enum class, they can be initialized as:
    // Look at Color class

    println(ColorDescription.RED.rgb)
}

enum class ColorDescription(val rgb: Int) {
    RED(0xFF0000) {
        override fun descriptionColor(): String {
            return "RED int code is $rgb"
        }
    },
    GREEN(0x00FF00) {
        override fun descriptionColor(): String {
            return "GREEN int code is $rgb"
        }
    },
    BLUE(0x0000FF) {
        override fun descriptionColor(): String {
            return "BLUE int code is $rgb"
        }
    };

    // If the enum class defines any members, separate the enum constant definitions from
    // the member definitions with a semicolon.

    abstract fun descriptionColor(): String
}

fun anonymousClasses() {
    // Enum constants can also declare their own anonymous classes with their corresponding methods,
    // as well as overriding base methods.

    // Look at ColorDescription class
    println(ColorDescription.RED.descriptionColor())

    //Enum entries cannot contain nested types other than inner classes (deprecated in Kotlin 1.2).
}

enum class IntArithmetic : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun applyAsInt(left: Int, right: Int): Int = left + right
    },
    TIMES {
        override fun applyAsInt(left: Int, right: Int): Int = left * right
    };

    override fun apply(t: Int, u: Int): Int = applyAsInt(t, u)
}

fun implementingInterfacesInEnumClasses() {
    // An enum class may implement an interface (but not derive from a class), providing either a single interface
    // members implementation for all of the entries, or separate ones for each entry within its anonymous class.
    // This is done by adding the interfaces to the enum class declaration as follows:

    // Look at IntArithmetic class
    println("PLUS APPLY : " + IntArithmetic.PLUS.apply(2, 5))
    println("PLUS APPLYASINT : " + IntArithmetic.PLUS.applyAsInt(2, 5))

    println("TIMES APPLY : " + IntArithmetic.TIMES.apply(2, 5))
    println("TIMES APPLYASINT : " + IntArithmetic.TIMES.applyAsInt(2, 5))
}

fun workingWithEnumConstants() {
    // Enum classes in Kotlin have synthetic methods allowing to list the defined enum constants and to get an enum
    // constant by its name. The signatures of these methods are as follows (assuming the name of the enum class is EnumClass):
}