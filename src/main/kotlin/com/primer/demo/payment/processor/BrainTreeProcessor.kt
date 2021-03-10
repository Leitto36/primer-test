package com.primer.demo.payment.processor

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.CreditCardRequest
import com.braintreegateway.TransactionRequest
import com.primer.demo.InvalidCreateCardProcessorResponseException
import com.primer.demo.InvalidTransactionProcessorResponseException
import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import org.springframework.stereotype.Component

import com.primer.demo.UnsuccessfulProcessorResponseException
import com.primer.demo.model.CardTokenStatus
import com.primer.demo.model.ProcessorType
import com.primer.demo.model.Transaction
import com.primer.demo.model.TransactionType
import com.primer.demo.util.log

@Component
class BrainTreeProcessor (
    private val brainTreeGateway: BraintreeGateway
) : DefaultProcessor {

    override fun createCreditCard(card: Card): CardToken = brainTreeGateway.creditCard()
        .create(toCreditCardRequest(card)).let {
            when {
                it.isSuccess -> CardToken(
                    processorType = ProcessorType.BRAIN_TREE,
                    token = it.target.token,
                    status = CardTokenStatus.ACTIVE
                )
                else -> {
                    log.error("process=create_credit_card  status=failed processor=${ProcessorType.BRAIN_TREE} " +
                            "merchant=${card.merchantId} reason=${it.message}")
                    throw InvalidCreateCardProcessorResponseException(
                        "Failed request for merchantId=${card.merchantId} " +
                                "with processor=${ProcessorType.BRAIN_TREE}")
                }
            }
        }

    override fun createTransaction(transaction: Transaction): Transaction =
        when (transaction.type) {
            TransactionType.SALE -> brainTreeGateway.transaction().sale(toTransactionRequest(transaction)).let {
                when {
                    it.isSuccess -> transaction.copy(
                        processorTransactionId = it.target.id,
                        createdAt = it.target.createdAt.toInstant().toEpochMilli()
                    )
                    else -> {
                        log.error("process=create_transaction status=failed processor=${ProcessorType.BRAIN_TREE} " +
                                "merchant=${transaction.merchantId} reason=${it.message}")
                        throw InvalidTransactionProcessorResponseException(
                            "Failed request for merchantId=${transaction.merchantId} with " +
                                    "processor=${ProcessorType.BRAIN_TREE}")
                    }
                }
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

    private fun toTransactionRequest(transaction: Transaction) : TransactionRequest = TransactionRequest()
        .creditCard()
            .token(transaction.token)
        .done()
        .amount(transaction.amount)
        .currencyIsoCode(transaction.currency.currencyCode)
        .paymentMethodNonce("fake-valid-nonce")
}
