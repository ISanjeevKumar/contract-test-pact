package payment.api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import payment.api.datamodel.PaymentRequestModel
import payment.api.datamodel.PaymentResponseModel
import payment.api.services.TransactionService

@RestController
class TransactionController {
    @Autowired
    private lateinit var transactionService: TransactionService;

    @PostMapping("/process-payment")
    fun makePayment(@RequestBody paymentRequest: PaymentRequestModel): ResponseEntity<PaymentResponseModel> {
        val paymentResponse = transactionService.processPayment(paymentRequest)
        return ResponseEntity(paymentResponse, HttpStatus.OK)
    }
}