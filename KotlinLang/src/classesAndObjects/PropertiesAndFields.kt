package ClassesAndObjects

import java.lang.NullPointerException

/**
 * The class declaration consists of the properties and fields
 * https://kotlinlang.org/docs/reference/properties.html
 * */
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

fun main() {

    // TODO Declaring Properties
    declaringProperties()

    println("\n****************************\n")

    // TODO Getters and Setters
    gettersAndSetters()

    println("\n****************************\n")

    // TODO Backing Fields
    backingFields()

    println("\n****************************\n")

    // TODO Backing Properties
    backingProperties()

    println("\n****************************\n")

    // TODO Compile-Time Constants
    compileTimeConstants()

    println("\n****************************\n")

    // TODO Late-Initialized Properties and Variables
    lateInitPropertiesAndVariables()
    println("\n****************************\n")

}

fun declaringProperties() {
    // Look at Address class
    val address = Address()
    address.name = "New address"
    address.street = "New street"
    copyAddress(address)
}

class Address {
    // Properties in Kotlin classes can be declared either as mutable using the var keyword,
    // or as read-only using the val keyword.
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip: String = "123456"
}

fun copyAddress(address: Address): Address {
    // To use a property, simply refer to it by name:
    val result = Address() // there's no 'new' keyword in Kotlin
    result.name = address.name // accessors are called
    result.street = address.street
    println(result.name)
    println(result.street)
    return result
}

fun gettersAndSetters() {
    // The full syntax for declaring a property is
    // var <propertyName>[: <PropertyType>] [= <property_initializer>] [<getter>] [<setter>]

    // The initializer, getter and setter are optional. Property type is optional
    // if it can be inferred from the initializer (or from the getter return type, as shown below).
    var allByDefault: Int? // error: explicit initializer required, default getter and setter implied
    var initialized = 1 // has type Int, default getter and setter

    // The full syntax of a read-only property declaration differs from a mutable one in two ways:
    // it starts with val instead of var and does not allow a setter:
    val simple: Int? // has type Int, default getter, must be initialized in constructor
    val inferredType = 1 // has type Int and a default getter

    // We can define custom accessors for a property. If we define a custom getter,
    // it will be called every time we access the property (this allows us to implement a computed property).
    // Here's an example of a custom getter:

    // If we define a custom setter, it will be called every time we assign a value to the property.
    // A custom setter looks like this:
    class CustomSetterClass {
        var stringRepresentation: String
            get() = this.toString()
            set(value) {
                println("Custom setter value is :  $value")
            }
    }

    val customSetterClass = CustomSetterClass()
    customSetterClass.stringRepresentation = "Hi!"

    println("\n****************************\n")

    // If you need to change the visibility of an accessor or to annotate it, but don't need to change
    // the default implementation, you can define the accessor without defining its body:
    class NoBodySetterClass {
        var setterVisibility: String = "abc"
            private set // the setter is private and has the default implementation

        var setterWithAnnotation: Any? = null
        // @Inject set // annotate the setter with Inject
    }

    val noBodySetterClass = NoBodySetterClass()
    noBodySetterClass.setterVisibility == "def"
    println(noBodySetterClass.setterVisibility)
}

fun backingFields() {
    // Fields cannot be declared directly in Kotlin classes. However, when a property needs a backing field,
    // Kotlin provides it automatically. This backing field can be referenced in the accessors
    // using the field identifier

    class BackingFields() {
        var counter = 0 // Note: the initializer assigns the backing field directly
            set(value) {
                // The field identifier can only be used in the accessors of the property.
                if (value >= 0) field = value
            }
    }

    val backingFields = BackingFields()
    println("old backing fields : ${backingFields.counter}")
    backingFields.counter = 2
    println("new backing fields : ${backingFields.counter}")
    backingFields.counter = -2
    println("new backing fields : ${backingFields.counter}")
}

fun backingProperties() {
    // If you want to do something that does not fit into this "implicit backing field" scheme,
    // you can always fall back to having a backing property

    class ImplicitProperty {
        private var _table: Map<String, Int>? = null
        public val table: Map<String, Int>
            get() {
                if (_table == null) {
                    _table = HashMap() // Type parameters are inferred
                }
                return _table ?: throw NullPointerException("Error!!!")
            }
    }

    val implicitProperty = ImplicitProperty()
    println("Get implicit property : " + implicitProperty.table)
}

fun compileTimeConstants() {
    /*
    If the value of a read-only property is known at the compile time, mark it as a compile time constant using
    the const modifier. Such properties need to fulfil the following requirements:
       -Top-level, or member of an object declaration or a companion object.
       -Initialized with a value of type String or a primitive type
       -No custom getter
        Such properties can be used in annotations:
    */
    class CompileTime {
        @Deprecated(SUBSYSTEM_DEPRECATED, ReplaceWith("println(\"Welcome!\")"))
        fun sayHello() {
            println("Hello!")
        }

        fun sayWelcome() {
            println("Welcome!")
        }
    }

    val compileTime = CompileTime()
    compileTime.sayHello() // This is deprecated
    compileTime.sayWelcome()
}

fun lateInitPropertiesAndVariables() {
    /*
    Normally, properties declared as having a non-null type must be initialized in the constructor.
    However, fairly often this is not convenient. For example, properties can be initialized through
    dependency injection, or in the setup method of a unit test. In this case, you cannot supply a non-null initializer
    in the constructor, but you still want to avoid null checks when referencing the property inside the body of a class.

    The modifier can be used on var properties declared inside the body of a class (not in the primary constructor,
    and only when the property does not have a custom getter or setter) and, since Kotlin 1.2, for top-level properties
    and local variables. The type of the property or variable must be non-null, and it must not be a primitive type.

    Accessing a lateinit property before it has been initialized throws a special exception that clearly identifies
    the property being accessed and the fact that it hasn't been initialized.

    This check is only available for the properties that are lexically accessible, i.e. declared in the same type
    or in one of the outer types, or at top level in the same file.
        if (foo::bar.isInitialized) {
            println(foo.bar)
        }
    */
}