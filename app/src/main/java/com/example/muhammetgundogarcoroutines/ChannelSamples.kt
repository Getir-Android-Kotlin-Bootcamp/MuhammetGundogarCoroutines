package com.example.muhammetgundogarcoroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// Page 31
fun exampleChannelSenderReceiver() = runBlocking {
    val channel = Channel<Int>()

    launch {
        println("Sender Started")
        channel.send(10)
        println("Value Sent 10")
    }

    launch {
        println("receiver started")
        val value = channel.receive()
        println("Value received: $value")
    }
}

// Page 32
fun exampleChannelSenderReceiverMultiple() = runBlocking {
    val channel = Channel<String>()

    launch {
        println("Sender Started")
        for (i in 1..5) {
            channel.send("Message $i")

            println("Message sent: Message $i")
        }
    }

    launch {
        println("Receiver Start")
        while (true) {
            val message = channel.receive()
            println("Message received: $message")
        }
    }
}