package com.primer.demo.payment

import com.primer.demo.model.MerchantConfiguration
import com.primer.demo.payment.processor.DefaultProcessor

interface PaymentProcessorFactory {

    fun getPaymentProcessor(config: MerchantConfiguration) : DefaultProcessor
}
