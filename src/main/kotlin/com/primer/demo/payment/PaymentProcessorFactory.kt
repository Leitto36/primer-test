package com.primer.demo.payment

import com.primer.demo.payment.processor.DefaultProcessor
import java.util.UUID

interface PaymentProcessorFactory {

    fun getPaymentProcessor(merchantId: UUID) : DefaultProcessor
}
