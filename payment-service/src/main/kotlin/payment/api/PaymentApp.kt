package payment.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentApp

fun main(args: Array<String>) {
    println("Starting Order service!!!!")
    runApplication<PaymentApp>(*args)
}