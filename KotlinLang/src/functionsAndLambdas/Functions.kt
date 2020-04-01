package functionsAndLambdas

/**
 * Functions in Kotlin are declared using the fun keyword:
 *
 * https://kotlinlang.org/docs/reference/functions.html
 *
 **/

fun main() {
    // TODO Function usage
    functionUsage()

    // TODO Parameters
    parameters()

    println("\n****************************\n")

    // TODO Default arguments
    defaultArguments()

    println("\n****************************\n")

    // TODO Named arguments
    namedArguments()

    println("\n****************************\n")

    // TODO Unit-returning functions
    unitReturningFunctions()

    println("\n****************************\n")

    // TODO Single-expression functions
    singleExpressionFunctions()

    // TODO Explicit return types
    explicitReturnTypes()

    println("\n****************************\n")

    // TODO Variable number of arguments (Varargs)
    variableNumberOfArguments()

    println("\n****************************\n")

    // TODO Infix notation
    infixNotation()

    println("\n****************************\n")

    // TODO Function scope
    functionScope()

    // TODO Generic functions : look at Generics.kt
    // TODO Inline functions : look at InlineFunctions.kt
    // TODO Extension functions : look at Extensions.kt
    // TODO Higher-order functions and lambdas : look at Lambdas.kt

    println("\n****************************\n")

    // TODO Tail recursive functions
    tailRecursiveFunctions()
}

fun getResult(): Int {
    return 3
}

class Stream {
    fun read() {
        println("Stream reading...")
    }
}

fun functionUsage() {
    // Calling functions uses the traditional approach:
    val result = getResult()

    // Calling member functions uses the dot notation:
    Stream().read() // create instance of class Stream and call read()
}

fun parameters() {
    // Function parameters are defined using Pascal notation, i.e. name: type.
    // Parameters are separated using commas. Each parameter must be explicitly typed:

    // fun powerOf(number: Int, exponent: Int) { }
}

fun read(arr: Array<String>, off: Int = 0, len: Int = arr.size) {
    println("All item size $len , first element : ${arr[off]}")
}

open class A {
    open fun showValue(value: Int = 10) {
        println("A class showValue is $value")
    }
}

class B : A() {
    override fun showValue(value: Int) {
        println("B class showValue is $value")
    }  // no default value allowed

    fun showCountAndValue(count: Int = 0, value: String) {
        println("B class showValue is $count and $value")
    }
}

fun showMessage(messageCount: Int = 0, message: String = "default message", show: (Int, String) -> Unit) {
    show(messageCount, message)
}

fun defaultArguments() {
    // Function parameters can have default values, which are used when a corresponding argument is omitted.
    // This allows for a reduced number of overloads compared to other languages:
    println("Default arguments : ")

    val cities: Array<String> = arrayOf("İstanbul", "Bursa")
    read(cities)

    // or
    read(cities, 1, 2)

    println("\n--------------\n")

    // Overriding methods always use the same default parameter values as the base method. When overriding
    // a method with default parameter values, the default parameter values must be omitted from the signature:
    val b = B()
    b.showValue()
    b.showValue(10)

    println("\n--------------\n")

    // If a default parameter precedes a parameter with no default value, the default value can only be used by
    // calling the function with named arguments:
    b.showCountAndValue(value = "Hi!") // count parameter has default value, so you can this
    // b.showCountAndValue(count = 5) // value parameter hasn't default value, you can't this

    println("\n--------------\n")

    // If the last argument after default parameters is a lambda, it can be passed in either as a named argument or
    // outside the parentheses:

    showMessage(1, "First message") { count, message ->
        println("$count. message is $message")
    }

    // Uses the default value message
    showMessage(2, show = { count: Int, message: String ->
        println("$count. message is $message")
    })

    // Uses both default messageCount count and message
    showMessage { i, s -> println("$i. message is $s") }

}

fun reformatString(
    str: String,
    upperCaseFirstLetter: Boolean = false,
    loverCaseFirstLetter: Boolean = false
) {
    when {
        upperCaseFirstLetter -> println(str.toUpperCase())
        loverCaseFirstLetter -> println(str.toLowerCase())
        else -> println(str)
    }
}

fun showAllMessage(vararg messages: String) {
    for (message in messages) {
        println(message)
    }
}

fun namedArguments() {
    // Function parameters can be named when calling functions. This is very convenient when a function has a high number
    // of parameters or default ones.

    // Given the following function, look at reformatString function

    println("Named arguments : ")

    // We could call this using default arguments:
    reformatString("Default Message")

    // However, when calling it with non-default, the call would look something like:
    reformatString("Default Message", false, false)

    // With named arguments we can make the code much more readable:
    /* fun reformatString(
        str: String,
        upperCaseFirstLetter = true,
        loverCaseFirstLetter = false )
    */

    // and if we do not need all arguments:
    reformatString("LowerCase Message", loverCaseFirstLetter = true)

    // When a function is called with both positional and named arguments, all the positional arguments should be
    // placed before the first named one. For example, the call f(1, y = 2) is allowed, but f(x = 1, 2) is not.

    // reformatString("LowerCase Message", true, loverCaseFirstLetter = true) // allowed
    // reformatString("LowerCase Message", upperCaseFirstLetter = true, false) // not allowed

    // Variable number of arguments (vararg) can be passed in the named form by using the spread operator:
    showAllMessage(messages = *arrayOf("First message", "Second message"))

    // On the JVM: the named argument syntax cannot be used when calling Java functions because Java bytecode does not
    // always preserve names of function parameters.
}

fun sayHello(name: String?): Unit {
    if (name != null)
        println("Hello ${name}")
    else
        println("Hi there!")
    // `return Unit` or `return` is optional
}

fun unitReturningFunctions() {
    // If a function does not return any useful value, its return type is Unit. Unit is a type with only one value - Unit.
    // This value does not have to be returned explicitly:

    println("Unit returning functions : ")
    sayHello("Ali")
    sayHello(null)

    // The Unit return type declaration is also optional. The above code is equivalent to:
    // fun sayHello(name: String?) {}
}

fun singleExpressionFunctions() {
    // When a function returns a single expression,
    // the curly braces can be omitted and the body is specified after a = symbol:

    fun double(x: Int): Int {
        return x * 2
    }

    fun singleExpressionDouble(x: Int): Int = x * 2

    // Explicitly declaring the return type is optional when this can be inferred by the compiler:
    fun optionalSingleExpressionDouble(x: Int) = x * 2

    println("Single expression functions : ")
    println("Default fun call : ${double(2)}")
    println("Single expression fun call : ${singleExpressionDouble(2)}")
    println("Single optional expression fun call : ${optionalSingleExpressionDouble(2)}")
    println("The below functions are equivalent")
}

fun explicitReturnTypes() {
    // Functions with block body must always specify return types explicitly, unless it's intended for them to return Unit,
    // in which case (fun sayHello(name: String?): Unit ) it is optional. Kotlin does not infer return types for
    // functions with block bodies because such functions may have complex control flow in the body,
    // and the return type will be non-obvious to the reader (and sometimes even for the compiler).
}

fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

fun variableNumberOfArguments() {
    // A parameter of a function (normally the last one) may be marked with vararg modifier, look at asList function

    // allowing a variable number of arguments to be passed to the function:
    println("Variable number of arguments (Varargs) : ")
    val strList = asList("Ayşe", "Fatma", "Hayriye")
    println(strList)
    val intList = asList(1, 2, 3)
    println(intList)

    // Inside a function a vararg-parameter of type T is visible as an array of T, i.e. the ts variable in the example
    // above has type Array<out T>.

    // Only one parameter may be marked as vararg. If a vararg parameter is not the last one in the list,
    // values for the following parameters can be passed using the named argument syntax, or,
    // if the parameter has a function type, by passing a lambda outside parentheses.

    // When we call a vararg-function, we can pass arguments one-by-one, e.g. asList(1, 2, 3), or,
    // if we already have an array and want to pass its contents to the function,
    // we use the spread operator (prefix the array with *):
    val a = arrayOf(1, 2, 3)
    val list = asList(-1, 0, *a, 4)
    println(list)
}

infix fun Int.sqr(x: Int): Int {
    return x * x
}


fun infixNotation() {
    /* Functions marked with the infix keyword can also be called using the infix notation (omitting the dot and
    the parentheses for the call). Infix functions must satisfy the following requirements:

      - They must be member functions or extension functions;
      - They must have a single parameter;
      - The parameter must not accept variable number of arguments and must have no default value. */

    println("Infix function : ")

    // calling the function using the infix notation
    println("Int sqr result is ${1 sqr 2}")

    // is the same as
    println("Int sqr result is ${1.sqr(3)}")

    // Infix function calls have lower precedence than the arithmetic operators, type casts, and the rangeTo operator.
    // The following expressions are equivalent:
    println("Int sqr result is ${1 sqr (2 + 3)}")
    println("Int sqr result is ${1 sqr "12".toInt()}")

    // On the other hand, infix function call's precedence is higher than that of
    // the boolean operators && and ||, is- and in-checks, and some other operators.
    // These expressions are equivalent as well:

    println("Int sqr result is ${1 sqr 5 or 4}") // xor is infix function

    val orResult = 5 or 4
    println("Int sqr result is ${1 sqr orResult}") // compare above code, both are different

    println("\n--------------\n")

    // Note that infix functions always require both the receiver and the parameter to be specified. When you're calling
    // a method on the current receiver using the infix notation, you need to use this explicitly; unlike regular
    // method calls, it cannot be omitted. This is required to ensure unambiguous parsing.

    class InfixString {

        infix fun addStr(s: String) { // infix fun must be member fun or extension fun, this is member fun
            println(s + s)
        }

        fun buildStr() {
            this addStr "AAA" // Correct
            this.addStr("BBB") // Correct
            // addStr "CCC" // Mistake
        }
    }

    InfixString().buildStr()
}

fun functionScope() {
    // In Kotlin functions can be declared at top level in a file, meaning you do not need to create a class to hold
    // a function, which you are required to do in languages such as Java, C# or Scala.
    // In addition to top level functions, Kotlin functions can also be declared local, as member functions
    // and extension functions.

    // TODO Local functions
    // Kotlin supports local functions, i.e. a function inside another function.
    // Local function can access local variables of outer functions (i.e. the closure), so in the case above,
    // the visited can be a local variable:

    fun showMessage(user: String, message: String) {
        fun sayHello() {
            print("Hi $user! ")
        }

        sayHello()
        print(message)
    }

    showMessage("Okan", "Welcome to Kotlin :)")

    // TODO Member functions
    // A member function is a function that is defined inside a class or object :
    class MessageHelper {
        fun showMessage(user: String, message: String) {
            println("Hi $user! $message")
        }
    }

    // Member functions are called with dot notation:
    MessageHelper().showMessage("Büşra", "Welcome to Kotlin :)")
}

fun tailRecursiveFunctions() {
    // Kotlin supports a style of functional programming known as tail recursion. This allows some algorithms that
    // would normally be written using loops to instead be written using a recursive function, but without the risk of
    // stack overflow. When a function is marked with the tailrec modifier and meets the required form,
    // the compiler optimises out the recursion, leaving behind a fast and efficient loop based version instead:

    val eps = 1E-10 // "good enough", could be 10^-15

    tailrec fun findFixPoint(x: Double = 1.0): Double =
        if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))

    println("Tail Recursive Functions : ")
    println(findFixPoint())

    // This code calculates the fixpoint of cosine, which is a mathematical constant. It simply calls Math.cos repeatedly
    // starting at 1.0 until the result doesn't change any more, yielding a result of 0.7390851332151611 for t
    // he specified eps precision. The resulting code is equivalent to this more traditional style:

    fun findFixPoint(): Double {
        var x = 1.0
        while (true) {
            val y = Math.cos(x)
            if (Math.abs(x - y) < eps) return x
            x = Math.cos(x)
        }
    }
    println(findFixPoint())

    // To be eligible for the tailrec modifier, a function must call itself as the last operation it performs.
    // You cannot use tail recursion when there is more code after the recursive call,
    // and you cannot use it within try/catch/finally blocks. Currently, tail recursion is supported by Kotlin for
    // JVM and Kotlin/Native.
}