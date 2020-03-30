package classesAndObjects

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * https://kotlinlang.org/docs/reference/delegated-properties.html
 *
 **/

fun main() {

    // TODO Delegated Properties
    delegatedPropertiesOverview()

    println("\n****************************\n")

    // TODO Standard Delegates
    standartDelegates()

    println("\n****************************\n")

    // TODO Storing Properties in a Map
    storingPropertiesInMap()

    println("\n****************************\n")

    // TODO Local Delegated Properties (since 1.1)
    localDelegatedProperties()

    println("\n****************************\n")

    // TODO Property Delegate Requirements
    propertyDelegateRequirements()

    // TODO Translation Rules
    translationRules()

    println("\n****************************\n")

    // TODO Providing a delegate (since 1.1)
    providingDelegate()
}

class Delegate<T>(initial: T) {

    private var delegateInitial = initial

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("thank you for delegating '${property.name}' to me!")
        return delegateInitial
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        println("$value has been assigned to '${property.name}'")
        this.delegateInitial = value
    }
}

fun delegatedPropertiesOverview() {
    /*
    There are certain common kinds of properties, that, though we can implement them manually every time we need them,
    would be very nice to implement once and for all, and put into a library. Examples include:
        - lazy properties: the value gets computed only upon first access;
        - observable properties: listeners get notified about changes to this property;
        - storing properties in a map, instead of a separate field for each property.
     */

    // To cover these (and other) cases, Kotlin supports delegated properties:
    class Example {
        var p: String by Delegate("")
    }

    // The syntax is: val/var <property name>: <Type> by <expression>. The expression after by is the delegate,
    // because get() (and set()) corresponding to the property will be delegated to its getValue() and setValue() methods.
    // Property delegates don’t have to implement any interface, but they have to provide a getValue() function
    // (and setValue() — for vars). For example, look at Delegate class below

    // When we read from p that delegates to an instance of Delegate, the getValue() function from Delegate is called,
    // so that its first parameter is the object we read p from and the second parameter holds a description of p itself
    // (e.g. you can take its name). For example:
    println("Delegated property getter using : ")
    val e = Example()
    println(e.p)

    // Similarly, when we assign to p, the setValue() function is called. The first two parameters are the same,
    // and the third holds the value being assigned:
    println("Delegated property setter using : ")
    e.p = "New value"
}

class LazyUser {
    // default lazy is thread-safe
    val allPayments: List<Payment> by lazy {
        // (LazyThreadSafetyMode.NONE) {
        fetchPaymentsFromDatabase()
    }
}

class Payment(var id: Int) {}

fun fetchPaymentsFromDatabase(): List<Payment> {
    //costly operation
    println("fetching payments from database (called on lazy property)")
    return listOf(Payment(1), Payment(2), Payment(3))
}

val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

class ObservableDelegateUser {
    var name: String by Delegates.observable("<no name>") { prop, old, new ->
        println("Observable value changed : $old -> $new")
    }

    var max: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
        newValue > oldValue
    }
}

fun standartDelegates() {
    // The Kotlin standard library provides factory methods for several useful kinds of delegates.

    // TODO Lazy (http://ilkinulas.github.io/development/kotlin/2017/04/02/Kotlin-Delegation.html)
    // lazy() is a function that takes a lambda and returns an instance of Lazy<T> which can serve as a delegate
    // for implementing a lazy property: the first call to get() executes the lambda passed to lazy()
    // and remembers the result, subsequent calls to get() simply return the remembered result.

    val lazyUser = LazyUser()
    println("Calling of lazy property on LazyUser class : ")
    lazyUser.allPayments

    println("\n--------------\n")

    // other example
    println("Using of Lazy property : ")
    println("First calling of Lazy property : $lazyValue")
    println("Second calling of Lazy property : $lazyValue")

    println("\n--------------\n")

    // By default, the evaluation of lazy properties is synchronized: the value is computed only in one thread,
    // and all threads will see the same value. If the synchronization of initialization delegate is not required,
    // so that multiple threads can execute it simultaneously, pass LazyThreadSafetyMode.PUBLICATION as a parameter to
    // the lazy() function. And if you're sure that the initialization will always happen on the same thread as
    // the one where you use the property, you can use LazyThreadSafetyMode.NONE:
    // it doesn't incur any thread-safety guarantees and the related overhead.

    // TODO Observable
    // Delegates.observable() takes two arguments: the initial value and a handler for modifications.
    // The handler is called every time we assign to the property (after the assignment has been performed).
    // It has three parameters: a property being assigned to, the old value and the new one:

    println("Observable delegated property creating : ")
    val observableUser = ObservableDelegateUser()
    observableUser.name = "first"
    observableUser.name = "second"

    // If you want to intercept assignments and "veto" them, use vetoable() instead of observable().
    // The handler passed to the vetoable is called before the assignment of a new property value has been performed.

    println("Observable delegated property changing : ")
    observableUser.max = 10
    println(observableUser.max) // initial value is 0, so meets the max setter condition (10>0), so max is 10

    observableUser.max = 5
    println(observableUser.max) // Does not meet the max setter condition (5 > 10), so max still is 10
}

fun storingPropertiesInMap() {
    // One common use case is storing the values of properties in a map.
    // This comes up often in applications like parsing JSON or doing other “dynamic” things.
    // In this case, you can use the map instance itself as the delegate for a delegated property.

    class StorageUser(map: Map<String, Any?>) {
        val name: String by map
        val age: Int     by map
    }

    // In this example, the constructor takes a map:
    val storageUser = StorageUser(
        mapOf(
            "name" to "John Doe",
            "age" to 25
        )
    )

    // Delegated properties take values from this map (by the string keys –– names of properties):
    println("Storing properties in immutable map :")
    println(storageUser.name) // Prints "John Doe"
    println(storageUser.age)  // Prints 25
    //storageUser.name = "New name" you cant this, if you want this you should use MutableMap

    println("\n--------------\n")

    // This works also for var’s properties if you use a MutableMap instead of read-only Map:
    class MutableUser(map: MutableMap<String, Any?>) {
        var name: String by map
        var age: Int     by map
    }

    val storageMutableUser = MutableUser(
        mutableMapOf(
            "name" to "John Doe",
            "age" to 25
        )
    )

    println("Storing properties in mutable map :")
    println(storageMutableUser.name) // Prints "John Doe"
    println(storageMutableUser.age)  // Prints 25
    storageMutableUser.name = "Jessica"
    println("New user name ${storageMutableUser.name}")
}

class LocalDelegateUser {
    var age: Int = 0
}

fun localDelegateUser(showUser: () -> LocalDelegateUser) {
    val user by lazy {
        println("LocalDelegateUser lazy created")
        showUser()
    }

    println("Assign age to user : ")
    user.age = 28

    if (user.age > 0) {
        println("New user added!")
    }
}

fun localDelegatedProperties() {
    // You can declare local variables as delegated properties. For instance, you can make a local variable lazy:
    // Look at LocalDelegateUser class and localDelegateUser fun

    localDelegateUser {
        LocalDelegateUser()
    }
}

/*interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
}

interface ReadWriteProperty<in R, T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T)
}*/

class SimpleReadOnlyDelegate<out T>(private val id: T) : ReadOnlyProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = id.also {
        println("Getting value for ${property.name} from ${thisRef::class.simpleName}")
    }
}

class DelegateExample {
    val myProperty by SimpleReadOnlyDelegate("demo")
}

class SimpleDelegate<T> : ReadWriteProperty<Any, T?> {
    private var value: T? = null
    override fun getValue(thisRef: Any, property: KProperty<*>) = value
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        this.value = value
    }
}

class DelegateDemo {
    var someNum by SimpleDelegate<Int>()
    var someStr by SimpleDelegate<String>()
}

fun propertyDelegateRequirements() {
    // Here we summarize requirements to delegate objects.

    /* Description 1 : For a read-only property (i.e. a val), a delegate has to provide a function named getValue that
    takes the following parameters:

    thisRef — must be the same or a supertype of the property owner (for extension properties — the type being extended);
    property — must be of type KProperty<*> or its supertype.
    this function must return the same type as property (or its subtype). */

    println("Creating ReadOnlyProperty :")
    val readOnlyExample = DelegateExample()
    println(readOnlyExample.myProperty) // getValue() on SimpleReadOnlyDelegate

    //println("Changing ReadOnlyProperty :")
    //readOnlyExample.myProperty = "New value" You cant this
    //println(readOnlyExample.myProperty)

    println("\n--------------\n")

    /* Description 2 : For a mutable property (a var), a delegate has to additionally provide a function named setValue that
    takes the following parameters:

    thisRef — same as for getValue();
    property — same as for getValue();
    new value — must be of the same type as the property or its subtype. */

    val demo = DelegateDemo().apply {
        someNum = 42
        someStr = "demo"
    }
    println("${demo.someNum} ${demo.someStr}")

    /* Description 3 : getValue() and/or setValue() functions may be provided either as member functions of the delegate
    class or extension functions. The latter is handy when you need to delegate property to an object which doesn't
    originally provide these functions. Both of the functions need to be marked with the operator keyword. */

    /* Description 4 : The delegate class may implement one of the interfaces ReadOnlyProperty and ReadWriteProperty
    containing the required operator methods. These interfaces are declared in the Kotlin standard library:
    Look at ReadOnlyProperty and ReadWriteProperty interfaces */
}

fun translationRules() {
    /*
    Under the hood for every delegated property the Kotlin compiler generates an auxiliary property and delegates to it.
    For instance, for the property prop the hidden property prop$delegate is generated, and the code of the accessors
    simply delegates to this additional property:

    class C {
        var prop: Type by MyDelegate()
    }

    // this code is generated by the compiler instead:
    class C {
        private val prop$delegate = MyDelegate()
        var prop: Type
            get() = prop$delegate.getValue(this, this::prop)
        set(value: Type) = prop$delegate.setValue(this, this::prop, value)
    }

    The Kotlin compiler provides all the necessary information about prop in the arguments: the first argument this refers
    to an instance of the outer class C and this::prop is a reflection object of the KProperty type describing prop itself.

    Note that the syntax this::prop to refer a bound callable reference in the code directly is available only since
    Kotlin 1.1. (https://kotlinlang.org/docs/reference/reflection.html#bound-function-and-property-references-since-11)
    */
}

class MyDelegateFactory(var value: String) {
    operator fun provideDelegate(thisRef: Any, prop: KProperty<*>): ReadWriteProperty<Any, String> {
        // add some conditions
        println("returning a new ReadWriteProperty delegate for ${prop.name}")
        return MyDelegate(value)
    }
}

class MyDelegate(value: String) : ReadWriteProperty<Any, String> {
    var value: String = value
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        this.value = value
    }
}

class DelegateProvideDemo {
    var myProp by MyDelegateFactory("First step")
}

fun providingDelegate() {
    /* By defining the provideDelegate operator you can extend the logic of creating the object to which the property
    implementation is delegated. If the object used on the right hand side of by defines provideDelegate as a member or
    extension function, that function will be called to create the property delegate instance.

    One of the possible use cases of provideDelegate is to check property consistency when the property is created,
    not only in its getter or setter. */

    // Look at this sample https://kotlinlang.org/docs/reference/delegated-properties.html#providing-a-delegate-since-11

    // For simple example : https://americanexpress.io/advanced-kotlin-delegates/ : Adding One More Layer
    val delegateProvideDemo = DelegateProvideDemo()
    println(delegateProvideDemo.myProp)

    delegateProvideDemo.myProp = "Second step"
    println(delegateProvideDemo.myProp)

    // The provideDelegate method is called for each property during the creation of the DelegateProvideDemo instance,
    // and it performs the necessary validation right away.

    // Note that the provideDelegate method affects only the creation of the auxiliary property and doesn't affect
    // the code generated for getter or setter.
}
