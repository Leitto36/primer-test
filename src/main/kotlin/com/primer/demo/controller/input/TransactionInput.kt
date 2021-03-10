package com.primer.demo.controller.input

import java.math.BigDecimal
import java.util.Currency

data class CreateTransactionInput(
    val amount: BigDecimal,
    val currency: Currency,
    val type: CreateTransactionInputType,
    val token: String
)

enum class CreateTransactionInputType {
    SALE
}
