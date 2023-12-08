package payment.api.datamodel

data class PaymentRequestModel(
    val transactionId :String,
    val amount: Double,
    val currency: String,
    val paymentMethod: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String
)