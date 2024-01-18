package com.amandeep.kotlinbasics.kotlin.genericsinkotlin

fun main() {

   // val userComparator:Comparable<User> = adminComparator // it is not compile due to contravariance.

    val adminComparator : Comparable<AdminUser> = userComparator // this will compile
}
val adminComparator :Comparable<AdminUser> = object :Comparable<AdminUser> {
    override fun compareTo(other: AdminUser): Int {
        return 1;
    }
}

public val userComparator :Comparable<User> = object :Comparable<User>{
    override fun compareTo(other: User): Int {
        return 1;
    }
}


