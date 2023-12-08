package payment.api.datamodel

data class PaymentResponseModel(
    val transactionId: String,
    val status: String,
    val message: String
)