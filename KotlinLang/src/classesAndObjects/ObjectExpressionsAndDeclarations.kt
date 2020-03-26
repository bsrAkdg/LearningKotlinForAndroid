package classesAndObjects


/**
 * Sometimes we need to create an object of a slight modification of some class, without explicitly declaring
 * a new subclass for it. Kotlin handles this case with object expressions and object declarations.
 *
 * https://kotlinlang.org/docs/reference/object-declarations.html
 *
 **/

fun main() {
    // TODO Object expressions
    objectExpressions()

    println("\n****************************\n")

    // TODO Object declarations
    objectDeclarations()

    println("\n****************************\n")

    // TODO Companion Objects
    companionObjectsWithObjectDeclarations()

    // TODO Semantic difference between object expressions and declarations
    differenceObjectExpressionsAndDeclarations()
}

open class ExampleObjectClass(newMessage: String) {
    open var message: String = newMessage
}

interface MessageInterface {
    fun showMessage(message : String)
}

fun objectExpressions() {
    // To create an object of an anonymous class that inherits from some type (or types), we write:

    /*window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) { ... }

        override fun mouseEntered(e: MouseEvent) { ... }
    })*/

    // If a supertype has a constructor, appropriate constructor parameters must be passed to it.
    // Many supertypes may be specified as a comma-separated list after the colon, For example MessageInterface :

    val exampleObjectClasss: ExampleObjectClass = object : ExampleObjectClass("Say Hello :)"), MessageInterface {
        override var message: String = ""
            set(value) {
                field = value
                showMessage(field)
            }

        override fun showMessage(message: String) {
            println("New message is $message")
        }
    }

    exampleObjectClasss.message = "How are you?"

    println("\n--------------\n")

    // If, by any chance, we need "just an object", with no nontrivial supertypes, we can simply say:

    val sayHello = object {
        var message : String = "How are you?"
        var author : String = "Okan"
    }

    println("Hey ${sayHello.author}, ${sayHello.message}")

    // Note that anonymous objects can be used as types only in local and private declarations.
    // If you use an anonymous object as a return type of a public function or the type of a public property,
    // the actual type of that function or property will be the declared supertype of the anonymous object,
    // or Any if you didn't declare any supertype. Members added in the anonymous object will not be accessible.

    // Look at showLoginRegisterMessage() with using loginMessage() and registerMessage() at below
    showLoginRegisterMessage()
}

// Private function, so the return type is the anonymous object type
private fun loginMessage() = object {
    val message: String = "loginMessage message"
}

// Public function, so the return type is Any
fun registerMessage() = object {
    val message: String = "registerMessage message"
}

fun showLoginRegisterMessage() {
    val loginMessage = loginMessage().message // OK!
    // val registerMessage = registerMessage().message //  ERROR: Unresolved reference 'message'
}

open class SuperType

object MessageManager : SuperType() {
    var message: String = ""

    fun showMessage() {
        println(message)
    }

    object BatteryManager {
        var battery: Int = 0

        fun showBattery() {
            println("Your batteries is $battery")
        }
    }
}

fun objectDeclarations() {
    // Singleton may be useful in several cases, and Kotlin (after Scala) makes it easy to declare singletons:

    // This is called an object declaration, and it always has a name following the object keyword.
    // Just like a variable declaration, an object declaration is not an expression,
    // and cannot be used on the right hand side of an assignment statement.

    /*val messageManager = object : MessageManager {
        // This way you can't access
    }*/

    // Object declaration's initialization is thread-safe and done at first access.

    // To refer to the object, we use its name directly, for example look at MessageManager
    MessageManager.message = "Say Hello with singleton object declarations"
    MessageManager.showMessage()

    // Such objects can have supertypes for example : SuperType

    println("\n--------------\n")

    // NOTE: object declarations can't be local (i.e. be nested directly inside a function),
    // but they can be nested into other object declarations or non-inner classes. For example look at BatteryManager

    // local declaration example :
    /*object LocalObjectDeclaration {
        // You cant this, open and look at error
    }*/

    // But you can this (nested into other object declarations : BatteryManager)
    MessageManager.BatteryManager.battery = 98
    MessageManager.BatteryManager.showBattery()
}

class ShopUtil {
    companion object DialogUtil{
        fun createShopUtil(): ShopUtil = ShopUtil()
    }
}

class PaymentUtil {
    companion object {
        fun createPaymentUtil(): PaymentUtil = PaymentUtil()
    }
}

interface Factory<T> {
    fun create(): T
}

class BillUtil {
    companion object : Factory<BillUtil> {
        override fun create(): BillUtil {
            println("Creating BillUtil class with Factory")
            return BillUtil()
        }
    }
}

fun companionObjectsWithObjectDeclarations() {
    // An object declaration inside a class can be marked with the companion keyword, for example DialogUtil on ShopUtil

    // Members of the companion object can be called by using simply the class name as the qualifier:
    val shopUtil = ShopUtil.createShopUtil()

    // The name of the companion object can be omitted, in which case the name Companion will be used, for example PaymentUtil
    val paymentUtil = PaymentUtil.createPaymentUtil()

    // The name of a class used by itself (not as a qualifier to another name) acts as a reference to
    // the companion object of the class (whether named or not):

    val newShopUtil = ShopUtil
    newShopUtil.createShopUtil()

    val newPaymentUtil = PaymentUtil
    newPaymentUtil.createPaymentUtil()

    // Note that, even though the members of companion objects look like static members in other languages,
    // at runtime those are still instance members of real objects, and can, for example, implement interfaces,
    // for example look at Factory implementation on BillUtil
    val billUtil = BillUtil.create()

}

fun differenceObjectExpressionsAndDeclarations() {
    // There is one important semantic difference between object expressions and object declarations:

    // 1. object expressions are executed (and initialized) immediately, where they are used;
    // look ad objectExpressions()

    // 2. object declarations are initialized lazily, when accessed for the first time;
    // look at objectDeclarations()

    // 3. a companion object is initialized when the corresponding class is loaded (resolved),
    // matching the semantics of a Java static initializer.
    // look at companionObjectsWithObjectDeclarations
}