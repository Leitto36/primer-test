package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.payment.PaymentProcessorFactory
import com.primer.demo.repository.CardTokenRepository
import com.primer.demo.service.CardTokenService
import com.primer.demo.service.MerchantConfigurationService
import org.springframework.stereotype.Service

@Service
class CardTokenServiceImpl(
    private val cardTokenRepository: CardTokenRepository,
    private val merchantConfigurationService: MerchantConfigurationService,
    private val paymentProcessorFactory: PaymentProcessorFactory
) : CardTokenService {

    override fun createToken(card: Card): CardToken = cardTokenRepository.create(
        paymentProcessorFactory.getPaymentProcessor(
            merchantConfigurationService.getMerchantConfiguration(card.merchantId)
        ).createCreditCard(card)
    )
}
