package classesAndObjects

/**
 * Inline classes are available only since Kotlin 1.3 and currently are experimental.
 *
 * https://kotlinlang.org/docs/reference/inline-classes.html
 *
 **/


fun main() {
    // TODO Inline classes overview
    inlineOverview()

    println("\n****************************\n")

    // TODO Members
    inlineMembers()

    println("\n****************************\n")

    // TODO Inheritance
    inlineInheritance()

    println("\n****************************\n")

    // TODO Representation
    representation()

    println("\n****************************\n")

    // TODO Mangling
    inlineMangling()

    println("\n****************************\n")

    // TODO Inline classes vs type aliases
    inlineClassesTypeAliases()

    println("\n****************************\n")

    // TODO Experimental status of inline classes
    experimentalStatusOfInlineClasses()

    println("\n****************************\n")

    // TODO Important
    //  See this language proposal for inline classes for other technical details and discussion.
    //  Look at see more https://github.com/Kotlin/KEEP/blob/master/proposals/inline-classes.md
}

inline class Password(val value: String)

fun inlineOverview() {
    // Sometimes it is necessary for business logic to create a wrapper around some type.
    // However, it introduces runtime overhead due to additional heap allocations.
    // Moreover, if the wrapped type is primitive, the performance hit is terrible,
    // because primitive types are usually heavily optimized by the runtime, while their wrappers don't get any special treatment.

    // To solve such issues, Kotlin introduces a special kind of class called an inline class,
    // which is declared by placing an inline modifier before the name of the class, look at Password

    // An inline class must have a single property initialized in the primary constructor. At runtime, instances of the inline class will be represented using this single property (see details about runtime representation below):

    // No actual instantiation of class 'Password' happens
    // At runtime 'securePassword' contains just 'String'
    val securePassword = Password("Don't try this in production")
    println(securePassword.value)

    // This is the main feature of inline classes, which inspired the name "inline": data of the class is "inlined"
    // into its usages (similar to how content of inline functions is inlined to call sites).

    println("\n--------------\n")

    var a = 0
    // Fast (inline fun doesn't create Function instance )
    repeat(10) {
        a += 1
    }

    var b = 0
    // Slow (noinline fun creates Function instance )
    noinlineRepeat(10) {
        b += 1
    }
}

inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
        println("after repeat $index")
    }
}

fun noinlineRepeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
        println("after noinlineRepeat $index")
    }
}

inline class InlineName(val s: String) {
    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }
}

fun inlineMembers() {
    // Inline classes support some functionality of regular classes.
    // In particular, they are allowed to declare properties and functions:

    val name = InlineName("Kotlin")
    name.greet() // method `greet` is called as a static method
    println(name.length) // property getter is called as a static method

    // However, there are some restrictions for inline class members:
    // 1. inline classes cannot have init blocks
    // 2. inline class properties cannot have backing fields
    // 3. it follows that inline classes can only have simple computable properties (no lateinit/delegated properties)

}

interface Printable {
    fun prettyPrint(): String
}

inline class InlinePrintableName(val s: String) : Printable {
    override fun prettyPrint(): String = "Let's $s!"
}

fun inlineInheritance() {
    // Inline classes are allowed to inherit from interfaces:

    val name = InlinePrintableName("Kotlin")
    println(name.prettyPrint()) // Still called as a static method

    // It is forbidden for inline classes to participate in a class hierarchy.
    // This means that inline classes cannot extend other classes and must be final.
}

interface OrderInterface {
    fun startOrder()
}

inline class InlineOrder(val orderId: Int) : OrderInterface {
    override fun startOrder() {
        println("Started $orderId. order")
    }
}

fun createOrder(order: InlineOrder) {
    println("Created ${order.orderId}. order")
}

fun <T> asGeneric(genericClass: T) {}

fun startOrder(orderInterface: OrderInterface) {
    orderInterface.startOrder()
}

fun asNullableOrder(order: InlineOrder?) {}

fun <T> repeat(genericClass: T): T = genericClass

fun representation() {
    // In generated code, the Kotlin compiler keeps a wrapper for each inline class.
    // Inline class instances can be represented at runtime either as wrappers or as the underlying type.
    // This is similar to how Int can be represented either as a primitive int or as the wrapper Integer.

    // https://kotlinlang.org/docs/reference/basic-types.html#representation

    // The Kotlin compiler will prefer using underlying types instead of wrappers to produce the most performant
    // and optimized code. However, sometimes it is necessary to keep wrappers around. As a rule of thumb,
    // inline classes are boxed whenever they are used as another type.

    // TODO Boxing - Unboxing
    // The casting of variable types into reference types is called boxing,
    // and the casting of reference types into variable types is called unboxing.

    // TODO Underlying value
    // val value: T  --> the underlying value

    val newOrder = InlineOrder(42)

    createOrder(newOrder)    // unboxed: used as InlineOrder itself

    asGeneric(newOrder)   // boxed: used as generic type T

    startOrder(newOrder) // boxed: used as type OrderInterface

    asNullableOrder(newOrder)  // boxed: used as InlineOrder?, which is different from InlineOrder

    // below, 'newOrder' first is boxed (while being passed to 'repeat') and then unboxed (when returned from 'repeat')
    // In the end, 'newOrderRepeat' contains unboxed representation (just '42'), as 'newOrder'
    val newOrderRepeat = repeat(newOrder)

    // Because inline classes may be represented both as the underlying value and as a wrapper,
    // referential equality is pointless for them and is therefore prohibited.

    // TODO Referential equality
    // Referential equality is checked by the === operation (and its negated counterpart !==).
    // a === b evaluates to true if and only if a and b point to the same object.
    // For values which are represented as primitive types at runtime (for example, Int),
    // the === equality check is equivalent to the == check.
}

inline class UInt(val x: Int)

fun inlineMangling() {
    // Since inline classes are compiled to their underlying type, it may lead to various obscure errors,
    // for example unexpected platform signature clashes:

    // Represented as 'public final void compute(int x)' on the JVM
    fun compute(x: Int) {
        println("Int compute")
    }

    // Also represented as 'public final void compute(int x)' on the JVM!
    fun compute(x: UInt) {
        println("UInt compute")
    }

    compute(UInt(5))
    compute(5)

    // To mitigate such issues, functions using inline classes are mangled by adding some stable hashcode to the function name.
    // Therefore, fun compute(x: UInt) will be represented as public final void compute-<hashcode>(int x), which solves the clash problem.

    // Note that - is an invalid symbol in Java, meaning that it's impossible to call functions which accept inline classes from Java.
}

typealias StringTypeAlias = String

inline class StringInlineClass(val str: String)

fun acceptString(str: String) {
    println("String $str")
}

fun acceptStringTypeAlias(stringTypeAlias: StringTypeAlias) {
    println("StringTypeAlias $stringTypeAlias")
}
fun acceptStringInlineClass(stringInlineClass: StringInlineClass) {
    println("StringInlineClass $stringInlineClass")
}

fun inlineClassesTypeAliases() {
    // At first sight, inline classes may appear to be very similar to type aliases.
    // Indeed, both seem to introduce a new type and both will be represented as the underlying type at runtime.

    // However, the crucial difference is that type aliases are assignment-compatible with
    // their underlying type (and with other type aliases with the same underlying type), while inline classes are not.

    // In other words, inline classes introduce a truly new type, contrary to type aliases which only introduce
    // an alternative name (alias) for an existing type:

    val stringAlias: StringTypeAlias = ""
    val nameInlineClass: StringInlineClass = StringInlineClass("")
    val string: String = ""

    acceptString(stringAlias) // OK: pass alias instead of underlying type
    // acceptString(nameInlineClass) // Not OK: can't pass inline class instead of underlying type

    // And vice versa:
    acceptStringTypeAlias(string) // OK: pass underlying type instead of alias
    //acceptStringInlineClass(string) // Not OK: can't pass underlying type instead of inline class
}

fun experimentalStatusOfInlineClasses() {
    // The design of inline classes is experimental, meaning that this feature is moving fast and
    // no compatibility guarantees are given. When using inline classes in Kotlin 1.3+, a warning will be reported,
    // indicating that this feature is experimental.

    // To remove the warning you have to opt in to the usage of this experimental feature by passing
    // the compiler argument -Xinline-classes.

    // TODO Enabling inline classes in Gradle

    /*
    compileKotlin {
        kotlinOptions.freeCompilerArgs += ["-Xinline-classes"]
    }
    */
}