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
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import payment.api.PaymentApp
import payment.api.datamodel.PaymentResponseModel
import payment.api.services.TransactionService

@PactBroker(host = "localhost", port = "9292")
@Provider("cart-payment-provider")
@SpringBootTest(classes = [PaymentApp::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class PaymentServiceProviderTest {

    @LocalServerPort
    lateinit var port: Integer

    @MockkBean
    lateinit var transactionService: TransactionService

    @BeforeEach
    fun setupTestTarget(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", port.toInt(), "/")
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext?) {
        context?.verifyInteraction()
    }

    @State("payment request is valid")
    fun `process the payment`() {
        val response = PaymentResponseModel(
            "1234567890",
            "success",
            "Payment successful."
        )
        every { transactionService.processPayment(any()) } returns response
    }
}