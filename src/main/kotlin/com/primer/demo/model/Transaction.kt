package com.primer.demo.model

import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

data class Transaction (
    val id: UUID? = null,
    val processorTransactionId: String,
    val token: String,
    val merchantId: UUID,
    val amount: BigDecimal,
    val taxAmount: BigDecimal,
    val currency: Currency,
    val processorType: ProcessorType,
    val type: TransactionType,
    val paymentMethod: TransactionPaymentMethod,
    val createdAt: Long
)

enum class TransactionType {
    SALE
}

enum class TransactionPaymentMethod{
    TEST
}

