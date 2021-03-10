package com.primer.demo.payment;

import com.braintreegateway.BraintreeGateway
import com.primer.demo.UnknownPaymentProcessorException
import com.primer.demo.model.ProcessorType
import com.primer.demo.payment.processor.BrainTreeProcessor
import com.primer.demo.payment.processor.DefaultProcessor
import com.primer.demo.service.MerchantConfigurationService
import java.util.UUID
import org.springframework.stereotype.Service;

@Service
class PaymentProcessorFactoryImpl(
    private val merchantConfigurationService: MerchantConfigurationService,
    private val brainTreeGateway: BraintreeGateway
) : PaymentProcessorFactory {

    override fun getPaymentProcessor(merchantId: UUID): DefaultProcessor {
        val config = merchantConfigurationService.getMerchantConfiguration(merchantId)

        return when(config.processorType) {
            ProcessorType.BRAIN_TREE -> BrainTreeProcessor(brainTreeGateway)
            else -> throw UnknownPaymentProcessorException("Cannot find processor with type=${config.processorType} for merchant=${config.merchantId}")
        }
    }
}
