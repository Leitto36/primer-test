package com.primer.demo.service.impl

import com.primer.demo.model.Card
import com.primer.demo.model.CardToken
import com.primer.demo.repository.CardRepository
import com.primer.demo.service.CardService
import com.primer.demo.service.CardTokenService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CardServiceImpl(
    private val cardRepository: CardRepository,
    private val cardTokenService: CardTokenService
) : CardService {

    @Transactional
    override fun addCard(card: Card): CardToken = cardTokenService.createToken(card).let {
        cardRepository.create(card)
        it
    }
}
