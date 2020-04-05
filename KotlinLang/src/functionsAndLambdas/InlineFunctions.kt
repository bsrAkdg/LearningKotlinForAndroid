package functionsAndLambdas

/**
 *
 * https://kotlinlang.org/docs/reference/inline-functions.html
 *
 **/

private var localVariable: Int = 8

fun main() {

    // TODO Why needed inline functions
    // https://medium.com/@agrawalsuneet/inline-function-kotlin-3f05d2ea1b59 (Look at this blog)
    whyNeededInlineFunExample()

    println("\n****************************\n")

    // TODO Inline Functions
    inlineFunctionsOverview()

    // TODO noinline
    noinline()

    println("\n****************************\n")

    // TODO Non-local returns
    nonLocalReturns()

    println("\n****************************\n")

    // TODO Reified type parameters
    reifiedTypeParameters()

    println("\n****************************\n")

    // TODO Inline properties (since 1.1)
    inlineProperties()

    // TODO Restrictions for public API inline functions
    restrictionsForPublicAPIInlineFunctions()
}

fun isMultipleOfNoInner(number: Int, multipleOf: Int): Boolean {
    return number % multipleOf == 0
}

inline fun isMultipleOf(number: Int, multipleOf: Int): Boolean {
    // you can't use localVariable in inline fun
    return number % multipleOf == 0
}

fun <T> ArrayList<T>.filterOnCondition(condition: (T) -> Boolean): ArrayList<T> {
    val result = arrayListOf<T>()
    for (item in this) {
        if (condition(item)) {
            result.add(item)
        }
    }
    return result
}

fun whyNeededInlineFunExample() {
    var list = arrayListOf<Int>()
    for (number in 1..10) {
        list.add(number)
    }

    val resultListNoInner = list.filterOnCondition { isMultipleOfNoInner(it, 5) }
    print(resultListNoInner)

    /*
    In the above example, the functionality of the isMultipleOf is so small that it can be copied but as it can be
    used at multiple places, we created it as a separate function. As this function is passed as an argument to
    filterOnCondition function, CPU will assign it to some object which will take some memory. Also, for every item in
    the list, the CPU will jump to the memory address of isMultipleOf function and will pass back to result.

    To prevent this, we can make the isMultipleOf function as inline which will as the compiler to copy it to
    the calling place which will prevent the memory allocation of the function and jumping of the CPU at runtime.

    The functionality will remain the same. */

    val resultList = list.filterOnCondition { isMultipleOf(it, 5) }
    println(resultList)

    // Things to keep in mind

    // 1. We will lose access to any private variable or function of the class inside the inline function.
    // So it’s better to make functions inline which are very generic and don’t use a class level variable
    // or function to achieve their functionality. Look at localVariable note in isMultipleOf function.

    // 2. Do not inline for bigger functions as it degrades the performance.
}

fun inlineFunctionsOverview() {
    /*
    Using higher-order functions imposes certain runtime penalties: each function is an object, and it captures
    a closure, i.e. those variables that are accessed in the body of the function. Memory allocations (both for function
    objects and classes) and virtual calls introduce runtime overhead.

    But it appears that in many cases this kind of overhead can be eliminated by inlining the lambda expressions.
    The functions shown below are good examples of this situation. I.e., the lock() function could be easily inlined
    at call-sites. Consider the following case:

    lock(l) { foo() }

    Instead of creating a function object for the parameter and generating a call, the compiler could emit the following code:

    l.lock()
    try {
        foo()
    }
    finally {
        l.unlock()
    }

    Isn't it what we wanted from the very beginning?

    To make the compiler do this, we need to mark the lock() function with the inline modifier:

    inline fun <T> lock(lock: Lock, body: () -> T): T { ... }

    The inline modifier affects both the function itself and the lambdas passed to it: all of those will be inlined
    into the call site.

    Inlining may cause the generated code to grow; however, if we do it in a reasonable way (i.e. avoiding inlining
    large functions), it will pay off in performance, especially at "megamorphic" call-sites inside loops.
    */
}

inline fun printSomething(aLambda: () -> Unit, noinline noInlineLambda: () -> Unit, aLambda2: () -> Unit) {

    println("Do something ...")

    aLambda()
    noInlineLambda() //won't be inlined.
    aLambda2()

    println("Do another something ...")

}

fun noinline() {
    // In case you want only some of the lambdas passed to an inline function to be inlined,
    // you can mark some of your function parameters with the noinline modifier:

    printSomething(
        { println("inline first fun called") },
        { println("noinline fun called") },
        { println("inline second fun called") })

    /*
    Inlinable lambdas can only be called inside the inline functions or passed as inlinable arguments,
    but noinline ones can be manipulated in any way we like: stored in fields, passed around etc.

    Note that if an inline function has no inlinable function parameters and no reified type parameters,
    the compiler will issue a warning, since inlining such functions is very unlikely to be beneficial (you can suppress
    the warning if you are sure the inlining is needed using the annotation @Suppress("NOTHING_TO_INLINE")).

    TODO Reified type parameters
    https://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters */
}

fun ordinaryFunction(action: () -> Unit) {
    action()
}

inline fun inlined(action: () -> Unit) {
    action()
}

fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true // returns from hasZeros
    }
    return false
}

inline fun higherOrderFunction(crossinline lambda: () -> Unit) {
    normalFunction {
        println("This is nested function")
        lambda() //must mark aLambda as crossinline to use in this context.
    }
}

fun normalFunction(lambda: () -> Unit) {
    println("This is normal function")
    lambda()
    return
}

fun nonLocalReturns() {
    /* In Kotlin, we can only use a normal, unqualified return to exit a named function or an anonymous function.
    This means that to exit a lambda, we have to use a label, and a bare return is forbidden inside a lambda,
    because a lambda cannot make the enclosing function return: */

    fun sayHelloOne() {
        ordinaryFunction {
            // return println("Hello, this is ordinaryFunction") // ERROR: cannot make `foo` return here
        }
    }

    // But if the function the lambda is passed to is inlined, the return can be inlined as well, so it is allowed:

    fun sayHelloTwo() {
        inlined {
            return println("Hello, this is inlined ")
        }
    }

    sayHelloOne()
    sayHelloTwo()

    println("\n--------------\n")

    // Such returns (located in a lambda, but exiting the enclosing function) are called non-local returns.
    // We are used to this sort of construct in loops, which inline functions often enclose:

    val intsHasZero = arrayListOf(-1, 3, -2, 4, 2, 0)
    val intsHasNoZero = arrayListOf(-1, 3, -2, 4, 2, 3)

    println("Has intsHasZero zero ? ${hasZeros(intsHasZero)}")
    println("Has intsHasNoZero zero ? ${hasZeros(intsHasNoZero)}")

    println("\n--------------\n")

    /*
    Note that some inline functions may call the lambdas passed to them as parameters not directly from the function
    body, but from another execution context, such as a local object or a nested function. In such cases,
    non-local control flow is also not allowed in the lambdas. To indicate that, the lambda parameter needs to be marked
    with the crossinline modifier:
    */

    higherOrderFunction { println("higherOrderFunction calling...") }

    // break and continue are not yet available in inlined lambdas, but we are planning to support them too.

}

fun <T> myGenericFun(cl: T) {
    // println(T::class.java.isInstance(String)) // You can't access T class
}

inline fun <reified T> myGenericFunInlined() {
    println("Is T a String class? ${T::class.java.isInstance(String)}") // You can access T class
}

fun reifiedTypeParameters() {
    // Sometimes we need to access a type passed to us as a parameter.
    // Look at myGenericFun and myGenericFunInlined functions

    myGenericFunInlined<String>()
    myGenericFun("Hi!")

    // Normal functions (not marked as inline) cannot have reified parameters. A type that does not have
    // a run-time representation (e.g. a non-reified type parameter or a fictitious type like Nothing) cannot be used
    // as an argument for a reified type parameter.

    //For a low-level description, see the spec document.
    // https://github.com/JetBrains/kotlin/blob/master/spec-docs/reified-type-parameters.md
}

class Fruit

var apple: Fruit
    get() = Fruit()
    set(value) {

    }

var banana: Fruit
    inline get() = Fruit()
    inline set(value) {

    }

inline var strawberry: Fruit
    get() = Fruit()
    set(value) {

    }

fun inlineProperties() {
    // The inline modifier can be used on accessors of properties that don't have a backing field.
    // You can annotate individual property accessors: Look at apple and banana

    // You can also annotate an entire property, which marks both of its accessors as inline: Look at strawberry

    // At the call site, inline accessors are inlined as regular inline functions.
}

fun restrictionsForPublicAPIInlineFunctions() {
    /*
    When an inline function is public or protected and is not a part of a private or internal declaration,
    it is considered a module's public API. It can be called in other modules and is inlined at such call sites as well.

    This imposes certain risks of binary incompatibility caused by changes in the module that declares an inline
    function in case the calling module is not re-compiled after the change.

    To eliminate the risk of such incompatibility being introduced by a change in non-public API of a module,
    the public API inline functions are not allowed to use non-public-API declarations, i.e. private
    and internal declarations and their parts, in their bodies.

    An internal declaration can be annotated with @PublishedApi, which allows its use in public API inline functions.
    When an internal inline function is marked as @PublishedApi, its body is checked too, as if it were public. */
}