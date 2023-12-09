package contracts.provider

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import payment.api.datamodel.PaymentRequestModel
import payment.api.datamodel.PaymentResponseModel
import payment.api.services.TransactionService

@PactBroker(host = "localhost", port = "9292")
@Provider("transaction-xxxx")
class PaymentServiceProviderTest {

    @MockkBean
    private lateinit var transactionService: TransactionService

    @BeforeEach
    fun setupTestTarget(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", 8080)
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext?) {
        context?.verifyInteraction()
    }

    @State("Payment request is valid")
    fun validPaymentRequestState() {
        val request = PaymentRequestModel(
            "1234567890", 10.00, "INR", "credit_card", "1234 5678 9101 1121",
            "12/25", "123"
        )
        every { transactionService.processPayment(request) } returns PaymentResponseModel("1234567890", "success","Payment successful.")
    }
}