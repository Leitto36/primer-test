package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.service.CardService
import com.primer.demo.service.CardTokenService
import com.primer.demo.service.MerchantService
import org.springframework.stereotype.Service

@Service
class CardServiceImpl(
    private val cardTokenService: CardTokenService,
    private val merchantService: MerchantService
) : CardService {

    override fun addCard(card: Card): CardToken = cardTokenService.createToken(
        card = card,
        merchant = merchantService.getMerchant(card.merchantId)
    )
}
