package classesAndObjects

/**
 * Inline classes are available only since Kotlin 1.3 and currently are experimental.
 *
 * https://kotlinlang.org/docs/reference/inline-classes.html
 *
 **/


fun main() {

    // TODO Property Delegation
    // Delegated properties are described on a separate class: DelegatedProperties.

    // TODO Implementation by Delegation
    implementationByDelegation()

    println("\n****************************\n")

    // TODO Overriding a member of an interface implemented by delegation
    overridingDelegation()
}

interface ClosedShape {
    fun area(): Int
}

fun implementationByDelegation() {
    // In software engineering, the delegation pattern is an object-oriented design pattern that
    // allows object composition to achieve the same code reuse as inheritance.

    // In the example below, the class OldWindow delegates the area() call to its internal OldRectangle object (its delegate).

    class OldRectangle(val width: Int, val height: Int) {
        fun area() = width * height
    }

    class OldWindow(val bounds: OldRectangle) {
        // Delegation
        fun area() = bounds.area()
    }

    // Some languages have special support for delegation built in. For example, we could write:

    class Rectangle(val width: Int, val height: Int) : ClosedShape {
        override fun area() = width * height
    }

    class Window(private val bounds: ClosedShape) : ClosedShape by bounds

    // The by-clause in the supertype list for Window indicates that bounds will be stored internally in objects of
    // Window and the compiler will generate all the methods of ClosedShape that forward to bounds.
}

interface Base {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int) : Base {
    override fun printMessage() {
        println("BaseImpl printMessage $x")
    }

    override fun printMessageLine() {
        println("BaseImpl printMessageLine $x")
    }
}

class Derived(b: Base) : Base by b {
    override fun printMessage() {
        println("Derived printMessage")
    }
}

interface OverrideBase {
    val message: String
    fun print()
}

class OverrideBaseImpl(val x: Int) : OverrideBase {
    override val message = "message: x = $x"
    override fun print() {
        println("OverrideBaseImpl $message")
    }
}

class OverrideDerived(b: OverrideBase) : OverrideBase by b {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of OverrideDerived"
}

fun overridingDelegation() {
    // Overrides work as you might expect: the compiler will use your override implementations instead of those in
    // the delegate object. If we were to add override fun printMessage() { print("abc") } to Derived,
    // the program would print "abc" instead of "10" when printMessage is called:

    val b = BaseImpl(10)
    Derived(b).printMessage()
    Derived(b).printMessageLine()

    println("\n--------------\n")

    // Note, however, that members overridden in this way do not get called from the members of the delegate object,
    // which can only access its own implementations of the interface members:

    val overrideBase = OverrideBaseImpl(10) //  message = "message: x = 10"
    val derived = OverrideDerived(overrideBase) // message = "Message of OverrideDerived"
    derived.print() // OverrideBaseImpl message: x = 10
    println(derived.message) // Message of OverrideDerived
}