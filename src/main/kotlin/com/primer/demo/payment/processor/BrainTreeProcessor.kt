package com.primer.demo.payment.processor

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.CreditCardRequest
import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import org.springframework.stereotype.Component

import com.primer.demo.UnsuccessfulProcessorResponseException
import com.primer.demo.model.CardTokenStatus
import com.primer.demo.model.ProcessorType
import com.primer.demo.util.log

@Component
class BrainTreeProcessor (
    private val brainTreeGateway: BraintreeGateway
) : DefaultProcessor {

    override fun createCreditCard(card: Card): CardToken {
        try {
            return brainTreeGateway.creditCard().create(toCreditCardRequest(card)).let {
                when {
                    it.isSuccess -> CardToken(
                        cardId = card.id!!,
                        processorType = ProcessorType.BRAIN_TREE,
                        token = it.target.token,
                        status = CardTokenStatus.ACTIVE
                    )
                    else -> {
                        log.error("Failed call to brain tree processor for merchant=${card.merchantId} reason=${it.message}")
                        throw UnsuccessfulProcessorResponseException("Failed request for merchantId=${card.merchantId}")
                    }
                }
            }
        } catch (e: Exception) {
            log.error("Failed call to brain tree processor for merchant=${card.merchantId}", e)
            throw UnsuccessfulProcessorResponseException("Response from processor=${ProcessorType.BRAIN_TREE} wasn't returned for merchantId=${card.merchantId}")
        }
    }

    private fun toCreditCardRequest(card: Card) : CreditCardRequest =  CreditCardRequest()
        .customerId(card.merchantId.toString())
        .cardholderName(card.cardHolderName)
        .number(card.number)
        .expirationMonth(card.expirationMonth.toString())
        .expirationYear(card.expirationYear.toString())
        .options()
            .verifyCard(true)
        .done()
}
