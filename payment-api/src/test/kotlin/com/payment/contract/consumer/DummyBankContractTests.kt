package com.payment.contract.consumer

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.apache.http.client.fluent.Request
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "Dummy-Bank-Provider", hostInterface = "localhost", port = "9020")
class DummyBankContractTests {

    @Pact(consumer = "Get-Bank-Details-Consumer")
    fun getBankInfo(builder: PactDslWithProvider): RequestResponsePact {
        val expectedResponse = PactDslJsonBody()
            .stringType("accountName", "Dummy Bank")
            .integerType("sortCode", 123456)
            .integerType("sortCode", 12345678)
            .asBody()

        return builder.given("get bank info details")
            .uponReceiving("a request to get bank details")
            .path("/bank/details")
            .method("GET")
            .willRespondWith()
            .matchHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .status(200)
            .body(expectedResponse)
            .toPact()
    }

    @PactTestFor(pactMethod = "getBankInfo")
    @Test
    fun `should create journey for on boarded product`(mockServer: MockServer) {
        val response = Request.Get(mockServer.getUrl() + "/bank/details")
            .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .execute().returnResponse()
        assertEquals(200, response.statusLine.statusCode)
    }
}