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

    println("\n****************************\n")

    // TODO Resolving overriding conflicts
    resolvingOverridingConflicts()
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

interface Name {
    val name: String
}

interface Person : Name {
    val firstName: String
    val lastName: String

    override val name: String
        get() = "$firstName $lastName"
}

fun interfacesInheritance() {
    // An interface can derive from other interfaces and thus both provide implementations for their members and
    // declare new functions and properties. Quite naturally, classes implementing such an interface are only
    // required to define the missing implementations:

    // Look at Name-Person interfaces and Employee class, Employee class must implement members of Person,
    // but must not implement members of Name, because Person override name member,
    // if Person doesn't implement name member, Employee must implement it.
    data class Employee(override val firstName: String, override val lastName: String, val id: Int) : Person

    val employee = Employee("Ali", "Veli", 1)
    println(employee)
}

interface A {
    fun foo() {
        println("A foo")
    }

    fun bar()
}

interface B {
    fun foo() {
        println("B foo")
    }

    fun bar() {
        println("B bar")
    }
}

class C : A {
    override fun bar() {
        println("C override A bar")
    }
}

class D : A, B {

    override fun foo() {
        println("D override foo")
        super<A>.foo() // supertype list
        super<B>.foo() // supertype list
    }

    override fun bar() {
        println("D override bar")
        super.bar()
    }
}

fun resolvingOverridingConflicts() {
    // When we declare many types in our supertype list, it may appear that we inherit more than one implementation of
    // the same method.

    println("C created : \n")
    val c = C()
    c.foo()
    c.bar()

    println("\nD created : \n")
    val d = D()
    d.foo()
    d.bar()
}