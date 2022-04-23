package com.amandeep.kotlinbasics.kotlin.basics

import com.amandeep.kotlinbasics.kotlin.basics.OuterInnerClass.InnerNestedClass

/**
 * A class which is created inside another class
 *  - In kotlin a netsted class by default static , so its data members and function members can be accessed without creating object of class.
 *  - Nested class can not access the data member of outer class.
 *  - Nested class must be initialized. ex   OuterClass.NestedClass().dataFunctions()
 *
 *
 * A class which is created inside another class with keyword inner
 * - Inner class is a class which is created inside another class with keyword inner
 * - Inner class can not be declared inside interfaces or non-inner nested classes
 * - Advantages of inner classes
 *      - it is able to access the member of outer class even it is private.
 * - Inner class keeps a reference to an object of its outer class.
 * */


class OuterClass{
    class NestedClass{
        val dataMembers=10;
        fun dataFunctions(){
            print("DataFunctions")
        }
    }
}



class  OuterInnerClass{
    val outerClassMembers=50
    private fun outerClassMemberFunction(): String {
        return "outerClassMemberFunction"
    }
    inner class InnerNestedClass{
        val innerNestedMember=10
        fun innerDataMembers(){
            println("innerDataMembers")
        }

        // OuterInner class members and function members can be accessed by Inner class
        fun canAccessOuterClassMembers(){
            println("Outer member accessed by inner class $outerClassMembers")
            println("Outer member function accessed by inner class ${outerClassMemberFunction()}")

        }
    }
}

fun main() {
    // Nested class must be initialized
    val outerClass = OuterClass.NestedClass().dataFunctions()

    val outerInnerClass = OuterInnerClass()
    outerInnerClass.InnerNestedClass().canAccessOuterClassMembers()


}