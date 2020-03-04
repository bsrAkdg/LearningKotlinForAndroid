package ClassesAndObjects

/**
 * https://kotlinlang.org/docs/reference/visibility-modifiers.html
 *
 **/
fun main() {
    // Classes, objects, interfaces, constructors, functions, properties and their setters can have visibility modifiers.
    // (Getters always have the same visibility as the property.) There are four visibility modifiers in Kotlin: private,
    // protected, internal and public. The default visibility, used if there is no explicit modifier, is public.

    // TODO Package
    packages()

    println("\n****************************\n")

    // TODO Classes and Interfaces
    classesAndInterfaces()

    println("\n****************************\n")

    // TODO Constructors
    constructors()

    // TODO Local declarations
    // Local variables, functions and classes can not have visibility modifiers.

    // TODO Modules


}

fun packages() {
    // Functions, properties and classes, objects and interfaces can be declared on the "top-level", i.e. directly inside a package:
    // for example main method on this file

    // If you do not specify any visibility modifier, public is used by default,
    // which means that your declarations will be visible everywhere;

    // If you mark a declaration private, it will only be visible inside the file containing the declaration;

    // If you mark it internal, it is visible everywhere in the same module;

    // protected is not available for top-level declarations.

    // Note: to use a visible top-level declaration from another package, you should still import it.
    // file name: example.kt
}

open class Outher {
    private val privateValue = 1
    protected open val protectedOpenValue = 2
    internal val internalValue = 3
    val defaultValue = 4

    protected class Nested {
        public val nestedClassValue = 5
    }
}

class Subclass : Outher() {

    override val protectedOpenValue: Int
        get() = 6

    fun logValues() {
        println("privateValue : inaccessible")
        println("protectedOpenValue accessible : $protectedOpenValue")
        println("internalValue accessible : $internalValue")
        println("defaultValue accessible : $defaultValue")
        val nested = Nested()
        println("nestedClassValue accessible : ${nested.nestedClassValue}")
    }
}

fun classesAndInterfaces() {
    // If you override a protected member and do not specify the visibility explicitly,
    // the overriding member will also have protected visibility.

    val subclass = Subclass()
    subclass.logValues()

    // val nested = Outher.Nested() look at warning of this line
}

fun constructors() {
    // To specify a visibility of the primary constructor of a class,
    // use the following syntax (note that you need to add an explicit constructor keyword):

    // Here the constructor is private. By default, all constructors are public,
    // which effectively amounts to them being visible everywhere
    // where the class is visible (i.e. a constructor of an internal class is only visible within the same module).

    class CustomPrivateClass private constructor(firstElement: String) {
        val customPrivateMessage = "This is $firstElement"
    }

    class CustomPublicClass constructor(firstElement: String) {
        val customPublicMessage = "This is $firstElement"
    }

    //val customPrivateClass = CustomPrivateClass("Private message") look at warning of this line

    val customPublicClass = CustomPublicClass("Public message")
    println(customPublicClass.customPublicMessage)
}