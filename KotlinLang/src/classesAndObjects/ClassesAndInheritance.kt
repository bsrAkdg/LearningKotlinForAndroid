package classesAndObjects

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

    println("\n****************************\n")

    secondaryConstructorWithoutThis()

    println("\n****************************\n")

    primaryPrivateConstructor()

    println("\n****************************\n")

    // TODO Inheritance
    anyClass()

    println("\n****************************\n")

    derivedClass()

    println("\n****************************\n")

    // TODO Overriding Methods

    derivedOverridingClass()

    println("\n****************************\n")

    derivedFinalOverridingClass()

    println("\n****************************\n")

    // TODO Overriding Properties

    derivedOverridingPropertyClass()

    println("\n****************************\n")

    derivedOverridingConstructorProperty()

    println("\n****************************\n")

    // TODO Derived class initialization order

    derivedClassInitializationOrder()

    println("\n****************************\n")

    // TODO Calling the superclass implementation

    callingTheSuperClassImplementation()

    println("\n****************************\n")

    callingTheSuperInnerClassImplementation()

    println("\n****************************\n")

    // TODO Abstract classes
    abstractClasses()

    println("\n****************************\n")

    // TODO Companion objects
    companionObjects()
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
    // or read-only (val)

    class InitOrderDemo(val name: String, var index: Int) {

        val firstProperty = "First property : $name ".also { println(it) }

        init {
            println("First initializer 1")
        }

        init {
            println("First initializer 2")
        }

        // primary constructor can be used in property initializers declared in the class body
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

        // primary constructor can be used in the initializer blocks
        init {
            println("Init block : $firstName")
        }

        var age = 0

        // 1. The class can also declare secondary constructors, which are prefixed with constructor
        // 2. If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor,
        // either directly or indirectly through another secondary constructor(s). Delegation to another constructor of
        // the same class is done using the this keyword:
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


fun secondaryConstructorWithoutThis() {
    // Note that code in initializer blocks effectively becomes part of the primary constructor.
    // Delegation to the primary constructor happens as the first statement of a secondary constructor,
    // so the code in all initializer blocks and property initializers is executed before the secondary constructor body.
    // Even if the class has no primary constructor, the delegation still happens implicitly,
    // and the initializer blocks are still executed

    class Constructors {
        init {
            println("Init block")
        }

        constructor(i: Int) {
            println("Constructor")
        }
    }

    println(Constructors(1))
}

fun primaryPrivateConstructor() {
    // If a non-abstract class does not declare any constructors (primary or secondary),
    // it will have a generated primary constructor with no arguments. The visibility of the constructor will be public.
    // If you do not want your class to have a public constructor, you need to declare
    // an empty primary constructor with non-default visibility:

    println(PublicConstructorClass("büşra"))
    // println(PrivateConstructorClass("büşra")) TODO look at this line

}

class PublicConstructorClass(firstName: String) {
    init {
        println("PublicConstructorClass init : $firstName")
    }
}

class PrivateConstructorClass private constructor(firstName: String) {
    init {
        println("PrivateConstructorClass init : $firstName")
    }
}

fun anyClass() {
    // All classes in Kotlin have a common superclass Any,
    // that is the default superclass for a class with no supertypes declared:
    // Any has three methods: equals(), hashCode() and toString(). Thus, they are defined for all Kotlin classes.

    class Base { // Implicitly inherits from Any
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun toString(): String {
            return super.toString()
        }
    }
}

fun derivedClass() {
    // To declare an explicit supertype, place the type after a colon in the class header

    open class Base(number: Int)

    // 1. If the derived class has a primary constructor, the base class can (and must) be initialized right there,
    // using the parameters of the primary constructor.
    class DerivedHasPrimary(number: Int) : Base(number)

    // 2. or used constructor
    class DerivedNoPrimary : Base {
        constructor(number: Int) : super(number)
    }

    println(DerivedHasPrimary(9))
    println(DerivedNoPrimary(9))

}

fun derivedOverridingClass() {

    open class Shape {
        open fun draw() {
            println("Shape Drawing...")
        }

        fun fill() {
            println("Shape Filling...")
        }
    }

    class Circle : Shape() {

        override fun draw() {
            super.draw()
            println("Circle Drawing...")

        }

        // Cannot override fill() method
    }

    val circle = Circle()
    println(circle.draw())
    println(circle.fill())
}

fun derivedFinalOverridingClass() {

    open class Shape {
        open fun draw() {
            println("Shape Drawing...")
        }

        fun fill() {
            println("Shape Filling...")
        }
    }

    open class Circle : Shape() {
        // A member marked override is itself open, i.e. it may be overridden in subclasses.
        // If you want to prohibit re-overriding, use final

        final override fun draw() {
            super.draw()
            println("Circle Drawing...")

        }

        // Cannot override fill() method (fill() dont include open keyword on Shape class)
    }

    class BigCircle : Circle() {
        // Cannot override draw() method (draw() include final keyword on Circle class)
    }

    val bigCircle = BigCircle()
    println(bigCircle.draw())
    println(bigCircle.fill())
}

fun derivedOverridingPropertyClass() {
    // Overriding properties works in a similar way to overriding methods;
    // properties declared on a superclass that are then redeclared on a derived class must be prefaced with override,
    // and they must have a compatible type. Each declared property can be overridden by a property with an initializer
    // or by a property with a get method.

    open class Shape {
        open val vertexCount: Int = 0
        open var width: Int = 100

        open fun showVertexCount() {
            println("Vertex count = $vertexCount")
        }

        open fun showWidth() {
            println("Width = $width")
        }
    }

    class Rectangle : Shape() {
        // You can also override a val property with a var property, but not vice versa.
        // This is allowed because a val property essentially declares a get method,
        // and overriding it as a var additionally declares a set method in the derived class.
        // override var vertexCount = 4 true using because vertexCount is val on Shape
        // override val width : Int = 200 wrong using because width is var on Shape

        override val vertexCount = 4

        override fun showVertexCount() {
            super.showVertexCount()
            println("Vertex count = $vertexCount")
        }
    }

    val rectangle = Rectangle()
    rectangle.showVertexCount()
    rectangle.showWidth()
}

interface Shape {
    val vertexCount: Int
}

fun derivedOverridingConstructorProperty() {
    // Note that you can use the override keyword as part of the property declaration in a primary constructor.

    class Rectangle(override val vertexCount: Int) : Shape

    class Polygon : Shape {
        override var vertexCount: Int = 0 // Can be set to any number later
    }

    val rectangle = Rectangle(4)
    val polygon = Polygon()
    polygon.vertexCount = 5

    println(rectangle.vertexCount)
    println(polygon.vertexCount)
}

fun derivedClassInitializationOrder() {
    // During construction of a new instance of a derived class,
    // the base class initialization is done as the first step (preceded only by evaluation of the arguments for
    // the base class constructor) and thus happens before the initialization logic of the derived class is run.

    open class Base(val name: String) {

        init {
            println("Initializing Base")
        }

        open val size: Int = name.length.also {
            println("Initializing size in Base: $it")
        }
    }

    class Derived(
        name: String,
        lastName: String
    ) : Base(name.capitalize().also { println("Argument for Base: $it") }) {

        init {
            println("Initializing Derived")
        }

        override val size: Int =
            (super.size + lastName.length).also {
                println("Initializing size in Derived: $it")
            }
    }

    Derived("Büşra", "Akdağ")
}

fun callingTheSuperClassImplementation() {
    // Code in a derived class can call its superclass functions and
    // property accessors implementations using the super keyword:

    open class Rectangle {
        open fun draw() {
            println("Drawing a rectangle on Rectangle")
        }

        val borderColor: String get() = "black"
    }

    class FilledRectangle : Rectangle() {
        override fun draw() {
            super.draw()
            println("Drawing a rectangle on FilledRectangle")
        }

        val fillColor: String get() = super.borderColor
    }

    val rectangle = Rectangle()
    rectangle.draw()

    val filledRectangle = FilledRectangle()
    filledRectangle.draw()
    println(filledRectangle.fillColor)

}

fun callingTheSuperInnerClassImplementation() {
    // Inside an inner class, accessing the superclass of the outer class is done with
    // the super keyword qualified with the outer class name: super@Outer:

    open class Rectangle {
        open fun draw() {
            println("Drawing a rectangle on Rectangle")
        }

        val borderColor: String get() = "black"
    }

    class FilledRectangle: Rectangle() {

        override fun draw() {
            super.draw()
            println("Drawing a rectangle on FilledRectangle")
        }

        fun fill() {
            println("Filling a rectangle on FilledRectangle")
        }

        inner class Filler {

            fun fill() {
                //super@FilledRectangle.fill() // Cannot call FilledRectangle's implementation of fill()
                println("Filling")
            }

            fun drawAndFill() {
                super@FilledRectangle.draw() // Calls Rectangle's implementation of draw()
                fill()
                // Uses Rectangle's implementation of borderColor's get()
                println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}")
            }
        }
    }
}


fun abstractClasses() {
    // A class and some of its members may be declared abstract. An abstract member
    // does not have an implementation in its class.
    open class Polygon {
        open fun draw() {
            println("Drawing on Polygon")
        }
    }

    abstract class Rectangle : Polygon() {
        override abstract fun draw()
    }

    class Circle : Rectangle() {
        override fun draw() {
            println("Drawing on Circle")
        }

    }

    val circle = Circle()
    circle.draw()
}

class Helper {

    fun setHelperSettings() {
        println("setHelperSettings called")
    }

    companion object {
        fun changeHelperSettings() {
            println("changeHelperSettings called")
        }
    }
}

fun companionObjects() {
    // If you need to write a function that can be called without having a class instance but
    // needs access to the internals of a class (for example, a factory method), you can write it as a member of
    // an object declaration inside that class.

    // You call changeHelperSettings method without instance of Helper
    Helper.changeHelperSettings()

    // But you don't call setHelperSettings method without of Helper
    //Helper.setHelperSettings()

    // You should create instance
    val helper = Helper()
    helper.setHelperSettings()
}
