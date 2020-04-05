package collections

/**
 * https://kotlinlang.org/docs/reference/collections-overview.html
 * */

fun main() {
    // TODO Kotlin Collections Overview
    collectionsOverview()

    // TODO Collection
    collection()

    println("\n****************************\n")

    // TODO List
    list()

    println("\n****************************\n")

    // TODO Set
    set()

    println("\n****************************\n")

    // TODO Map
    map()
}

fun collectionsOverview() {
    // Collection types are: List, Set, Map

    // TODO Collection types
    // List, Set, Map can be read-only(immutable) or mutable (add,remove,update)

    /*
    Note that altering a mutable collection doesn't require it to be a var: write operations modify
    the same mutable collection object, so the reference doesn't change.
    Although, if you try to reassign a val collection, you'll get a compilation error.
    */

    val numbers = mutableListOf("one", "two", "three", "four")
    numbers.add("five") // you can add new element because list is mutable
    // numbers = mutableListOf("six", "seven") // you can't because numbers is val
}

fun collection() {
    /*
    Collection<T> is the root of the collection hierarchy. This interface represents the common behavior of
    a read-only collection: retrieving size, checking item membership, and so on. Collection inherits from
    the Iterable<T> interface that defines the operations for iterating elements. You can use Collection as a parameter
    of a function that applies to different collection types. For more specific cases, use the Collection's inheritors:
    List and Set.
    */

    fun printAll(strings: Collection<String>) {
        for (s in strings) print("$s ")
        println()
    }

    val stringList = listOf("one", "two", "one")
    printAll(stringList)

    val stringSet = setOf("one", "two", "three")
    printAll(stringSet)

    println("\n--------------\n")

    // MutableCollection is a Collection with write operations, such as add and remove.

    fun List<String>.getShortWordsTo(shortWords: MutableList<String>, maxLength: Int) {
        this.filterTo(shortWords) { it.length <= maxLength }
        // throwing away the articles
        val articles = setOf("a", "A", "an", "An", "the", "The")
        shortWords -= articles
    }

    val words = "A long time ago in a galaxy far far away".split(" ")
    val shortWords = mutableListOf<String>()
    words.getShortWordsTo(shortWords, 3)
    println(shortWords)
}

class Person(var name: String, var age: Int)

fun list() {
    /* List<T> stores elements in a specified order and provides indexed access to them. Indices start from zero –
    the index of the first element – and go to lastIndex which is the (list.size - 1). */

    val strNumbers = listOf("one", "two", "three", "four")
    println("Number of elements: ${strNumbers.size}")
    println("Third element: ${strNumbers.get(2)}")
    println("Fourth element: ${strNumbers[3]}")
    println("Index of element \"two\" ${strNumbers.indexOf("two")}")

    println("\n--------------\n")

    /* List elements (including nulls) can duplicate: a list can contain any number of equal objects or occurrences
    of a single object. */

    val bob = Person("Bob", 31)
    val people = listOf(Person("Adam", 20), bob, bob)
    val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
    println(people === people2)
    bob.age = 32
    println(people === people2)
    val people3 = people2
    println(people2 === people3)

    println("\n--------------\n")

    // MutableList is a List with list-specific write operations, for example, to add or remove an element at a specific position.
    val numbers = mutableListOf(1, 2, 3, 4)
    numbers.add(5)
    numbers.removeAt(1)
    numbers[0] = 0
    numbers.shuffle()
    println(numbers)

    /* As you see, in some aspects lists are very similar to arrays. However, there is one important difference:
    an array's size is defined upon initialization and is never changed; in turn, a list doesn't have a predefined size;
    a list's size can be changed as a result of write operations: adding, updating, or removing elements.

    In Kotlin, the default implementation of List is ArrayList which you can think of as a resizable array. */
}

fun set() {
    /* Set<T> stores unique elements; their order is generally undefined. null elements are unique as well:
    a Set can contain only one null. */

    val nullSet = setOf(null, "Ayşe", null, null, null, "Ali") // null, Ayşe, Ali
    println("Number of elements: ${nullSet.size}")

    println("\n--------------\n")

    val numbersSet = setOf(1, 2, 3, 4)
    println("Number of elements: ${numbersSet.size}")
    if (numbersSet.contains(1)) println("1 is in the set")

    val numbersBackwardsSet = setOf(4, 3, 2, 1)

    // Two sets are equal if they have the same size, and for each element of a set there is an equal element in the other set.
    println("The sets are equal: ${numbersSet == numbersBackwardsSet}")

    println("\n--------------\n")

    /* MutableSet is a Set with write operations from MutableCollection.

    The default implementation of Set – LinkedHashSet – preserves the order of elements insertion. Hence,
    the functions that rely on the order, such as first() or last(), return predictable results on such sets. */

    val numbers = setOf(1, 2, 3, 4)  // LinkedHashSet is the default implementation
    val numbersBackwards = setOf(4, 3, 2, 1)

    println(numbers.first() == numbersBackwards.first())
    println(numbers.first() == numbersBackwards.last())

    println("\n--------------\n")

    /* An alternative implementation – HashSet – says nothing about the elements order, so calling such functions on
    it returns unpredictable results. However, HashSet requires less memory to store the same number of elements. */

    val numbersHashSet = hashSetOf(1, 2, 3, 4)  // LinkedHashSet is the default implementation
    val numbersBackwardsHashSet = hashSetOf(4, 3, 2, 1)

    println(numbersHashSet) // Look at result
    println(numbersBackwardsHashSet) // Look at result

    println(numbersHashSet.first() == numbersBackwardsHashSet.first())
    println(numbersHashSet.first() == numbersBackwardsHashSet.last())
}

fun map() {
    /* Map<K, V> is not an inheritor of the Collection interface; however, it's a Kotlin collection type as well.
    A Map stores key-value pairs (or entries); keys are unique, but different keys can be paired with equal values.
    The Map interface provides specific functions, such as access to value by key, searching keys and values, and so on. */

    // Look at picture at https://kotlinlang.org/docs/reference/collections-overview.html#collection-types

    val numbers = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)

    println("All keys: ${numbers.keys}")
    println("All values: ${numbers.values}")

    if ("key2" in numbers) println("Value by key \"key2\": ${numbers["key2"]}")
    if (numbers.containsKey("key3")) println("Value by key \"key3\": ${numbers["key3"]}") // same as previous

    if (1 in numbers.values) println("The value 1 is in the map")
    if (numbers.containsValue(2)) println("The value 2 is in the map") // same as previous

    println("\n--------------\n")

    // Two maps containing the equal pairs are equal regardless of the pair order.

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    val anotherMap = mapOf("key2" to 2, "key1" to 1, "key4" to 1, "key3" to 3)

    println("The maps are equal: ${numbersMap == anotherMap}")

    println("\n--------------\n")

    /* MutableMap is a Map with map write operations, for example, you can add a new key-value pair or update the value
    associated with the given key. */

    // this's mutable (mutableMapOf)
    val mutableNumbersMap = mutableMapOf("one" to 1, "two" to 2)
    mutableNumbersMap.put("three", 3)
    mutableNumbersMap["one"] = 11

    println(mutableNumbersMap)

    val immutableNumbersMap = mapOf("one" to 1, "two" to 2)
    // immutableNumbersMap.put("three", 3) you can't
    // immutableNumbersMap["one"] = 11 you can't

    println(immutableNumbersMap)

    /* The default implementation of Map – LinkedHashMap – preserves the order of elements insertion when iterating
    the map. In turn, an alternative implementation – HashMap – says nothing about the elements order. */
}