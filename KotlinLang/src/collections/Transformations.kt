package collections

/**
 * The Kotlin standard library provides a set of extension functions for collection transformations.
 * These functions build new collections from existing ones based on the transformation rules provided.
 * In this page, we'll give an overview of the available collection transformation functions.
 *
 * Mapping
 * Zipping
 * Association
 * Flattening
 * String representation
 *
 * https://kotlinlang.org/docs/reference/collection-transformations.html
 * */

fun main() {

    // TODO Mapping
    mapping()

    println("\n****************************\n")

    // TODO Zipping
    zipping()

    println("\n****************************\n")

    // TODO Association
    association()

    println("\n****************************\n")

    // TODO Flattening
    flattening()

    println("\n****************************\n")

    // TODO String representation
    stringRepresentation()

}

fun mapping() {

    /* The mapping transformation creates a collection from the results of a function on the elements of another
    collection. The basic mapping function is map(). It applies the given lambda function to each subsequent element
    and returns the list of the lambda results. The order of results is the same as the original order of elements.
    To apply a transformation that additionally uses the element index as an argument, use mapIndexed(). */

    val numbers = setOf(1, 2, 3)
    println("Numbers : $numbers")

    val mapIndexedNumbers = numbers.mapIndexed { _, value -> value * value } // uses the element index as an argument
    println("Map indexed numbers : $mapIndexedNumbers")

    val mapNumbers = numbers.map { it * it }
    println("Mapping numbers : $mapNumbers")

    println("\n--------------\n")

    /* If the transformation produces null on certain elements, you can filter out the nulls from the result
    collection by calling the mapNotNull() function instead of map(), or mapIndexedNotNull() instead of mapIndexed(). */

    val mappingNullNumbers = numbers.map { if (it > 3) it * it else null }
    println("Mapping null numbers : $mappingNullNumbers")

    val mappingNotNullNumbers = numbers.mapNotNull { if (it > 3) it * it else null }
    println("Mapping not null numbers : $mappingNotNullNumbers")

    val mapIndexedNotNullNumbers = numbers.mapIndexedNotNull { _, value -> if (value > 3) value * value else null }
    println("Map indexed not null numbers : $mapIndexedNotNullNumbers")

    println("\n--------------\n")

    /* When transforming maps, you have two options: transform keys leaving values unchanged and vice versa.
    To apply a given transformation to keys, use mapKeys(); in turn, mapValues() transforms values.
    Both functions use the transformations that take a map entry as an argument,
    so you can operate both its key and value. */

    val numbersMap = mapOf("One" to 1, "Two" to 2, "Three" to 3, "Four" to 4)

    val mapKeys = numbersMap.mapKeys { it.key.toUpperCase() }
    println("Map keys called, look at keys : $mapKeys")

    val mapValues = numbersMap.mapValues { it.value * it.value }
    println("Map values called, look at values : $mapValues")

}

fun zipping() {

    /* Zipping transformation is building pairs from elements with the same positions in both collections.
    In the Kotlin standard library, this is done by the zip() extension function.
    When called on a collection or an array with another collection (array) as an argument, zip() returns
    the List of Pair objects. The elements of the receiver collection are the first elements in these pairs.
    If the collections have different sizes, the result of the zip() is the smaller size; the last elements of
    the larger collection are not included in the result. zip() can also be called in the infix form a zip b. */

    val colors = listOf("red", "brown", "grey", "yellow")
    val animals = listOf("fox", "bear", "wolf")

    println(colors zip animals)
    println(animals zip colors)

    println("\n--------------\n")

    val manyAnimals = listOf("fox", "bear", "wolf", "cat", "dog")

    println(colors.zip(manyAnimals))
    println(manyAnimals.zip(colors))

    println("\n--------------\n")

    println("Animals with iterator : ")
    val iterator = colors.zip(manyAnimals).iterator()
    iterator.forEach { println(it.second) }

    println("\n--------------\n")

    /* You can also call zip() with a transformation function that takes two parameters: the receiver element and
    the argument element. In this case, the result List contains the return values of the transformation function called
    on pairs of the receiver and the argument elements with the same positions. */

    // color : receiver element, animal argument element
    val colorOfAnimals = colors.zip(manyAnimals) { color, animal -> "The $animal color is ${color.toUpperCase()}" }
    println("Colors of animals are :")
    colorOfAnimals.forEach { println(it) }

    println("\n--------------\n")

    /* When you have a List of Pairs, you can do the reverse transformation – unzipping – that builds two lists from these pairs:
        - The first list contains the first elements of each Pair in the original list.
        - The second list contains the second elements.
    To unzip a list of pairs, call unzip(). */

    val animalPairs = listOf("fox" to "red", "bear" to "brown", "wolf" to "grey")
    val unzipAnimalPairs = animalPairs.unzip()
    println("Animals with unzio : ${unzipAnimalPairs.first}")
    println("Colors with unzip : ${unzipAnimalPairs.second}")
}

data class FullName(val firstName: String, val lastName: String)

fun parseFullName(fullName: String): FullName {
    val nameParts = fullName.split(" ")
    if (nameParts.size == 2) {
        return FullName(nameParts[0], nameParts[1])
    } else throw Exception("Wrong name format")
}


fun association() {

    /* Association transformations allow building maps from the collection elements and certain values associated with
    them. In different association types, the elements can be either keys or values in the association map.

    The basic association function associateWith() creates a Map in which the elements of the original collection are
    keys, and values are produced from them by the given transformation function. If two elements are equal,
    only the last one remains in the map. */

    println("create map with using associateWith (list is key, association value is value on map (key-value) : ")
    val animals = listOf("fox", "bear", "wolf", "cat", "dog")
    val associationWithAnimals = animals.associateWith { it.length }
    associationWithAnimals.forEach { (key, value) -> println("$key $value") }

    println("\n--------------\n")

    /* For building maps with collection elements as values, there is the function associateBy().
    It takes a function that returns a key based on an element's value. If two elements are equal,
    only the last one remains in the map. associateBy() can also be called with a value transformation function. */

    println("create map with using associateBy (list is value, association value is key on map (key-value) : ")
    val associationByAnimals = animals.associateBy { it.first().toUpperCase() }
    associationByAnimals.forEach { (key, value) -> println("$key $value") }

    println("\n--------------\n")

    println("create map with using associateBy (valueTransform is value, keySelector is key on map (key-value) : ")
    val associationKeyValueSelectorAnimals =
        animals.associateBy(keySelector = { it.first().toUpperCase() }, valueTransform = { it })
    associationKeyValueSelectorAnimals.forEach { (key, value) -> println("$key $value") }

    println("\n--------------\n")

    /* Another way to build maps in which both keys and values are somehow produced from collection elements is
    the function associate(). It takes a lambda function that returns a Pair: the key and the value of the corresponding
    map entry.

    Note that associate() produces short-living Pair objects which may affect the performance. Thus, associate()
    should be used when the performance isn't critical or it's more preferable than other options.

    An example of the latter is when a key and the corresponding value are produced from an element together. */

    println("create map with using associate : ")
    val names = listOf("Ayşe Yılmaz", "Fatma Yılmaz", "Hayriye Yılmaz", "Osman Çetin", "Ali Çetin")
    val associateNames = names.associate { name -> parseFullName(name).let { it.firstName to it.lastName } }
    associateNames.forEach { (key, value) -> println("Firstname = $key Lastname = $value") }

    // Here we call a transform function on an element first, and then build a pair from the properties of
    // that function's result.
}

fun flattening() {
    /* If you operate nested collections, you may find the standard library functions that provide flat access to
    nested collection elements useful.

    The first function is flatten(). You can call it on a collection of collections, for example, a List of Sets.
    The function returns a single List of all the elements of the nested collections. */

    val nameSets = listOf(setOf("Ayşe", "Fatma", "Hayriye"), setOf("Ali", "Veli"), setOf("Selin", "Pelin"))
    println("name sets $nameSets")

    val flattenName = nameSets.flatten()
    println("flatten name sets $flattenName")

}

fun stringRepresentation() {
    /* If you need to retrieve the collection content in a readable format, use functions that transform
    the collections to strings: joinToString() and joinTo().

    joinToString() builds a single String from the collection elements based on the provided arguments.
    joinTo() does the same but appends the result to the given Appendable object.

    When called with the default arguments, the functions return the result similar to calling toString() on
    the collection: a String of elements' string representations separated by commas with spaces. */

    val names = listOf("Büşra", "Aysun", "İlayda")
    println(names)
    println(names.joinToString())

    println("\n--------------\n")

    val description = StringBuffer("List of names : ")
    names.joinTo(description)
    println(description)

    /* To build a custom string representation, you can specify its parameters in function arguments separator, prefix,
    and postfix. The resulting string will start with the prefix and end with the postfix.
    The separator will come after each element except the last. */

    println(names.joinToString(separator = " & ", prefix = "Start ", postfix = " End"))

    /* For bigger collections, you may want to specify the limit – a number of elements that will be included into result.
    If the collection size exceeds the limit, all the other elements will be replaced with a single value of
    the truncated argument. */

    val numbers = (1..50).toList()
    println(numbers.joinToString(limit = 10, truncated = "..."))

    // Finally, to customize the representation of elements themselves, provide the transform function.

    println(names.joinToString { "Name : ${it.toUpperCase()}" })

}