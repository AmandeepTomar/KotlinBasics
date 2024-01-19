package com.amandeep.kotlinbasics.kotlin.basics


fun main() {

    val student = Student()
    student.name = "Amandeep"
    println("student ${student.name}")
    student.marks = 90
    student.name = "Amandeep Tomar"
    println("student ${student.name} ${student.marks}")

    println(student.courseName)
    student.courseName = "Updated COurse"
    println(student.courseName)


}

class Student {

    private var _courseName: String = "Course name" // backing field
    var courseName: String = ""
                get() = _courseName // backing property

    val isPass: Boolean
        get() = marks > 30 // this will not create the backing field because it is useing someother property
    // not included field in this getter.

    var marks: Int = 0
        get() = 10
        set


    var name: String = "NO Name"
        get() {
            println("Name Student => $field")
            return "Complete Name  => $field"
        }
        set(value) {
            field = if (marks > 80) "Good Student $value"
            else value
        }


}