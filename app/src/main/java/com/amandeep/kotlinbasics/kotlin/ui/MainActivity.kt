package com.amandeep.kotlinbasics.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
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
    }
}