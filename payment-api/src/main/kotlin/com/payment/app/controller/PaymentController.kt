package com.payment.app.controller

import com.payment.app.datamodel.BankDetails
import com.payment.app.services.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService;

    @GetMapping("/bank/details")
    fun getBankDetails(): BankDetails {
        return paymentService.getBankDetails()
    }
}