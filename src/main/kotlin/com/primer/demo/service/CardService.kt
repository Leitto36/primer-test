package com.primer.demo.service

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken

interface CardService {

    fun addCard(card: Card) : CardToken
}
