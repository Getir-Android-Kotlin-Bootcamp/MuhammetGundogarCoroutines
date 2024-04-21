package com.example.muhammetgundogarcoroutines

import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.muhammetgundogarcoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Page 6
        exampleCoroutineLaunch()

        // Page 7
        exampleAsyncAwait()

        // Page 8
        exampleBlockingCoroutine()

        // Page 9
        exampleUpdateUI(binding.textView)

        // Page 10
        exampleNetworkCall()

        // Page 11
        exampleCoroutineDefaultDispatcher()

        // Page 12
        exampleWithContext()

        // Page 13
        exampleCancelJob()

        // Page 15
        val numbers = getNumbers(1000)
        println(numbers)

        // Page 16
        exampleFlowWithCollect()

        // Page 17
        exampleScopeLaunch()

        // Page 18
        exampleSupervisorJob()

        // Page 20
        exampleMainScope()

        // Page 23
        runBlocking {
            examplePage23()
        }

        // Page 24
        runBlocking {
            examplePage24()
        }

        // Page 25
        runBlocking {
            examplePage25()
        }

        // Page 26
        runBlocking {
            examplePage26()
        }

        // Page 27
        runBlocking {
            examplePage27()
        }

        // Page 28
        runBlocking {
            examplePage28()
        }

        // Page 31 and Page 32: Call examples from the ChannelExamples file
        exampleChannelSenderReceiver()
        exampleChannelSenderReceiverMultiple()
    }

    // Page 6
    private fun exampleCoroutineLaunch() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            println("Coroutine Tamamlandı")
        }
    }

    // Page 7
    private fun exampleAsyncAwait() {

        val deferred = GlobalScope.async {
            "merhaba!"
        }
        runBlocking {
            val mesaj = deferred.await()
            println(mesaj)
        }
    }

    // Page 8
    private fun exampleBlockingCoroutine() {
        // Test senaryolarında kullanılır Thread blokladığı için
        runBlocking {
            delay(5000)
            println("Coroutine Tamamlandı")
        }
    }

    // Page 9
    private fun exampleUpdateUI(textView: TextView) {

        CoroutineScope(Dispatchers.Main).launch {
            val text = "UI güncellendi"
            textView.text = text

        }
    }

    // Page 10
    private fun exampleNetworkCall() {
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://samepleapi.com"
            val response = URL(url).readText()

        }
    }

    // Page 11
    private fun exampleCoroutineDefaultDispatcher() {

        CoroutineScope(Dispatchers.Default).launch {
            var sum = 0
            for (i in 0..10) {
                sum += i
            }
            println("Sum $sum")
        }
    }

    // Page 12
    private fun exampleWithContext() {

        val url = "https://sampleapi.com"

        CoroutineScope(Dispatchers.IO).launch {
            val response = withContext(Dispatchers.IO) {
                URL(url).readText()
            }

        }
    }

    // Page 13
    private fun exampleCancelJob() {

        val scopeExample = CoroutineScope(Dispatchers.IO)
        val job = scopeExample.launch {
            // Your task here
        }
        // Coroutine iptal eder
        job.cancel()
    }

    // Page 15
    private fun getNumbers(delay: Long): List<Int> {

        val numbers = mutableListOf<Int>()
        for (i in 1..10) {
            Thread.sleep(delay)
            numbers.add(i)
        }
        return numbers
    }

    // Page 16
    private fun exampleFlowWithCollect() {

        val flow = getNumbersFlow(1000)
        CoroutineScope(Dispatchers.IO).launch {
            flow.collect { number ->
               println("Number $number")
            }
        }
    }

    private fun getNumbersFlow(delay: Long): Flow<Int> {
        return flow {
            for (i in 1..10) {
                emit(i)
                delay(delay)
            }
        }
    }

    // Page 17
    private fun exampleScopeLaunch() {

        val scopeLaunch = CoroutineScope(Dispatchers.IO)
        scopeLaunch.launch {
            // Start the coroutine
            delay(2000) // Simulating Work
            println("Hello")
        }
    }

    // Page 18
    private fun exampleSupervisorJob() {

        val supervisorJob = SupervisorJob()
        val scopePage18 = CoroutineScope(supervisorJob)

        scopePage18.launch {
            // Coroutine 1
            delay(2000)
            println("Coroutine 1 completed")
        }

        scopePage18.launch {
            // Coroutine 2
            delay(1000)
            println("Coroutine 2 completed")
        }
    }

    // Page 20
    private fun exampleMainScope() {

        val uiScope = MainScope()
        uiScope.launch {
            // UI coroutine
            delay(2000)
            println("Ui Coroutine Completed")
        }

        // Cancelling the UI scope after a delay
        GlobalScope.launch {
            delay(3000)
            uiScope.cancel()

        }
    }

    // Page 23
    private fun examplePage23() {

        runBlocking {
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            flow.collect { value ->  println(value.toString()) }
        }
    }

    // Page 24
    private fun examplePage24() {

        runBlocking {
            val numbers = listOf(1, 2, 3)
            val flow = numbers.asFlow()

            flow.collect { value -> println(value) }
        }
    }

    // Page 25
    private fun examplePage25() {

        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
            }

            val squaresFlow = numbersFlow.map { number -> number * number }

            squaresFlow.collect { square -> println(square) }
        }
    }

    // Page 26
    private fun examplePage26() {

        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }

            val oddFlow = numbersFlow.filter { number -> number % 2 == 1 }

            oddFlow.collect { odd -> println(odd) }
        }
    }

    // Page 27
    private fun examplePage27() {

        runBlocking {
            (1..3).asFlow()
                .transform { request ->
                    emit("Making Request $request")
                    emit(performRequest(request))
                }
                .collect { response -> println(response) }
        }
    }

    private suspend fun performRequest(request: Int): String {
        delay(1000)
        return "request $request"
    }

    // Page 28
    private fun examplePage28() {

        runBlocking {
            val numbersFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }

            numbersFlow
                .flowOn(Dispatchers.IO)
                .collect { number -> println(number) }
        }
    }
}