package classesAndObjects

/**
 * https://kotlinlang.org/docs/reference/type-aliases.html
 *
 **/

typealias MyInt = Int

typealias intToStringList = HashMap<Int, List<String>>

typealias condition = (Int) -> Boolean

typealias sayHello = (HashMap<Int, List<String>>) -> Unit

fun main() {

    // Type aliases provide alternative names for existing types.
    // If the type name is too long you can introduce a different shorter name and use the new one instead.

    // It's useful to shorten long generic types. For instance, it's often tempting to shrink collection types,
    // look at intToStringList alias.

    val myInt: MyInt = 5
    println("My value of alias property is $myInt")

    println("\n--------------\n")

    val cities = intToStringList()
    cities[1] = arrayListOf("Adana", "Ankara")
    cities[2] = arrayListOf("Bursa")
    cities.forEach {
        val message = if (it.value.isNotEmpty() && it.value.size > 1) {
            "cities are"
        } else {
            "city is"
        }
        println("${it.key}. $message ${it.value}")
    }

    println("\n--------------\n")

    // You can provide different aliases for function types, look ad condition alias
    val filter: condition = { it % 2 == 0 }
    println("My even numbers = " + listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).filter(filter))

    println("\n--------------\n")

    // 1. define type alias, look at sayHello alias
    // 2. define and perform action, look at sayHelloAllCities
    // 3. call defined action sayHelloAllCities(cities)

    val sayHelloAllCities: sayHello = { it ->
        it.forEach { println("Hi, ${it.value}") }
    }
    sayHelloAllCities(cities)


    // You can have new names for inner and nested classes, look at AInner and BInner

    // Finally, Type aliases do not introduce new types. They are equivalent to the corresponding underlying types.

}
class TypeAliasesA {
    inner class Inner
}
class TypeAliasesB {
    inner class Inner
}

typealias AInner = TypeAliasesA.Inner
typealias BInner = TypeAliasesB.Inner