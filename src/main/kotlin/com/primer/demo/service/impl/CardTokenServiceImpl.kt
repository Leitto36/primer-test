package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.model.Merchant
import com.primer.demo.payment.PaymentProcessorFactory
import com.primer.demo.service.CardTokenService
import com.primer.demo.service.MerchantConfigurationService
import org.springframework.stereotype.Service

@Service
class CardTokenServiceImpl(
    private val paymentProcessorFactory: PaymentProcessorFactory,
    private val merchantConfigurationService: MerchantConfigurationService
) : CardTokenService {

    override fun createToken(card: Card, merchant: Merchant): CardToken {
        val config = merchantConfigurationService.getMerchantConfiguration(merchant.id)

        return paymentProcessorFactory.getPaymentProcessor(config)
            .createCreditCard(card = card, merchant = merchant, customerId = config.customerId)
    }
}
