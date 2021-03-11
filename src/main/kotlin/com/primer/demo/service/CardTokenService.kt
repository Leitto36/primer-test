package com.primer.demo.service

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.model.Merchant

interface CardTokenService {

    fun createToken(card: Card, merchant: Merchant) : CardToken
}
