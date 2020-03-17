package classesAndObjects

/**
 * https://kotlinlang.org/docs/reference/generics.html
 * */

fun main() {

    // TODO Overview
    overview()

    // TODO Declaration-site variance
    siteVariance()

    example()

    // TODO Type projections
    // TODO Use-site variance: Type projections
    useSiteVariance()

}

fun overview() {
    // As in Java, classes in Kotlin may have type parameters:

    class Box<T>(t: T) {
        var value = t
    }

    // In general, to create an instance of such a class, we need to provide the type arguments:
    val musicBox: Box<String> = Box("Music")
    println("My ${musicBox.value} box is empty now")

    // But if the parameters may be inferred, e.g. from the constructor arguments or by some other means,
    // one is allowed to omit the type arguments:

    val cookBox = Box("Cook")
    println("My ${cookBox.value} box is empty now")

}

interface Production<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(item: T)
}

interface ProductionConsumer<T> {
    fun produce(): T
    fun consume(item: T)
}

fun siteVariance() {
    // List<out T> is like List<? extends T> in Java : If your generic class only use the generic type as output
    // of it’s function/s, then out is used i.e. : for example Production

    // List<in T> is like List<? super T> in Java : If your generic class only use the generic type as input
    // of it’s function/s, then in is used i.e. : for example Consumer

    // In the event one generic class uses the generic type as input and output to it’s function,
    // then no in or out is used. It is invariant. : for example ProductionConsumer

}

fun useSiteVariance() {
    // It is very convenient to declare a type parameter T as out and avoid trouble with subtyping on the use site,
    // but some classes can't actually be restricted to only return T's! A good example of this is Array:

    /*class Array<T>(val size: Int) {
        var list = Array<T>(size)

        fun get(index: Int): T {
            return list.get(index)
        }

        fun set(index: Int, value: T) {
            list[index] = value
        }
    }*/

    // This class cannot be either co- or contravariant in T. And this imposes certain inflexibilities.
    // Consider the following function:

    fun copy(from: Array<Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    // This function is supposed to copy items from one array to another. Let's try to apply it in practice:
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    // copy(ints, any) TODO look at this warning
    //   ^ type is Array<Int> but Array<Any> was expected


    // the only thing we want to ensure is that copy() does not do any bad things.
    // We want to prohibit it from writing to from, and we can:

    fun copyNew(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    copyNew(ints, any)
}

open class Food
open class FastFood : Food()
open class Burger : FastFood()

fun example() {
    class FoodStore : Production<Food> {
        override fun produce(): Food {
            println("Produce food")
            return Food()
        }
    }

    class FastFoodStore : Production<FastFood> {
        override fun produce(): FastFood {
            println("Produce fast food")
            return FastFood()
        }
    }

    class InOutBurger : Production<Burger> {
        override fun produce(): Burger {
            println("Produce burger")
            return Burger()
        }
    }

    val production1 : Production<Food> = FoodStore()
    val production2 : Production<Food> = FastFoodStore()
    val production3 : Production<Food> = InOutBurger()

    // Either a burger or fastFood production, is still a food production. Hence
    // For 'out' generic, we could assign a class of subtype to class of super-type

    // If we change to below, it would error out, because food or fastFood is not just a burger production.
    // val productionBurger1 : Production<Burger> = FoodStore()  // Error
    // val productionBurger2 : Production<Burger> = FastFoodStore()  // Error
    val productionBurger3 : Production<Burger> = InOutBurger()


    // Now, looking at the genericConsumer interface defined above, let’s further expand them to consume food,
    // fastfood and burger as below

    class Everybody : Consumer<Food> {
        override fun consume(item: Food) {
            println("Everybody eat food")
        }
    }

    class ModernPeople : Consumer<FastFood> {
        override fun consume(item: FastFood) {
            println("ModernPeople eat fast food")
        }
    }

    class American : Consumer<Burger> {
        override fun consume(item: Burger) {
            println("American eat burger")
        }
    }

    val consumer1 : Consumer<Burger> = Everybody()
    val consumer2 : Consumer<Burger> = ModernPeople()
    val consumer3 : Consumer<Burger> = American()


    // Here, a burger consumer is an American, who is also part of ModernPeople, who is part of Everybody. Hence
    // For ‘in' generic, we could assign a class of super-type to class of subtype

    // If we change to below, it would error out, because consumer of Food although could be American or ModernPeople,
    // it is not just American or just ModernPeople.

    val consumerFood1 : Consumer<Food> = Everybody()
    // val consumerFood2 : Consumer<Food> = ModernPeople()  // Error
    // val consumerFood3 : Consumer<Food> = American()  // Error

    // Note
    // SuperType could be assigned subtype, use IN
    // SubType could be assigned to SuperType, use OUT
}
