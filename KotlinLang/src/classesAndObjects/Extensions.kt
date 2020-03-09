package classesAndObjects
import java.util.*

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

fun List<String>.getLongestString() {
    var length = 0
    var longestString = String()
    for (value in this) {
        if (value.length > length) {
            length = value.length
            longestString = value
        }
    }
    println("Longest string is = $longestString")
}

fun main() {

    // TODO Extension functions
    extensionFunctions()

    println("\n****************************\n")

    // TODO Extensions are resolved statically
    extensionsResolvedStatically()

    println("\n****************************\n")

    // TODO Nullable receiver
    nullableReceiver()

    println("\n****************************\n")

    // TODO Extension properties
    extensionProperties()

    println("\n****************************\n")

    // TODO Companion object extensions
    companionObjectExtensions()

    println("\n****************************\n")

    // TODO Scope of extensions
    scopeOfObjectExtension()

    println("\n****************************\n")

    // TODO Declaring extensions as members
    declaringExtensionsAsMembers()

    println("\n****************************\n")

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
        println("Extension function example id : $id")
    }

    println("With overload extension function :")
    Example().printFunctionType(1)
}

fun nullableReceiver() {
    // Note that extensions can be defined with a nullable receiver type. Such extensions can be called on an object
    // variable even if its value is null, and can check for this == null inside the body. This is what allows you to
    // call toString() in Kotlin without checking for null: the check happens inside the extension function.

    fun String?.toUpper(): String {
        if (this == null || this == "") return "null"
        // after the null check, 'this' is autocast to a non-null type, so the toString() below
        // resolves to the member function of the Any class
        return toUpperCase()
    }

    val exampleNullString = String()
    val exampleString = "Hi!"
    println(message = "Nullable receiver : " + exampleNullString.toUpper())
    println(message = "Nullable receiver : " + exampleString.toUpper())
}

var Date.secondsSinceEpoch: Long
    get() = this.time / 1000
    set(value) {
        this.time = value * 1000
    }

val <T> List<T>.lastIndex: Int
    get() = size - 1

fun extensionProperties() {
    // Similarly to functions, Kotlin supports extension properties:

    // Note that, since extensions do not actually insert members into classes,
    // there's no efficient way for an extension property to have a backing field.
    // This is why initializers are not allowed for extension properties. Their behavior can only be defined
    // by explicitly providing getters/setters.

    // val House.number = 1 // error: initializers are not allowed for extension properties

    // You can only define an extension property with custom getter (and setter for var) or
    // a delegated property(https://kotlinlang.org/docs/reference/delegated-properties.html).

    val date = Date(10000)
    println("Extension properties : ${date.secondsSinceEpoch}")

    //or

    val fruitList = Arrays.asList("Apple", "Orange", "Banana")
    val lastFruit = fruitList[fruitList.lastIndex]
    println("Last fruit : $lastFruit")
}

class MessageHelper {
    companion object {
        fun sayWelcomeMessage(message : String) {
            println("Companion object extension welcome message : $message")
        }
    }
}

fun MessageHelper.Companion.sayGoodByMessage(message: String) { // will be called "Companion"
    println("Companion object extension good by message : $message")
}

fun companionObjectExtensions() {
    // If a class has a companion object defined, you can also define extension functions and properties for
    // the companion object. Just like regular members of the companion object, they can be called using only
    // the class name as the qualifier:

    // Look at MessageHelper and MessageHelper.showGoodByMessage
    MessageHelper.sayWelcomeMessage("Hi!")
    MessageHelper.sayGoodByMessage("Good by!")
}

fun scopeOfObjectExtension() {
    // Most of the time we define extensions on the top level - directly under packages:
    // look at getLongestString fun

    // To use such an extension outside its declaring package, we need to import it at the call site:
    // import org.example.project.path.getLongestString
    val list = listOf("red", "green", "blue", "purple")
    list.getLongestString()
}

fun declaringExtensionsAsMembers() {
    // Inside a class, you can declare extensions for another class. Inside such an extension, there are multiple
    // implicit receivers - objects members of which can be accessed without a qualifier.
    // The instance of the class in which the extension is declared is called dispatch receiver,
    // and the instance of the receiver type of the extension method is called extension receiver.

    class Host(val hostname: String) { //  extension receiver
        fun printHostname() {
            println("Host printHostname: $hostname")
        }
    }

    class Connection(val host: Host, val port: Int) { // dispatch receiver
        fun printPort() {
            println("Connection printPort: $port")
        }

        fun Host.printConnectionString() {
            println("Connection printConnectionString: ")
            printHostname()   // calls Host.printHostname()
            printPort()   // calls Connection.printPort()
        }

        fun connect() {
            host.printConnectionString()   // calls the extension function
        }
    }

    Connection(Host("kotl.in"), 443).connect()
    //Host("kotl.in").printConnectionString(443)  // error, the extension function is unavailable outside Connection

    println("\n--------------\n")

    // In case of a name conflict between the members of the dispatch receiver and the extension receiver,
    // the extension receiver takes precedence. To refer to the member of the dispatch receiver
    // you can use the qualified this syntax.

    class HostUpdate(val hostname: String) { //  extension receiver
        fun printHostname() {
            println("HostUpdate printHostname: $hostname")
        }

        fun printConnectionString() {
            println("HostUpdate printConnectionString: $hostname")
        }
    }

    class ConnectionUpdate(val host: HostUpdate, val port: Int) { // dispatch receiver
        fun printPort() {
            println("ConnectionUpdate port: $port")
        }

        fun printHostname() {
            println("ConnectionUpdate printHostname: ${host.hostname}")
        }

        fun HostUpdate.printConnectionString() {  // printConnectionString of Host called, not extension fun
            println("ConnectionUpdate extension printConnectionString:")
            printHostname()
            printPort()
        }

        fun HostUpdate.validateConnection() {
            println("ConnectionUpdate printConnectionHostString: ")
            // To refer to the member of the dispatch receiver you can use the qualified this syntax.
            this@ConnectionUpdate.printHostname() // ConnectionUpdate
            printHostname() // HostUpdate
        }

        fun connect() {
            host.printConnectionString()
        }

        fun validate() {
            host.validateConnection()
        }
    }

    ConnectionUpdate(HostUpdate("kotl.in"), 445).connect()
    println("\n--------------\n")
    ConnectionUpdate(HostUpdate("kotl.in"), 445).validate()

}