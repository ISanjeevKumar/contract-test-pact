package com.payment.app.services

import com.payment.app.datamodel.PaymentRequestModel
import com.payment.app.datamodel.PaymentResponseModel
import org.springframework.stereotype.Service

@Service
class PaymentService {
    fun processPayment(paymentRequest: PaymentRequestModel): PaymentResponseModel {
        // Perform payment processing logic here (mocked response for demonstration)
        val transactionId = "txn123"
        val status = "success"

        return PaymentResponseModel(transactionId, status)
    }
}