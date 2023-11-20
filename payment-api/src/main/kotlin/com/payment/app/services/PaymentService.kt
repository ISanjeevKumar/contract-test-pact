package com.payment.app.services

import com.payment.app.datamodel.BankDetails
import org.springframework.stereotype.Service

@Service
class PaymentService {

    fun getBankDetails() : BankDetails {
        return BankDetails("Dummy Account", 123456, 22234433)
    }
}