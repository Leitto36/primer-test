package com.primer.demo.controller.output

import java.math.BigDecimal
import java.util.Currency

data class CreateTransactionOutput(
    val id: String,
    val token: String,
    val amount: BigDecimal,
    val currency: Currency,
    val type: CreateTransactionOutputType,
    val createdAt: Long
)

enum class CreateTransactionOutputType {
    SALE
}
