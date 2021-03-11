package com.primer.demo.payment.processor

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.model.Merchant
import com.primer.demo.model.Transaction

interface DefaultProcessor {

    fun createCreditCard(card: Card, merchant: Merchant, customerId: String?) : CardToken

    fun createTransaction(transaction: Transaction) : Transaction

    fun createCustomer(merchant: Merchant) : String
}
