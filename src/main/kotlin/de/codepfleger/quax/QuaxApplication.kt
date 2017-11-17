package de.codepfleger.quax

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class QuaxApplication

fun main(args: Array<String>) {
    SpringApplication.run(QuaxApplication::class.java, *args)
}
