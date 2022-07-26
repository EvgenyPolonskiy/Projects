package com.example.springdb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringDbApplication

fun main(args: Array<String>) {
    runApplication<SpringDbApplication>(*args)
    println("test")

}
