package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.service.CardService
import com.primer.demo.service.CardTokenService
import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cardTokenService: CardTokenService
) : CardService {

    override fun addCard(card: Card): CardToken = cardTokenService.createToken(card)
}
