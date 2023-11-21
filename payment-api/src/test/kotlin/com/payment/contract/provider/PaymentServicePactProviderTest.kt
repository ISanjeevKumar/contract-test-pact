package com.payment.contract.provider

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import com.ninjasquad.springmockk.MockkBean
import com.payment.app.datamodel.CustomerInfoModel
import com.payment.app.datamodel.PaymentRequestModel
import com.payment.app.services.PaymentService
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@PactBroker(host = "localhost", port = "9292")
@Provider("Payment-Provider")
class PaymentServicePactProviderTest {


    @MockkBean
    lateinit var paymentService: PaymentService

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext?) {
        context?.verifyInteraction()
    }

    @State("Payment request is valid")
    fun validPaymentRequestState() {
        val validCustomer = CustomerInfoModel("customer123", "John Doe", "john@example.com")
        val validPaymentRequest = PaymentRequestModel(123, 50.0, validCustomer)
        val transactionId = "txn123"
        val status = "success"
        // Mocking PaymentService's behavior for processing a valid payment request
       // every { paymentService.processPayment(validPaymentRequest) } returns PaymentResponseModel(transactionId, status)
    }

}