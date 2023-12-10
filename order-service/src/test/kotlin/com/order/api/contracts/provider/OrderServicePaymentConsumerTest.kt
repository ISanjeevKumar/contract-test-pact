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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(hostInterface = "localhost", port = "9292")
class OrderServicePaymentConsumerTest {

    @Pact(consumer = "user-order-consumer", provider = "cart-payment-provider")
    fun createPaymentPact(builder: PactDslWithProvider): RequestResponsePact {
        val expectedRequest = PactDslJsonBody()
            .stringType("transactionId", "1234567890")
            .decimalType("amount", 50.0)
            .stringType("currency", "INR")
            .stringType("paymentMethod", "credit_card")
            .stringType("cardNumber", "1234 5678 9101 1121")
            .stringType("expiryDate", "12/25")
            .stringType("cvv", "123")
            .asBody()
        val expectedResponse = PactDslJsonBody()
            .stringType("transactionId", "1234567890")
            .stringType("status", "success")
            .stringType("message", "Payment successful.")
            .asBody()

        return builder
            .given("payment request is valid")
            .uponReceiving("A request to process payment for the order")
            .path("/process-payment")
            .method("POST")
            .headers(mapOf(HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE))
            .body(expectedRequest)
            .willRespondWith()
            .matchHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .status(200)
            .body(expectedResponse)
            .toPact()

    }

    @PactTestFor(pactMethod = "createPaymentPact")
    @Test
    fun `should complete payment for an order`(mockServer: MockServer) {
        val requestPayload = mapOf(
            "transactionId" to "1234567890",
            "amount" to 50.0,
            "currency" to "INR",
            "paymentMethod" to "credit_card",
            "cardNumber" to "1234 5678 9101 1121",
            "expiryDate" to "12/25",
            "cvv" to "123"
        )
        val requestBody = ObjectMapperCache.objectMapper.writeValueAsString(requestPayload)

        val response = Request.Post(mockServer.getUrl() + "/process-payment")
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