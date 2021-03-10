package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.payment.PaymentProcessorFactory
import com.primer.demo.service.CardTokenService
import org.springframework.stereotype.Service

@Service
class CardTokenServiceImpl(
    private val paymentProcessorFactory: PaymentProcessorFactory
) : CardTokenService {

    override fun createToken(card: Card): CardToken =
        paymentProcessorFactory.getPaymentProcessor(card.merchantId).createCreditCard(card)
}
