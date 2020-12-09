package com.task.btc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@ComponentScan("com.task.btc")
@EnableWebMvc
class BtcApplication

fun main(args: Array<String>) {
    runApplication<BtcApplication>(*args)
}
