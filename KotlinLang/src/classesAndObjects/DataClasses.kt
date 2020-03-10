package classesAndObjects

/**
 * We frequently create classes whose main purpose is to hold data. In such a class some standard functionality and
 * utility functions are often mechanically derivable from the data. In Kotlin, this is called a data class and
 * is marked as data
 *
 * https://kotlinlang.org/docs/reference/data-classes.html
 * */

fun main() {
    // TODO Overview Data Class
    dataClass()

    println("\n****************************\n")

    // TODO Properties Declared in the Class Body
    propertiesDeclaredInTheClassBody()
}

fun dataClass() {
    /*
    To ensure consistency and meaningful behavior of the generated code, data classes have to fulfill
    the following requirements:
        - The primary constructor needs to have at least one parameter;
        - All primary constructor parameters need to be marked as val or var;
        - Data classes cannot be abstract, open, sealed or inner;
        - (before 1.1) Data classes may only implement interfaces.

    Additionally, the members generation follows these rules with regard to the members inheritance:

    - If there are explicit implementations of equals(), hashCode() or toString() in the data class body or final
    implementations in a superclass, then these functions are not generated, and the existing implementations are used;

    - If a supertype has the componentN() functions that are open and return compatible types, the corresponding functions
    are generated for the data class and override those of the supertype.
    If the functions of the supertype cannot be overridden due to incompatible signatures or being final,
    an error is reported;

    - Deriving a data class from a type that already has a copy(...) function with a matching signature is deprecated
    in Kotlin 1.2 and is prohibited in Kotlin 1.3.

    - Providing explicit implementations for the componentN() and copy() functions is not allowed.
    Since 1.1, data classes may extend other classes (see Sealed classes for examples).

    On the JVM, if the generated class needs to have a parameterless constructor, default values for all properties
    have to be specified (see Constructors).
    */

    data class User(val username: String)
}

fun propertiesDeclaredInTheClassBody() {

    data class Person(val name: String) {
        var age: Int = 0
    }

    val personOne = Person("Ayşe")
    personOne.age = 20
    // personOne.name = "New Ayşe"
    println("${personOne.name}'s age : ${personOne.age}")

    println("\n--------------\n")

    // Only the property name will be used inside the toString(), equals(), hashCode(), and copy() implementations,
    // and there will only be one component function component1(). While two Person objects can have different ages,
    // they will be treated as equal. For example :
    println("Data class with toString() : $personOne")

    println("\n--------------\n")

    val personTwo = Person("Ayşe")
    val isEquals = personOne == personTwo
    println("Data class with equals() : $isEquals")

    println("\n--------------\n")

    val personThird = personOne.copy(name = "Fatma")
    println("${personThird.name}'s age : ${personThird.age}")

}