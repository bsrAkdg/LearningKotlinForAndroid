package gettingStarted

import java.io.File
import java.util.*

/**
 * A collection of random and frequently used idioms in Kotlin.
 *
 * https://kotlinlang.org/docs/reference/idioms.html
 */

fun main() {

    //TODO Filtering a list
    println(getFilteredFruitList())

    //TODO Traversing a map/list of pairs
    println(getChildNamesStartWithA())

    //TODO Using ranges
    showRanges()

    //TODO Lazy property
    showLazyProperty()

    //TODO Extension Functions
    val greatIdea = "Dünyada!her!şey!için,!medeniyet!için,!hayat!için,!başarı!için,!en!hakiki!mürşit!bilimdir,!fendir."
    println(greatIdea.changeSplitChar())

    //TODO Creating a singleton
    println("${User.getName()} is ${User.getDate()} years old")

    //TODO If not null shorthand
    showNotNullShorthand()

    //TODO Map nullable value if not null '?:'
    getColorId("Red")
    getColorId(null)

    //TODO Calling multiple methods on an object instance (with)
    showWithUsages()

    //TODO Swapping two variables
    swapTwoVariables()
}

fun getFilteredFruitList(): List<String> {
    val fruits = listOf("banana", "apple", "strawberry", "apricots", "orange", "melon", "avocado", "acerola")
    return fruits.filter { x -> x.startsWith("a") }
}

fun getChildNamesStartWithA(): ArrayList<String> {

    val old = arrayListOf("Halim", "Zekiye")
    val adult = arrayListOf("Cumhur", "Nevin", "Büke")
    val child = arrayListOf("Okan", "Ayşe", "Fatma", "Ali", "Emre", "Hülya")

    val names = mapOf("old" to old, "adult" to adult, "child" to child)
    // accessing a map 'map["key"]'

    val resultNames = arrayListOf<String>()

    //Get first item of a possibly empty collection
    println("resultNames : ${resultNames.firstOrNull() ?: "Empty"}")

    for ((key, values) in names) {
        resultNames.addAll(values
            .map { it.toUpperCase() }
            .filter { x -> key == "child" && x.startsWith("A") })
    }

    println("resultNames : ${resultNames.firstOrNull() ?: ""}")

    return resultNames
}

fun showRanges() {
    // for (i in 1..100) { ... }  // closed range: includes 100
    // for (i in 1 until 100) { ... } // half-open range: does not include 100
    // for (x in 2..10 step 2) { ... }
    // for (x in 10 downTo 1) { ... }
    // if (x in 1..10) { ... }

    println("Up ranges with step not include end : ")

    for (index in 5 until 100 step 20) { //does not include 100
        println(index)
    }

    println("Down ranges with step : ")

    for (index in 100 downTo 5 step 20) {
        println(index)
    }
}

fun showLazyProperty() {
    // lazy properties always create at runtime
    val lazy: Int by lazy { 10 }

    println("Lazy property : ")
    // if you put a break point on the top line and look at the value of lazy you can this message "Lazy value not initialized yet."

    println("Multiply lazy value : ${lazy * 10}")
}

fun String.changeSplitChar(): String {
    return this.replace("!", " ").toUpperCase()
}

object User {
    // Singleton is an object-oriented pattern where a class can have only one instance (object).
    private val name = "Efe"
    private val birthDate = 2014

    fun getName(): String {
        return name
    }

    fun getDate(): Int {
        return Calendar.getInstance().get(Calendar.YEAR) - birthDate
    }
}

fun showNotNullShorthand() {
    val files = File("Test").listFiles()

    println("Test files size : ${if (files?.size != null) files.size.toString() else "It is empty"}")
    // or If not null and else shorthand
    println("Test files size : ${files?.size ?: "It is empty"}")
    // or
    // println("Test files size : ${files?.size ?: throw NullPointerException("File not found")}")

    // Execute if not null
    files?.let {
        println("If Test is existing, it's size is : ${files.size}")
    }
}

fun getColorId(color: String?) {
    var result = color?.let { transform(it) } ?: "Unknow"
    println("Color id is : $result with 'if'")

    //This can be effectively combined with other idioms, leading to shorter code.
    result = color?.let { otherTransform(it) } ?: "Unknow"
    println("Color id is : $result with 'when'")
}

fun transform(color: String): String {
    //'if' expression
    return if (color == "Red") {
        "0x00000"
    } else if (color == "Yellow") {
        "0x00001"
    } else if (color == "Pink") {
        "0x00002"
    } else {
        ""
    }
}

fun otherTransform(color: String): String = when (color) {
    "Red" -> "0x00000"
    "Yellow" -> "0x00001"
    "Pink" -> "0x00002"
    else -> ""
}

fun showWithUsages() {
    //create class intance
    val turtle = Turtle()
    with(turtle) {
        penDown()
        for (i in 1..4) {
            forward(100.0)
            turn(90.0)
        }
        penUp()
    }
}

class Turtle {

    fun penDown() {
        println("penDown")
    }

    fun penUp() {
        println("penUp")
    }

    fun turn(degrees: Double) {
        println("turn $degrees")
    }

    fun forward(pixels: Double) {
        println("forward $pixels")
    }
}

fun swapTwoVariables() {
    var a = 1
    var b = 2
    println("a old value : $a")
    println("b old value : $b")
    a = b.also { b = a * 5 }
    println("a new value : $a")
    println("b new value : $b")
}