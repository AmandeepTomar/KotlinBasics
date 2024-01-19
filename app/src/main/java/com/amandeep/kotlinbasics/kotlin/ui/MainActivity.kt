package com.amandeep.kotlinbasics.kotlin.ui

import android.content.Intent
import android.graphics.Paint.Style
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.amandeep.kotlinbasics.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.HttpRetryException

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e("TAG", "handleException : ${throwable.cause}")
        }

        lifecycleScope.launch(Dispatchers.Main + handler) {
            supervisorScope {
                launch {
                    launch {
                        delay(40)
                        throw Exception("Coroutine 1 Error")
                    }
                }

                launch {
                    delay(100)
                    println("Coroutine 2 sccess")
                }

                launch {
                    delay(400)
                    println("Coroutine 3 sccess")
                }
            }

        }

// Wrong One
        lifecycleScope.launch {
            val job = launch {
                try {
                    delay(1000)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                // this code will be called because we cath the CancellationException so that will not perform its cancellation.
                println("This will be print here")
            }

            delay(100)
            job.cancel()
        }


        // Correct One

        lifecycleScope.launch {
            val job = launch {
                try {
                    delay(1000)
                } catch (e: HttpRetryException) {
                    e.printStackTrace()
                }
                // this code will be called because we cath the CancellationException so that will not perform its cancellation.
                println("Nope cant print")
            }

            delay(100)
            job.cancel()
        }


        var name: String? = "Initial"

        val student = Student()

        val length: Int = name?.let {
            // return lambda
            val newValue = it.plus("Name")
            newValue.length
        } ?: 0

        println("length with null safety and with let $length")

        val studentRun: String = Student().run {
            // return lambda
            name = "Name Run"
            age = 151
            "$name and $age WithRun"
        }

        println("studentRun $studentRun")

        val inWords: String = with(Student()) {
            name = "With Name"
            age = 10
            // return lambda
            "name $name and Age $age"
        }


        val studentAfterAlso: Student = student.also {
            // return same object
            "Name of $it" // no use
            it.age = 10
        }

        println("StudentAfterAlso $studentAfterAlso")

        val studentApply: Student = Student().apply {
            // return Same object
            name = "Apply Name"
            age = 15
        }


    }
}

class Student() {
    var name: String = ""
    var age: Int = 0
}