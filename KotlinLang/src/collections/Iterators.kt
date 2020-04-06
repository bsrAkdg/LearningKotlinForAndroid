package collections


/**
 * https://kotlinlang.org/docs/reference/iterators.html
 * */

fun main() {

    // TODO Overview
    iteratorsOverview()

    println("\n****************************\n")

    // TODO List iterators
    listIterators()

    println("\n****************************\n")

    // TODO Mutable iterators
    mutableIterators()
}

fun iteratorsOverview() {
    /* For traversing collection elements, the Kotlin standard library supports the commonly used mechanism of iterators
    – objects that provide access to the elements sequentially without exposing the underlying structure of the
    collection. Iterators are useful when you need to process all the elements of a collection one-by-one, for example,
    print values or make similar updates to them.

    Iterators can be obtained for inheritors of the Iterable<T> interface, including Set and List, by calling the
    iterator() function. Once you obtain an iterator, it points to the first element of a collection; calling the next()
    function returns this element and moves the iterator position to the following element if it exists. Once the
    iterator passes through the last element, it can no longer be used for retrieving elements; neither can it be reset
    to any previous position. To iterate through the collection again, create a new iterator. */

    val names = listOf("Ayşe", "Fatma", "Hayriye", "Haydi", "Çifte", "Telliye")
    val namesIterator = names.iterator()
    while (namesIterator.hasNext()) {
        println(namesIterator.next())
    }

    println("\n--------------\n")

    /* Another way to go through an Iterable collection is the well-known for loop. When using for on a collection,
    you obtain the iterator implicitly. So, the following code is equivalent to the example above: */

    println("in loop automatically iterate a collection")
    for (name in names) {
        println(name)
    }

    println("\n--------------\n")

    /* Finally, there is a useful forEach() function that lets you automatically iterate a collection and execute
    the given code for each element. So, the same example would look like this: */

    println("forEach automatically iterate a collection")
    names.forEach { println(it) }
}

fun listIterators() {
    /* For lists, there is a special iterator implementation: ListIterator. It supports iterating lists in both
    directions: forwards and backwards. Backward iteration is implemented by the functions hasPrevious() and previous().
    Additionally, the ListIterator provides information about the element indices with the functions nextIndex()
    and previousIndex(). */

    val names = listOf("Ayşe", "Fatma", "Hayriye", "Haydi", "Çifte", "Telliye")
    val namesIterator = names.listIterator()
    println("Iterating forwards:")
    while (namesIterator.hasNext()) {
        println("${namesIterator.nextIndex()}. item is : ${namesIterator.next()}")
    }
    println("Iterating backwards:")
    while (namesIterator.hasPrevious()) {
        println("${namesIterator.previousIndex()}. item is : ${namesIterator.previous()}")
    }

    // Having the ability to iterate in both directions, means the ListIterator can still be used after it reaches the last element.
}

fun mutableIterators() {
    /* For iterating mutable collections, there is MutableIterator that extends Iterator with the element removal
    function remove(). So, you can remove elements from a collection while iterating it. */

    val names = mutableListOf("Ayşe", "Fatma", "Hayriye", "Haydi", "Çifte", "Telliye")
    val namesMutableIterator = names.iterator()
    println("Item is ${namesMutableIterator.next()}, so remove this")
    namesMutableIterator.remove()
    println(names)

    println("\n--------------\n")

    /* In addition to removing elements, the MutableListIterator can also insert and replace elements while
    iterating the list. */

    val namesMutableListIterator = names.listIterator()
    println("Names list is $names")

    println("Osman added instead of first item with mutableListIterator")
    namesMutableListIterator.add("Osman")
    println(names)

    namesMutableListIterator.next()

    println("Oya added instead of second item with mutableListIterator")
    namesMutableListIterator.set("Oya")
    println(names)
}