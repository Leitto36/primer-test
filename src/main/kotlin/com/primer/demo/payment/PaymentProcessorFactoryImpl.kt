package com.primer.demo.payment;

import com.braintreegateway.BraintreeGateway
import com.primer.demo.UnknownPaymentProcessorException
import com.primer.demo.model.MerchantConfiguration
import com.primer.demo.model.ProcessorType
import com.primer.demo.payment.processor.BrainTreeProcessor
import com.primer.demo.payment.processor.DefaultProcessor
import org.springframework.stereotype.Service

@Service
class PaymentProcessorFactoryImpl(
    private val brainTreeGateway: BraintreeGateway
) : PaymentProcessorFactory {

    override fun getPaymentProcessor(config: MerchantConfiguration): DefaultProcessor {
        return when(config.processorType) {
            ProcessorType.BRAIN_TREE -> BrainTreeProcessor(brainTreeGateway)
            else -> throw UnknownPaymentProcessorException("Cannot find processor with type=${config.processorType} " +
                    "for merchant=${config.merchantId}")
        }
    }
}
