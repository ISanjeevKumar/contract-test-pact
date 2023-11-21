package com.payment.app.datamodel

data class PaymentRequestModel(
    val orderId: Int,
    val totalAmount: Double,
    val customer: CustomerInfoModel
)