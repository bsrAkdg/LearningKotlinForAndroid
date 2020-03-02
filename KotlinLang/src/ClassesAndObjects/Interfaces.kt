package ClassesAndObjects

/**
 * https://kotlinlang.org/docs/reference/interfaces.html
 *
 **/


fun main() {

    // TODO Interfaces
    interfaces()

    println("\n****************************\n")

    // TODO Properties in Interfaces
    propertiesInInterfaces()

    println("\n****************************\n")

    // TODO Interfaces Inheritance
    interfacesInheritance()
}

interface State {
    fun logCustomState()
    fun logDefaultState() {
        // Interfaces can contain declarations
        println("This is default state")
    }
}

fun interfaces() {
    // Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations.
    // What makes them different from abstract classes is that interfaces cannot store state.
    // They can have properties but these need to be abstract or to provide accessor implementations.

    class FileState : State { // A class or object can implement one or more interfaces
        override fun logCustomState() {
            // It can be override
            println("This is override custom state")
        }
    }

    val fileState = FileState()
    fileState.logCustomState()
    fileState.logDefaultState()
}

interface AdvancedState {
    val defaultMessage: String
        get() = "Default message"

    val customMessage: String // abstract

    fun logCustomState()

    fun logDefaultState() {
        // Interfaces can contain declarations
        println(defaultMessage)
    }
}

fun propertiesInInterfaces() {
    // You can declare properties in interfaces. A property declared in an interface can either be abstract,
    // or it can provide implementations for accessors. Properties declared in interfaces can't have backing fields,
    // and therefore accessors declared in interfaces can't reference them.

    class FileState : AdvancedState {
        // You must override customMessage
        override val customMessage: String
            get() = "Custom message"

        override fun logCustomState() {
            println("This is override override custom message $customMessage")
        }
    }

    val fileState = FileState()
    fileState.logCustomState()
    fileState.logDefaultState()
}

fun interfacesInheritance() {

}