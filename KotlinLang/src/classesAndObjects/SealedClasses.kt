package classesAndObjects

/**
 * Sealed classes are used for representing restricted class hierarchies, when a value can have one of the types
 * from a limited set, but cannot have any other type. They are, in a sense, an extension of enum classes:
 * the set of values for an enum type is also restricted, but each enum constant exists only as a single instance,
 * whereas a subclass of a sealed class can have multiple instances which can contain state.
 *
 * https://kotlinlang.org/docs/reference/sealed-classes.html
 * */

sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr() // It's final

fun main() {

    // 1. A sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members.
    // 2. Sealed classes are not allowed to have non-private constructors (their constructors are private by default).
    // val expr = Expr() Sealed types cannot be instantiated

    // 3. Note that classes which extend subclasses of a sealed class (indirect inheritors) can be placed anywhere,
    // not necessarily in the same file.
    val constOne = Const(10.2)
    println("Receive Const : ${receive(constOne)}")

    val constTwo = Const(0.8)
    val sum = Sum(constOne, constTwo)
    println("Receive Sum : ${receive(sum)}")

}

fun receive(expr: Expr): Double = when(expr) {
    // The key benefit of using sealed classes comes into play when you use them in a when expression.
    // If it's possible to verify that the statement covers all cases, you don't need to add an else clause to the
    // statement. However, this works only if you use when as an expression (using the result) and not as a statement.
    is Const -> expr.number
    is Sum -> receive(expr.e1) + receive(expr.e2)
    NotANumber -> Double.NaN
    // the `else` clause is not required because we've covered all the cases
}