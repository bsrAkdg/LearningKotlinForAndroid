package collections

/**
 * https://kotlinlang.org/docs/reference/collection-operations.html
 * */

fun main() {

    // TODO Collection Operations Overview
    collectionOperationsOverviewOverview()

    println("\n****************************\n")

    // TODO Write operations
    writeOperations()
}

fun collectionOperationsOverviewOverview() {
    /*

    The Kotlin standard library offers a broad variety of functions for performing operations on collections.
    This includes simple operations, such as getting or adding elements, as well as more complex ones including search,
    sorting, filtering, transformations, and so on.

    TODO Extension and member functions
    Collection operations are declared in the standard library in two ways: member functions of collection interfaces
    and extension functions.

    Member functions define operations that are essential for a collection type. For example, Collection contains the
    function isEmpty() for checking its emptiness; List contains get() for index access to elements, and so on.

    When you create your own implementations of collection interfaces, you must implement their member functions.
     To make the creation of new implementations easier, use the skeletal implementations of collection interfaces from
     the standard library: AbstractCollection, AbstractList, AbstractSet, AbstractMap, and their mutable counterparts.

    Other collection operations are declared as extension functions. These are filtering, transformation, ordering, and
    other collection processing functions.

    TODO Common operations
    Common operations are available for both read-only and mutable collections. Common operations fall into these groups:

    - Transformations
    - Filtering
    - plus and minus operators
    - Grouping
    - Retrieving collection parts
    - Retrieving single elements
    - Ordering
    - Aggregate operations

    Operations described on these pages return their results without affecting the original collection.
    For example, a filtering operation produces a new collection that contains all the elements matching the filtering
    predicate. Results of such operations should be either stored in variables, or used in some other way,
    for example, passed in other functions. */

    val firstNumbers = listOf("one", "two", "three", "four")
    firstNumbers.filter { it.length > 3 }  // nothing happens with `numbers`, result is lost
    println("numbers are still $firstNumbers")
    val longerThan3 = firstNumbers.filter { it.length > 3 } // result is stored in `longerThan3`
    println("numbers longer than 3 chars are $longerThan3")

    println("\n--------------\n")

    /* For certain collection operations, there is an option to specify the destination object. Destination is a mutable
    collection to which the function appends its resulting items instead of returning them in a new object.
    For performing operations with destinations, there are separate functions with the To postfix in their names,
    for example, filterTo() instead of filter() or associateTo() instead of associate(). These functions take the
    destination collection as an additional parameter. */

    val numbers = listOf("one", "two", "three", "four")
    val filterResults = mutableListOf<String>()  //destination object
    numbers.filterTo(filterResults) { it.length > 3 }
    numbers.filterIndexedTo(filterResults) { index, _ -> index == 0 }
    println(filterResults) // contains results of both operations

    println("\n--------------\n")

    // For convenience, these functions return the destination collection back, so you can create it right in the
    // corresponding argument of the function call:

    // filter numbers right into a new hash set,
    // thus eliminating duplicates in the result

    val numbersUniq = listOf("one", "two", "three", "four", "one", "two", "three", "four")
    val result = numbersUniq.mapTo(HashSet()) { it }
    println("distinct item lengths are $result")

    /* Functions with destination are available for filtering, association, grouping, flattening, and other operations.
    For the complete list of destination operations see the Kotlin collections reference. */
}

fun writeOperations() {
    /* For mutable collections, there are also write operations that change the collection state.
    Such operations include adding, removing, and updating elements. Write operations are listed in the Write operations
    and corresponding sections of List specific operations and Map specific operations.

    - CollectionWriteOperations.kt =  Write operations
    - ListSpecificOperations.kt = List specific operation
    - MapSpecificOperations.kt =  Map specific operations

    For certain operations, there are pairs of functions for performing the same operation: one applies the operation
    in-place and the other returns the result as a separate collection. For example, sort() sorts a mutable collection
    in-place, so its state changes; sorted() creates a new collection that contains the same elements in the sorted order. */

    val numbers = mutableListOf("one", "two", "three", "four")
    val sortedNumbers = numbers.sorted()
    println(numbers == sortedNumbers)  // false
    numbers.sort()
    println(numbers == sortedNumbers)  // true
}