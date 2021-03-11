package com.primer.demo.payment.processor

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.CreditCardRequest
import com.braintreegateway.TransactionRequest
import com.primer.demo.InvalidCreateCardProcessorResponseException
import com.primer.demo.InvalidTransactionProcessorResponseException
import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import org.springframework.stereotype.Component

import com.primer.demo.model.CardTokenStatus
import com.primer.demo.model.ProcessorType
import com.primer.demo.model.Transaction
import com.primer.demo.model.TransactionType
import com.primer.demo.util.log

import com.braintreegateway.CustomerRequest
import com.primer.demo.InvalidCustomerProcessorResponseException
import com.primer.demo.model.Merchant

@Component
class BrainTreeProcessor (
    private val brainTreeGateway: BraintreeGateway
) : DefaultProcessor {

    override fun createCreditCard(card: Card, merchant: Merchant, customerId: String?): CardToken {
        val id = customerId ?: createCustomer(merchant)

        return brainTreeGateway.creditCard().create(toCreditCardRequest(card = card, customerId = id))
            .let {
                when {
                    it.isSuccess -> CardToken(
                        customerId = id,
                        processorType = ProcessorType.BRAIN_TREE,
                        token = it.target.token,
                        status = CardTokenStatus.ACTIVE
                    )
                    else -> {
                        log.info("process=create_credit_card  status=failed processor=${ProcessorType.BRAIN_TREE} " +
                                "merchant=${card.merchantId} reason=${it.message}")
                        throw InvalidCreateCardProcessorResponseException(
                            "Failed request for merchantId=${card.merchantId} " +
                                    "with processor=${ProcessorType.BRAIN_TREE}")
                    }
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
                        log.info("process=create_transaction status=failed processor=${ProcessorType.BRAIN_TREE} " +
                                "merchant=${transaction.merchantId} reason=${it.message}")
                        throw InvalidTransactionProcessorResponseException(
                            "Failed request for merchantId=${transaction.merchantId} with " +
                                    "processor=${ProcessorType.BRAIN_TREE}")
                    }
                }
            }
        }

    override fun createCustomer(merchant: Merchant) : String = brainTreeGateway.customer()
        .create(toCustomerRequest(merchant)).let {
            when {
                it.isSuccess -> it.target.id
                else -> {
                    log.error("process=create_customer  status=failed processor=${ProcessorType.BRAIN_TREE} " +
                            "reason=${it.message}")
                    throw InvalidCustomerProcessorResponseException("Failed request with " +
                            "processor=${ProcessorType.BRAIN_TREE}")
                }
            }
    }

    private fun toCustomerRequest(merchant: Merchant): CustomerRequest = CustomerRequest()
        .firstName(merchant.firstName)
        .lastName(merchant.lastName)
        .company(merchant.company)
        .email(merchant.email)
        .phone(merchant.phone)
        .website(merchant.website)

    private fun toCreditCardRequest(card: Card, customerId: String) : CreditCardRequest = CreditCardRequest()
        .customerId(customerId)
        .cardholderName(card.cardHolderName)
        .number(card.number)
        .expirationMonth(card.expirationMonth)
        .expirationYear(card.expirationYear)
        .options()
            .verifyCard(true)
        .done()

    private fun toTransactionRequest(transaction: Transaction) : TransactionRequest = TransactionRequest()
        .amount(transaction.amount)
        .currencyIsoCode(transaction.currency.currencyCode)
        .paymentMethodToken(transaction.token)
}
