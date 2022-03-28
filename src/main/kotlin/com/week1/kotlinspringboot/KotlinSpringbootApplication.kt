package com.week1.kotlinspringboot

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.ui.set

@SpringBootApplication class KotlinSpringbootApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringbootApplication>(*args) { setBannerMode(Banner.Mode.OFF) }
}
