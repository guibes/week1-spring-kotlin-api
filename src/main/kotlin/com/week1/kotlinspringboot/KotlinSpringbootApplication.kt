package com.week1.kotlinspringboot

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication class KotlinSpringbootApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringbootApplication>(*args) { setBannerMode(Banner.Mode.OFF) }
}

@RestController
class MessageResource {
    @GetMapping
    fun index(): List<Message> =
            listOf(
                    Message("1", "Hello!"),
                    Message("2", "Bonjour!"),
                    Message("3", "Privet!"),
            )
}

data class Message(val id: String?, val text: String)
