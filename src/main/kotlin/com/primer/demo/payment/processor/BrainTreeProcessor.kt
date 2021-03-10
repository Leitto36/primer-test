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

    override fun createCreditCard(card: Card): CardToken {
        try {
            return brainTreeGateway.creditCard().create(toCreditCardRequest(card)).let {
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
        } catch (e: Exception) {
            log.error("Failed call to brain tree processor for merchant=${card.merchantId}", e)
            throw UnsuccessfulProcessorResponseException("Response from processor=${ProcessorType.BRAIN_TREE} " +
                "wasn't returned for merchantId=${card.merchantId}")
        }
    }

    override fun createTransaction(transaction: Transaction): Transaction {
        try {
            return when(transaction.type) {
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
        } catch (e: Exception) {
            log.error("Failed call to brain tree processor for merchant=${transaction.merchantId}", e)
            throw UnsuccessfulProcessorResponseException("Response from processor=${ProcessorType.BRAIN_TREE} " +
                    "wasn't returned for merchantId=${transaction.merchantId}")
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
