package com.primer.demo.rest.mapper

import com.primer.demo.rest.input.AddCardInput
import com.primer.demo.rest.output.CardTokenOutput
import com.primer.demo.model.Card
import com.primer.demo.model.CardToken

fun toCardModel(addCard: AddCardInput) = Card(
    merchantId = addCard.merchantId,
    cardHolderName = addCard.cardHolderName,
    number = addCard.number,
    expirationMonth = addCard.expirationMonth,
    expirationYear = addCard.expirationYear,
    cvv = addCard.cvv
)

fun toCardTokenOutput(cardToken: CardToken) = CardTokenOutput(
    token = cardToken.token
)
