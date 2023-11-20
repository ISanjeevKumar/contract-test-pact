package com.payment.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApp

fun main(args: Array<String>) {
    println("Starting Payment service!!!!")
    runApplication<PaymentApp>(*args)
}