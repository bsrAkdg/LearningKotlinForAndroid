package ClassesAndObjects

/**
 * The class declaration consists of the class name, the class header (specifying its type parameters,
 * the primary constructor etc.) and the class body, surrounded by curly braces. Both the header and the body are
 * optional; if the class has no body, curly braces can be omitted.
 *
 * https://kotlinlang.org/docs/reference/classes.html#classes-and-inheritance
 * */

fun main() {

    // TODO Constructors - Primary Constructors
    primaryConstructorExample()

    println("\n****************************\n")

    // TODO Secondary Constructors
    secondaryConstructorExample()

}

fun primaryConstructorExample() {
    // A class in Kotlin can have a primary constructor and one or more secondary constructors.
    // The primary constructor is part of the class header: it goes after the class name (and optional type parameters).

    class PrimaryConstructorClass constructor(title: String)

    // or If the primary constructor does not have any annotations or visibility modifiers, the constructor
    // keyword can be omitted:

    class PrimaryConstructorSecondClass(title: String)

    // The primary constructor cannot contain any code. Initialization code can be placed in initializer blocks,
    // which are prefixed with the init keyword.

    // Much the same way as regular properties, the properties declared in the primary constructor can be mutable (var)
    // or read-only (val).
    class InitOrderDemo(val name: String, var index: Int) {

        val firstProperty = "First property : $name ".also { println(it) }

        init {
            println("First initializer 1")
        }

        init {
            println("First initializer 2")
        }

        // Primary constructor can be used in the initializer blocks
        val secondPropery = "Second property : ${name.length}".also { println(it) }

        init {
            println("Second initializer")
        }
    }

    // if you create this instance, it will call InitOrderDemo variables and inits
    val initOrderDemo = InitOrderDemo("Kotlin", 1)
}

fun secondaryConstructorExample() {
    // Secondary constructor must call primary constructor use this keyword
    class Person(firstName: String) { // primary constructor

        init {
            println("Init block : $firstName")
        }

        var age = 0

        constructor(name: String, age: Int) : this(name) { // secondary constructor
            this.age = age
            println("Constructor : $name")
        }
    }

    val firstPerson = Person("Büşra")
    val secondPerson = Person("Okan", 30)

    println("First person age : ${firstPerson.age}")
    println("Second person age : ${secondPerson.age}")
}