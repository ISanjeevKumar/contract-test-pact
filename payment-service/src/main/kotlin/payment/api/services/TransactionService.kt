package payment.api.services

import org.springframework.stereotype.Service
import payment.api.datamodel.PaymentRequestModel
import payment.api.datamodel.PaymentResponseModel

@Service
class TransactionService {

    fun processPayment(paymentRequest: PaymentRequestModel): PaymentResponseModel {
        return if (isPaymentValid(paymentRequest)) {
            PaymentResponseModel(paymentRequest.transactionId, "success","Payment successful.")
        } else {
            PaymentResponseModel(paymentRequest.transactionId, "failed","Payment declined.")
        }

    }

    private fun isPaymentValid(paymentRequest: PaymentRequestModel): Boolean {
        return paymentRequest.amount > 0
    }
}