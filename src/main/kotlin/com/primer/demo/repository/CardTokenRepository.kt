package com.primer.demo.repository

import com.primer.demo.model.CardToken

interface CardTokenRepository {

    fun create(cardToken: CardToken) : CardToken
}
