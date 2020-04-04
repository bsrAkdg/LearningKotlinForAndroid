package functionsAndLambdas

/**
 *
 * https://kotlinlang.org/docs/reference/lambdas.html
 *
 **/

fun main() {
    // TODO Higher-Order Functions and Lambdas
    lambdasOverview()

    // TODO Higher-Order Functions
    higherOrderFunctions()

    println("\n****************************\n")

    // TODO Function types
    functionTypes()

    // TODO Instantiating a function type
    instantiatingFunctionType()

    println("\n****************************\n")

    // TODO Invoking a function type instance
    invokingFunctionTypeInstance()

    println("\n****************************\n")

    // TODO Lambda Expressions and Anonymous Functions
    lambdaAndAnonymousFunctions()

    println("\n****************************\n")

    // TODO Passing trailing lambdas
    passingTrailingLambda()

    println("\n****************************\n")

    // TODO it: implicit name of a single parameter
    itImplicitNameOfSingleParameter()

    println("\n****************************\n")

    // TODO Returning a value from a lambda expression
    returningValueFromLambdaExpression()

    println("\n****************************\n")

    // TODO Underscore for unused variables (since 1.1)
    underscoreForUnusedVariables()

    println("\n****************************\n")

    // TODO Destructuring Declarations : look at DestructuringDeclarations.kt
    // val (name, age) = person

    // TODO Anonymous functions
    anonymousFunctions()

    // TODO Closures
    closures()

    println("\n****************************\n")

    // TODO Function literals with receiver
    functionLiteralsWithReceiver()
}

fun lambdasOverview() {
    /* Kotlin functions are first-class, which means that they can be stored in variables and data structures,
    passed as arguments to and returned from other higher-order functions. You can operate with functions in any way
    that is possible for other non-function values.

    To facilitate this, Kotlin, as a statically typed programming language, uses a family of function types to represent
    functions and provides a set of specialized language constructs, such as lambda expressions. */
}

fun sum(numberOne: Int, numberTwo: Int): Int {
    return numberOne + numberTwo
}

fun sub(numberOne: Int, numberTwo: Int): Int {
    return numberOne - numberTwo
}

fun arithmeticOperations(transactionName: String, numberOne: Int, numberTwo: Int, transaction: (Int, Int) -> Int) {
    println("Your $transactionName result is = ${transaction(numberOne, numberTwo)}")
}

fun higherOrderFunctions() {
    // A higher-order function is a function that takes functions as parameters, or returns a function.

    println("Higher Order Functions : ")
    arithmeticOperations("Sum", 1, 3, ::sum)
    arithmeticOperations("Sub", 6, 1, ::sub)
}

fun functionTypes() {
    /*
    Kotlin uses a family of function types like (Int) -> String for declarations that deal with functions:
    val onClick: () -> Unit = ....

    These types have a special notation that corresponds to the signatures of the functions, i.e. their parameters
    and return values:

    1. All function types have a parenthesized parameter types list and a return type: (A, B) -> C denotes a type
    that represents functions taking two arguments of types A and B and returning a value of type C.
    The parameter types list may be empty, as in () -> A. The Unit return type cannot be omitted. */

    fun printHello(name: String?): Unit {
        if (name != null)
            println("Hello ${name}")
        else
            println("Hi there!")
        // `return Unit` or `return` is optional
    }

    /*
    2. Function types can optionally have an additional receiver type, which is specified before a dot in the notation:
    the type A.(B) -> C represents functions that can be called on a receiver object of A with a parameter of B and
    return a value of C. Function literals with receiver are often used along with these types(Next subject in this class).

    3. Suspending functions belong to function types of a special kind, which have a suspend modifier in the notation,
    such as suspend () -> Unit or suspend A.(B) -> C.

    TODO Suspending functions : https://stackoverflow.com/a/52925057/4133130
    Suspending functions are at the center of everything coroutines. A suspending function is simply a function that can
    be paused and resumed at a later time. They can execute a long running operation and wait for it to complete
    without blocking. The syntax of a suspending function is similar to that of a regular function except for the
    addition of the suspend keyword. It can take a parameter and have a return type. However, suspending functions can
    only be invoked by another suspending function or within a coroutine.

    The function type notation can optionally include names for the function parameters: (x: Int, y: Int) -> Point.
    These names can be used for documenting the meaning of the parameters.
        - To specify that a function type is nullable, use parentheses: ((Int, Int) -> Int)?.
        - Function types can be combined using parentheses: (Int) -> ((Int) -> Unit)
        - The arrow notation is right-associative, (Int) -> (Int) -> Unit is equivalent to the previous example,
        but not to ((Int) -> (Int)) -> Unit.

    You can also give a function type an alternative name by using a type alias:

    typealias ClickHandler = (Button, ClickEvent) -> Unit */

}

fun instantiatingFunctionType() {
    /*
    There are several ways to obtain an instance of a function type:

    1. Using a code block within a function literal, in one of the forms:
        - a lambda expression: { a, b -> a + b },
        - an anonymous function: fun(s: String): Int { return s.toIntOrNull() ?: 0 }
    Function literals with receiver can be used as values of function types with receiver.

    2. Using a callable reference to an existing declaration:
        - a top-level, local, member, or extension function: ::isOdd, String::toInt,
        - a top-level, member, or extension property: List<Int>::size,
        - a constructor: ::Regex
    These include bound callable references that point to a member of a particular instance: foo::toString.

    3. Using instances of a custom class that implements a function type as an interface: */

    class IntTransformer : (Int) -> Int {
        override operator fun invoke(x: Int): Int = x * x * x
    }

    val intFunction: (Int) -> Int = IntTransformer()
    println("IntTransformer invoke function calling ...")
    println(intFunction(5))

    // The compiler can infer the function types for variables if there is enough information:
    val plus = { i: Int -> i + 1 } // The inferred type is (Int) -> Int
    println(plus(2))

    println("\n--------------\n")

    /* Non-literal values of function types with and without receiver are interchangeable, so that the receiver can
    stand in for the first parameter, and vice versa. For instance, a value of type (A, B) -> C can be passed or
    assigned where a A.(B) -> C is expected and the other way around: */

    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
    val twoParameters: (String, Int) -> String = { message, times -> message.repeat(times) }

    fun runTransformation(func: (String, Int) -> String): String {
        return func("hello", 3)
    }

    println(runTransformation(repeatFun)) // A.(B) -> C
    println(runTransformation(twoParameters)) // (A, B) -> C

    // Note that a function type with no receiver is inferred by default, even if a variable is initialized with a
    // reference to an extension function. To alter that, specify the variable type explicitly.
}

fun invokingFunctionTypeInstance() {
    // A value of a function type can be invoked by using its invoke(...) operator: f.invoke(x) or just f(x).

    // If the value has a receiver type, the receiver object should be passed as the first argument.
    // Another way to invoke a value of a function type with receiver is to prepend it with the receiver object,
    // as if the value were an extension function: 1.foo(2),

    println("Invoking function type instance : ")

    val stringPlus: (String, String) -> String = String::plus
    val stringExtensionPlus: String.(String) -> String = String::plus

    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus("Say, ", "Hello!"))
    println(stringPlus.invoke("*****", "-----"))
    println("This is ".stringExtensionPlus("Extension function"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // extension-like call

    // TODO Inline functions
    // Sometimes it is beneficial to use inline functions, which provide flexible control flow,
    // for higher-order functions. (InlineFunctions.kt)
}

fun lambdaAndAnonymousFunctions() {
    // TODO Function Literals
    // Lambda expressions and anonymous functions are 'function literals', i.e. functions that are not declared,
    // but passed immediately as an expression. Consider the following example:

    // max(strings, { a, b -> a.length < b.length })

    // Function max is a higher-order function, it takes a function value as the second argument.
    // This second argument is an expression that is itself a function, i.e. a function literal,
    // which is equivalent to the following named function:

    fun compare(a: String, b: String): Boolean = a.length < b.length

    // TODO Lambda expression syntax
    // The full syntactic form of lambda expressions is as follows:

    println("Lambda expression syntax : ")

    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
    println("Sum with lambda : ${sum(3, 5)}")

    // A lambda expression is always surrounded by curly braces, parameter declarations in the full syntactic form go
    // inside curly braces and have optional type annotations, the body goes after an -> sign. If the inferred return
    // type of the lambda is not Unit, the last (or possibly single) expression inside the lambda body is treated as
    // the return value.

    // If we leave all the optional annotations out, what's left looks like this:
    val sub = { x: Int, y: Int -> x - y }
    println("Sub with lambda without optional annotations : ${sub(5, 3)}")
}


fun passingTrailingLambda() {
    // In Kotlin, there is a convention: if the last parameter of a function is a function,
    // then a lambda expression passed as the corresponding argument can be placed outside the parentheses:

    println("Passing trailing lambda : ")

    arithmeticOperations("Sum Suq", 1, 3) { a, b -> a * a + b * b }

    // Such syntax is also known as TODO trailing lambda.

    // If the lambda is the only argument to that call, the parentheses can be omitted entirely:
    run {
        println("Say Hello!")
    }
}

fun itImplicitNameOfSingleParameter() {
    // It's very common that a lambda expression has only one parameter.
    // If the compiler can figure the signature out itself, it is allowed not to declare the only parameter and omit ->.
    // The parameter will be implicitly declared under the name it:

    println("it implicit name of a single parameter : ")

    val ints = arrayListOf(1, 2, -1, -4, 5, -6)
    println("Elements of array are = $ints")
    println("Possitive numbers of array are : ${ints.filter { it > 0 }}") // this literal is of type '(it: Int) -> Boolean'
}

fun returningValueFromLambdaExpression() {
    // We can explicitly return a value from the lambda using the qualified return syntax. Otherwise, the value of
    // the last expression is implicitly returned.

    // TODO qualified return syntax
    // https://kotlinlang.org/docs/reference/returns.html#return-at-labels
    // With function literals, local functions and object expression, functions can be nested in Kotlin.
    // Qualified returns allow us to return from an outer function.
    // The most important use case is returning from a lambda expression. Recall that when we write this:

    /* Look at this, it doesn't continue because of return after (it == 3)
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // non-local return directly to the caller of foo()
        println(it)
    }
    println("this point is unreachable")
    */

    // Therefore, the two following snippets are equivalent:

    val ints = arrayListOf(1, 2, -1, -4, 5, -6)

    ints.filter {
        val hasPositive = it > 0
        hasPositive
    }

    ints.filter {
        return@filter it > 0
    }

    // This convention, along with passing a lambda expression outside parentheses, allows for LINQ-style code:

    // TODO LINQ-style code
    // https://docs.microsoft.com/en-us/previous-versions/dotnet/articles/bb308959(v=msdn.10)

    val cities = arrayListOf("Bursa", "İstanbul", "Çanakkale", "Kahramanmaraş", "Bolu")
    println(cities.filter { it.length > 5 }.sortedBy { it }.map { it.toUpperCase() })
}

fun underscoreForUnusedVariables() {
    // If the lambda parameter is unused, you can place an underscore instead of its name:

    val names = mapOf(
        "Girls" to arrayListOf("Ayşe", "Fatma", "Hayriye"),
        "Boys" to arrayListOf("Ali", "Veli", "Osman")
    )
    names.forEach { _, value -> println(value) }
}

fun anonymousFunctions() {

    // Repeat lambdas :
    // with type annotation in lambda expression
    val sum1 = { a: Int, b: Int -> a + b }

    // without type annotation in lambda expression
    val sum2: (Int, Int) -> Int = { a, b -> a + b }

    val result1 = sum1(2, 3)
    val result2 = sum2(3, 4)
    println("Lambda functions are calling :")
    println("The sum of two numbers is: $result1")
    println("The sum of two numbers is: $result2")

    println("\n--------------\n")

    // One thing missing from the lambda expression syntax presented above is the ability to specify the return type of
    // the function. In most cases, this is unnecessary because the return type can be inferred automatically.
    // However, if you do need to specify it explicitly, you can use an alternative syntax: an anonymous function.

    // An anonymous function looks very much like a regular function declaration, except that its name is omitted.
    // Its body can be either an expression or a block:

    // anonymous function with body as an expression
    val anonymous1 = fun(x: Int, y: Int): Int = x + y

    // anonymous function with body as a block
    val anonymous2 = fun(a: Int, b: Int): Int {
        return a * b
    }

    //invoking funtions
    val sum = anonymous1(3, 5)
    val mul = anonymous2(3, 5)

    println("Anonymous functions are calling :")
    println("The sum of two numbers is: $sum")
    println("The multiply of two numbers is: $mul")

    // The parameters and the return type are specified in the same way as for regular functions,
    // except that the parameter types can be omitted if they can be inferred from context:

    val ints = arrayListOf(1, 2, -1, -4, 5, -6)
    println(ints.filter(fun(item) = item > 0))

    /*
    1. The return type inference for anonymous functions works just like for normal functions: the return type is inferred
    automatically for anonymous functions with an expression body and has to be specified explicitly
    (or is assumed to be Unit) for anonymous functions with a block body.

    2. Note that anonymous function parameters are always passed inside the parentheses. The shorthand syntax allowing to
    leave the function outside the parentheses works only for lambda expressions.

    3. One other difference between lambda expressions and anonymous functions is the behavior of non-local returns.
    A return statement without a label always returns from the function declared with the fun keyword.
    This means that a return inside a lambda expression will return from the enclosing function,
    whereas a return inside an anonymous function will return from the anonymous function itself.

    Summary : https://stackoverflow.com/a/58007094/4133130
        - Anonymous functions allow you to specify return type, lambdas don't.
        - If you don't, return type inference works like for normal functions, and not like for lambdas.
        - The meaning of return is different (Look at 1.)
        - There is no implicit it parameter, or destructuring.
    */
}

fun closures() {
    // A lambda expression or anonymous function (as well as a local function and an object expression) can access
    // its closure, i.e. the variables declared in the outer scope. The variables captured in the closure
    // can be modified in the lambda:

    /* TODO Object expression
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) { /*...*/ }

        override fun mouseEntered(e: MouseEvent) { /*...*/ }
    }) */

    var sum = 0
    val ints = arrayListOf(1, 2, 1, 4, 5, 6)
    ints.filter { it > 0 }.forEach {
        sum += it
    }
    println(sum)
}

fun functionLiteralsWithReceiver() {
    /*
    Function types with receiver, such as A.(B) -> C, can be instantiated with a special form of function literals
    – function literals with receiver.

    As said above, Kotlin provides the ability to call an instance of a function type with receiver providing the
    receiver object.

    Inside the body of the function literal, the receiver object passed to a call becomes an implicit this,
    so that you can access the members of that receiver object without any additional qualifiers, or access the receiver
    object using a this expression. (Look at https://kotlinlang.org/docs/reference/this-expressions.html)

    This behavior is similar to extension functions, which also allow you to access the members of the receiver object
    inside the body of the function.

    Here is an example of a function literal with receiver along with its type, where plus is called on the receiver
    object:
    */

    val sumExtension: Int.(Int) -> Int = { other -> plus(other) }
    println("Function types with receiver : ")
    println(3.sumExtension(5))

    // The anonymous function syntax allows you to specify the receiver type of a function literal directly.
    // This can be useful if you need to declare a variable of a function type with receiver, and to use it later.

    val sumAnonymous = fun Int.(other: Int): Int = this + other
    println(3.sumAnonymous(5))

    // Lambda expressions can be used as function literals with receiver when the receiver type can be inferred from
    // context. One of the most important examples of their usage is type-safe builders:
    // (Look at stype-safe builders https://kotlinlang.org/docs/reference/type-safe-builders.html)
}