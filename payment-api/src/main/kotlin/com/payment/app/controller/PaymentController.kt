package com.payment.app.controller

import com.payment.app.datamodel.PaymentRequestModel
import com.payment.app.datamodel.PaymentResponseModel
import com.payment.app.services.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payments")
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService;

    @PostMapping
    fun makePayment(@RequestBody paymentRequest: PaymentRequestModel): ResponseEntity<PaymentResponseModel> {
        val paymentResponse = paymentService.processPayment(paymentRequest)
        return ResponseEntity(paymentResponse, HttpStatus.OK)
    }
}