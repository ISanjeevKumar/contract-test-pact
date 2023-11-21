package order.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrderApp

fun main(args: Array<String>) {
    println("Starting Order service!!!!")
    runApplication<OrderApp>(*args)
}