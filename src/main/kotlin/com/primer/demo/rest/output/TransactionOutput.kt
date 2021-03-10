package com.primer.demo.rest.output

import java.math.BigDecimal
import java.util.Currency

data class CreateTransactionOutput(
    val id: String,
    val token: String,
    val amount: BigDecimal,
    val currency: Currency,
    val type: String,
    val createdAt: Long
)
