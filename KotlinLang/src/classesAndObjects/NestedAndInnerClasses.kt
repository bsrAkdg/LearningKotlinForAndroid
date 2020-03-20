package classesAndObjects

import java.awt.event.ActionListener


/**
 * Classes can be nested in other classes.
 *
 * https://kotlinlang.org/docs/reference/nested-classes.html
 *
 **/


fun main() {
    // TODO Overview
    nestedOverview()

    // TODO Inner classes
    innerClasses()

    // TODO Anonymous inner classes
    anonymousInnerClasses()
}

class User {
    var gender: Int = 0

    class Gender {
        private val girl = 1
        private val boy = 2

        fun getGenderDescription(user: User) = "${getGenderTitle(user.gender)} is a ${getGenderName(user.gender)}"

        fun getGenderTitle(genderId: Int): String {
            return when (genderId) {
                girl -> "She"
                boy -> "He"
                else -> "It"
            }
        }

        fun getGenderName(genderId: Int): String {
            return when (genderId) {
                girl -> "girl"
                boy -> "boy"
                else -> "unknown"
            }
        }
    }
}

fun nestedOverview() {
    // Classes can be nested in other classes:
    // Look at User and Gender classes

    val girlUser = User()
    girlUser.gender = 1

    println(User.Gender().getGenderDescription(girlUser))
}

class NewUser {
    var gender: Int = 0

    inner class Gender {
        private val girl = 1
        private val boy = 2

        fun getGenderDescription() = "${getGenderTitle(gender)} is a ${getGenderName(gender)}"

        fun getGenderTitle(genderId: Int): String {
            return when (genderId) {
                girl -> "She"
                boy -> "He"
                else -> "It"
            }
        }

        fun getGenderName(genderId: Int): String {
            return when (genderId) {
                girl -> "girl"
                boy -> "boy"
                else -> "unknown"
            }
        }
    }
}

fun innerClasses() {
    // A nested class marked as inner can access the members of its outer class.
    // Inner classes carry a reference to an object of an outer class:

    val girlUser = NewUser()
    girlUser.gender = 1

    println(girlUser.Gender().getGenderDescription())
}

fun anonymousInnerClasses() {
    // Anonymous inner class instances are created using an object expression:

    /*window.addMouseListener(object : MouseAdapter() {

        override fun mouseClicked(e: MouseEvent) { ... }

        override fun mouseEntered(e: MouseEvent) { ... }
    })*/

    // Note: on the JVM, if the object is an instance of a functional Java interface (i.e. a Java interface with
    // a single abstract method), you can create it using a lambda expression prefixed with the type of the interface:

    val listener = ActionListener { println("clicked") }
}