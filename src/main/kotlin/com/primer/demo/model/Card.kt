package com.primer.demo.model

import java.util.UUID

data class Card(
    val id: UUID? = null,
    val merchantId: UUID,
    val cardHolderName: String,
    val number: String,
    val expirationMonth: Short,
    val expirationYear: Short,
    val cvv: Short
)

data class CardToken(
    val id: UUID? = null,
    val cardId: UUID? = null,
    val processorType: ProcessorType,
    val token: String,
    val status: CardTokenStatus
)

enum class CardTokenStatus {
    ACTIVE, DISABLED, EXPIRED
}
