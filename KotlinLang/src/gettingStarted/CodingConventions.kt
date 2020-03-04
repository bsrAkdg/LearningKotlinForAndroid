// TODO File annotations
// File annotations are placed after the file comment (if any), before the package statement, and are separated from
// package with a blank line (to emphasize the fact that they target the file and not the package).
/** License, copyright and whatever */
@file:JvmName("FooBar")

package gettingStarted

import javax.lang.model.element.Element

/**
 * This page contains the current coding style for the Kotlin language.
 *
 * https://kotlinlang.org/docs/reference/coding-conventions.html
 * */

// TODO Property names
// Names of constants (properties marked with const, or top-level or object val properties with no custom 'get'
// function that hold deeply immutable data) should use uppercase underscore-separated names:
const val MAX_COUNT = 8
val USER_NAME_FIELD = "UserName"

// Names of top-level or object properties which hold objects with behavior or mutable data should use regular camel-hump names:
val mutableCollection: MutableSet<String> = HashSet()

// For enum constants, it's OK to use either uppercase underscore-separated names or regular camel-humps names starting
// with an uppercase letter, depending on the usage.
enum class Color {
    RED, GREEN,
    ONE_STEP_COLOR, TWO_STEP_COLOR
}

// TODO Function names
// Names of functions, properties and local variables start with a lower case letter and use camel humps and no underscores:
fun processDeclarations(): Int {
    return 4
}

var declarationCount = processDeclarations()

// TODO Names for backing properties
// If a class has two properties which are conceptually the same but one is part of a public API and another is
// an implementation detail, use an underscore as the prefix for the name of the private property:
class ElementModel {
    private val _elementList = mutableListOf<Element>()

    val elementList: List<Element>
        get() = _elementList
}

// TODO Colon
/* Put a space before : in the following cases:
   - when it's used to separate a type and a supertype;
   - when delegating to a superclass constructor or a different constructor of the same class;
   - after the 'object' keyword.
   Don't put a space before : when it separates a declaration and its type.
*/

// TODO Class header formatting
// Classes with a few primary constructor parameters can be written in a single line:
open class Human(id: Int, name: String)

interface KotlinMaker

// For multiple interfaces, the superclass constructor call should be located first and then
// each interface should be located in a different line and For multiple interfaces, the superclass constructor call
// should be located first and then each interface should be located in a different line:
class Person(
    id: Int,
    name: String,
    job: String
) : Human(id, name),
    KotlinMaker

// TODO Modifiers
/* If a declaration has multiple modifiers, always put them in the following order:
    public / protected / private / internal
    expect / actual
    final / open / abstract / sealed / const
    external
    override
    lateinit
    tailrec
    vararg
    suspend
    inner
    enum / annotation
    companion
    inline
    infix
    operator
    data */

// TODO Annotation formatting
// Annotations are typically placed on separate lines, before the declaration to which they are attached, and with the same indentation:
@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude

// TODO Function formatting
// If the function signature doesn't fit on a single line, use the following syntax:
fun longMethodName(
    argument: String = "argument",
    argument2: String = "argument2"
): String {
    // body
    return ""
}

// Prefer using an expression body for functions with the body consisting of a single expression.
fun foo(): Int {     // bad
    return 1
}

fun fooOther() = 1      // good

// TODO Property formatting
// For very simple read-only properties, consider one-line formatting:
val isEmpty: Boolean get() = true

// For more complex properties, always put get and set keywords on separate lines:
var foo: String
    get() = "Foo"
    set(value) {}

// For properties with an initializer, if the initializer is long, add a line break after the equals sign and indent the initializer by four spaces:
private val defaultCharset: String? =
    EncodingRegistry().getDefaultCharsetForPropertiesFiles("fileName")

class EncodingRegistry {

    fun getDefaultCharsetForPropertiesFiles(fileName: String): String {
        return fileName
    }
}


