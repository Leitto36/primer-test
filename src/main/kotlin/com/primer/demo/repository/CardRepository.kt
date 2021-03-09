package com.primer.demo.repository

import com.primer.demo.model.Card

interface CardRepository {

    fun create(card: Card) : Card
}
