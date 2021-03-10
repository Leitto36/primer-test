package com.primer.demo.model

import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

data class Transaction (
    val id: UUID? = null,
    val processorTransactionId: String? = null,
    val token: String,
    val merchantId: UUID,
    val amount: BigDecimal,
    val currency: Currency,
    val processorType: ProcessorType? = null,
    val type: TransactionType,
    val createdAt: Long? = null
)

enum class TransactionType {
    SALE
}
