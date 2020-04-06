package collections

import java.util.*

/**
 * https://kotlinlang.org/docs/reference/constructing-collections.html
 * */

fun main() {
    // TODO Constructing from elements
    constructingFromElements()

    println("\n****************************\n")

    // TODO Empty collections
    emptyCollections()

    // TODO Initializer functions for lists
    initializerFunctionsForLists()

    // TODO Concrete type constructors
    concreteTypeConstructors()

    println("\n****************************\n")

    // TODO Copying
    copying()

    println("\n****************************\n")

    // TODO Invoking functions on other collections
    invokingFunctionsOnOtherCollections()
}

fun constructingFromElements() {
    /* The most common way to create a collection is with the standard library functions listOf<T>(), setOf<T>(),
    mutableListOf<T>(), mutableSetOf<T>(). If you provide a comma-separated list of collection elements as arguments,
    the compiler detects the element type automatically. When creating empty collections, specify the type explicitly. */


    // List
    val numberList = listOf(1, 2, 3, 4)
    val emptyNumberList = mutableListOf<Int>()

    // Set
    val numberSet = setOf(1, 2, 3, 4)
    val emptyNumberSet = mutableSetOf<Int>()

    // Map
    val numberMap = mapOf(1 to "One", 2 to "Two", 3 to "Three") // Not recommended !!!!!!!
    val emptyNumberMap = mutableMapOf<Int, String>()

    /* Note that the to notation creates a short-living Pair object, so it's recommended that you use it only
    if performance isn't critical. To avoid excessive memory usage, use alternative ways. For example,
    you can create a mutable map and populate it using the write operations. The apply() function can help to keep
    the initialization fluent here. */

    val numberMapRecommended = mutableMapOf<Int, String>().apply {
        this[1] = "One"; this[2] = "Two"; this[3] = "Three"
    }

    println("Recommended map initialization example is $numberMapRecommended")
}

fun emptyCollections() {
    /* There are also functions for creating collections without any elements: emptyList(), emptySet(), and emptyMap().
    When creating empty collections, you should specify the type of elements that the collection will hold. */

    // List
    val numberList = emptyList<Int>()

    // Set
    val numberSet = emptySet<Int>()

    // Map
    val numberMap = emptyMap<Int, String>()

}

fun initializerFunctionsForLists() {
    /* For lists, there is a constructor that takes the list size and the initializer function that defines the element
    value based on its index. */

    val immutableEvenNumbers = List(10) { it * 2 } // this is immutable
    // evenNumbers[0] = 5 you can't
    println(immutableEvenNumbers)

    val mutableEvenNumbers = MutableList(10) { it * 2 } // this is mutable
    println(mutableEvenNumbers)

    mutableEvenNumbers[0] = 4
    println(mutableEvenNumbers)
}

fun concreteTypeConstructors() {
    /* To create a concrete type collection, such as an ArrayList or LinkedList, you can use the available constructors
    for these types. Similar constructors are available for implementations of Set and Map. */

    val linkedList = LinkedList<String>(listOf("one", "two", "three")) // Collection<? extends E> collection

    // MAP is not extended from COLLECTION
    // val linkedListMap = LinkedList<String>(mapOf(1 to "One", 2 to "Two", 3 to "Three"))

    val presizedSet = HashSet<Int>(32)
}

fun copying() {
    /* To create a collection with the same elements as an existing collection, you can use copying operations.
    Collection copying operations from the standard library create shallow copy collections with references to
    the same elements. Thus, a change made to a collection element reflects in all its copies.

    Collection copying functions, such as toList(), toMutableList(), toSet() and others, create a snapshot of
    a collection at a specific moment. Their result is a new collection of the same elements. If you add or remove
    elements from the original collection, this won't affect the copies. Copies may be changed independently of
    the source as well. */

    val sourceList = mutableListOf(1, 2, 3)
    println("SourceList created: ${sourceList.size}")

    val copyList = sourceList.toMutableList()
    println("copyList created from sourceList with toMutableList: ${copyList.size}")

    val readOnlyCopyList = sourceList.toList()
    println("readOnlyCopyList created from sourceList with toList: ${readOnlyCopyList.size}")

    sourceList.add(4)
    println("SourceList new item added SourceList size: ${sourceList.size}")
    println("copyList size: ${copyList.size}")

    //readOnlyCopyList.add(4) // compilation error
    println("readOnlyCopyList size: ${readOnlyCopyList.size}")

    println("\n--------------\n")

    /* These functions can also be used for converting collections to other types, for example, build a set from a list
    or vice versa. */

    println("sourceList : $sourceList")
    val copySet = sourceList.toMutableSet()
    println("copySet created from sourceList with toMutableSet: $copySet")
    copySet.add(3)
    copySet.add(4)
    println("3 and 4 added updated copySet : $copySet")
    copySet.add(5)
    println("5 added updated copySet : $copySet")
    println("sourceList : $sourceList")
    println(
        "copySet is mutable and if sourceList changes, referenceList doesn't change, " +
                "because copySet created with sourceList.toMutableSet"
    )

    println("\n--------------\n")

    /* Alternatively, you can create new references to the same collection instance. New references are created
    when you initialize a collection variable with an existing collection. So, when the collection instance is altered
    through a reference, the changes are reflected in all its references. */

    val referenceList = sourceList
    println("sourceList : $sourceList")
    println("referenceList created from sourceList with assign: $referenceList")
    referenceList.add(5)
    println("referenceList new item added referenceList : $referenceList")
    println("sourceList : $sourceList")
    println(
        "referenceList is mutable and if sourceList changes, referenceList changes, " +
                "because referenceList of reference are the same place with sourceList"
    )

    println("\n--------------\n")

    /* Collection initialization can be used for restricting mutability. For example, if you create a List reference to
    a MutableList, the compiler will produce errors if you try to modify the collection through this reference. */

    println("sourceList : $sourceList")
    val immutableReferenceList: List<Int> = sourceList // read-only
    println("immutableReferenceList created from sourceList with assign: $immutableReferenceList")
    // immutableReferenceList.add(4)            //compilation error
    sourceList.add(6)
    println("SourceList new item added SourceList : $sourceList")
    println("immutableReferenceList : $immutableReferenceList") // // shows the current state of sourceList
    println(
        "immutableReferenceList is read-only but if sourceList changes, immutableReferenceList changes, " +
                "because immutableReferenceList of reference are the same place with sourceList"
    )
}

fun invokingFunctionsOnOtherCollections() {
    /* Collections can be created in result of various operations on other collections. For example, filtering
    a list creates a new list of elements that match the filter: */

    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println(numbers)
    println(evenNumbers)

    println("\n--------------\n")

    // Mapping produces a list of a transformation results:
    val numbersSet = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(numbersSet.map { it * 3 })
    println(numbersSet.mapIndexed { idx, value -> value * idx })

    println("\n--------------\n")

    // Association produces maps:
    val numbersList = listOf("one", "two", "three", "four")
    println(numbersList.associateWith { it.toUpperCase() })
}