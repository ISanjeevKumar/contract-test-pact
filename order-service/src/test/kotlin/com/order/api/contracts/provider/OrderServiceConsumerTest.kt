package com.order.api.contracts.provider

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.io.IOException

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(hostInterface = "localhost", port = "9292")
class OrderServiceConsumerTest {

    @Pact(consumer = "Order-Service-Consumer", provider = "Payment-Provider")
    fun createOrder(builder: PactDslWithProvider): RequestResponsePact {
        val expectedRequest = PactDslJsonBody()
            .integerType("orderId", 123)
            .decimalType("totalAmount", 50.0)
            .`object`("customer")
            .stringType("id", "customer123")
            .stringType("name", "John Doe")
            .stringType("email", "john@example.com")
            .closeObject()
            .asBody()
        val expectedResponse = PactDslJsonBody()
            .stringType("transactionId", "txn123")
            .stringType("status", "success")
            .asBody()

        return builder
            .given("Payment request is valid")
            .uponReceiving("A request to process payment for the order")
            .path("/payments")
            .method("POST")
            .headers(mapOf(HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE))
            .body(expectedRequest)
            .willRespondWith()
            .matchHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .status(200)
            .body(expectedResponse)
            .toPact()

    }

    @PactTestFor(pactMethod = "createOrder")
    @Test
    fun `Should be able to complete payment for an order`(mockServer: MockServer) {
        val request = mapOf(
            "orderId" to 123,
            "totalAmount" to 50.0,
            "customer" to mapOf(
                "id" to "customer123",
                "name" to "John Doe",
                "email" to "john@example.com"
            )
        )
        val requestBody = ObjectMapperCache.objectMapper.writeValueAsString(request)

        val response = Request.Post(mockServer.getUrl()+"/payments")
            .bodyString(requestBody, ContentType.APPLICATION_JSON)
            .execute()
            .returnResponse()

        assertEquals(200, response.statusLine.statusCode)

    }

    object ObjectMapperCache {
        val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
        // Other caching or initialization logic if present

        fun <T> serialize(value: T): String {
            return objectMapper.writeValueAsString(value)
        }

        inline fun <reified T> deserialize(json: String): T {
            return objectMapper.readValue(json, T::class.java)
        }
    }
}