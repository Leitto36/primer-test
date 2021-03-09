package com.primer.demo.payment.processor

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken

interface DefaultProcessor {

    fun createCreditCard(card: Card) : CardToken
}
