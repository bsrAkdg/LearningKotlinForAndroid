package GettingStarted

/**
 *
 * https://kotlinlang.org/docs/reference/basic-syntax.html
 */
fun main() {

    //TODO Defining functions
    println("sum method return value : " + sum(2, 3))
    println("multiply method return value : " + multiply(2, 3))
    division(3, 4)
    sub(5, 3)

    //TODO Defining variables
    //val : Read-only local variables are defined using the keyword val. They can be assigned a value only once.
    val a: Int = 2 // immediate assignment
    val b = 3 // type of b is inferred
    println("sum : ${a + b}")
    val sum: Int // type required when no initializer is provided
    sum = 5 // deferred assignment
    println(sum)

    // var : Variables that can be reassigned use the var keyword:
    var x = 5
    x += 3
    println("x value is : $x")

    //TODO Using string templates
    var y = 1
    val echo = "y is $y"
    y = 2
    println("${echo.replace("is", "was")}, but now is $y")

    //TODO Using conditional expressions
    println("great value for $x and $y is : ${maxOf(x, y)}")
    //or using "if" as an expression:
    println("small value for $x and $y is : ${minOf(x, y)}")


    //TODO Using nullable values and checking for "null"
    //A reference must be explicitly marked as nullable when null value is possible.
    println(isContainsOr("You can do or fix"))
    println(isContainsOr("You can do"))

    //TODO Using type checks and automatic casts
    /* The is operator checks if an expression is an instance of a type.
    If an immutable local variable or property is checked for a specific type, there's no need to cast it explicitly: */
    val myMessage = "I love to life so much"
    println("$myMessage is ${getStringLength(myMessage)} length")
    println("$myMessage is ${getStringLength("")} length")

    //TODO Using a for loop
    val myFruits = listOf("banana", "strawberry", "orange")
    val fruit = "apple"
    println("Is there an $fruit in the my fruits ? - ${checkMyFruits(myFruits, fruit)}")

    //TODO Using a while loop
    val myNewFruits = listOf("banana", "strawberry", "orange", "banana", "apple", "banana", "kiwi", "apple")
    val newFruit = "banana"
    println(
        "How many $newFruit s are in the my new fruits ? - There is/are ${sumSameFruits(myNewFruits, newFruit)} " +
                "$newFruit s."
    )

    //TODO Using when expression
    println("Center of city : ${getNameOfImportantPlace(16)}")
    println("Center of city : ${getNameOfImportantPlace(17)}")

    //TODO Using ranges
    // Check if a number is within a range using "in" operator:
    val number = 23
    val maxNumber = 100
    println("$number, between 0 and $maxNumber? - ${isNumberInRange(number, maxNumber)}")

    // Check if a number is out of range:
    println("$number, except 0 and $maxNumber? - ${isNotNumberInRange(number, maxNumber)}")

    // Iterating over a range or over a progression:
    toWayShow("KOTLIN")

    //TODO Using collections
    //Checking if a collection contains an object using "in" operator:
    val obj1 = "kiwi"
    val obj2 = "onion"
    println("$obj1 ${checkKindOf(obj1)}, $obj2 ${checkKindOf(obj2)}")

    //Using lambda expressions to filter and map collections:
    println("Fruits starting with 'a' :")
    showOnlyStartWithA()
}

//Function having two Int parameters with Int return type:
fun sum(a: Int, b: Int): Int {
    return a + b
}

//Function with an expression body and inferred return type:
fun multiply(a: Int, b: Int) = a * b

//Function returning no meaningful value: This type corresponds to the void type in Java.
fun division(a: Int, b: Int): Unit {
    println("division method inferred value : " + sum(a, b))
}

//Unit return type can be omitted:
fun sub(a: Int, b: Int) {
    println("sub method inferred value ${a + b}")
}

fun maxOf(a: Int, b: Int): Int {
    return if (a > b) {
        a
    } else {
        b
    }
}

fun minOf(a: Int, b: Int) = if (a < b) a else b

fun isContainsOr(str: String): String? = if (str.contains("or")) "$str contains 'or'" else null

fun getStringLength(obj: Any): Int? = if (obj is String) obj.length else null // is not = !is

fun checkMyFruits(myFruits: List<String>, checkFruit: String): String {
    var isExist = false
    for (fruit in myFruits) { // you should indices for index (index in myFruits.indices)
        if (fruit.equals(checkFruit)) {
            isExist = true
            break
        }
    }
    return if (isExist) "Yes, there is :) " else "No, there isn't :("
}

fun sumSameFruits(myFruits: List<String>, checkFruit: String): Int {
    var total = 0
    var index = 0
    while (index < myFruits.size) {
        if (myFruits[index].equals(checkFruit)) {
            total++
        }
        index++
    }
    return total
}

fun getNameOfImportantPlace(obj: Any): String? {
    return when (obj) {
        "Bursa", 16 -> "Ulucami"
        "İstanbul", 34 -> "Kız kulesi"
        "Ankara" -> "Anıtkabir"
        else -> null
    }
}

fun isNumberInRange(number: Int, maxNumber: Int): Boolean {
    return number in 0..maxNumber + 1
}

fun isNotNumberInRange(number: Int, maxNumber: Int): Boolean {
    return number !in 0..maxNumber + 1
    /* or if do you check list size
        if (list.size !in list.indices) {
            println("list size is out of valid list indices range, too")
        }
     */
}

fun toWayShow(str: String) {
    for (char in str) {
        println(char)
    }

    println("Reverse : ")

    for (index in str.length - 1 downTo 0) { // (index in str.length-1 downTo 0 step 2)
        println(str[index])
    }
}

fun checkKindOf(str: String): String {

    val fruits = listOf("banana", "apple", "strawberry", "kiwi", "orange")
    val vegetables = listOf("tomato", "onion", "cucumber", "patato")

    return when (str) {
        in fruits -> "is a fruit"
        in vegetables -> "is a vegetable"
        else -> "unknown"
    }
}

fun showOnlyStartWithA() {
    val fruits = listOf("banana", "apple", "strawberry", "apricots", "orange", "melon", "avocado", "acerola")
    fruits.filter { it.startsWith("a") }
        .sortedBy { it }
        .map { it.toUpperCase() }
        .forEach { println(it) }
}