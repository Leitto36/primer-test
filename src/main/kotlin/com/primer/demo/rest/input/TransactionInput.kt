package com.primer.demo.rest.input

import java.math.BigDecimal
import java.util.Currency
import java.util.UUID

data class CreateTransactionInput(
    val merchantId: UUID,
    val amount: BigDecimal,
    val currency: Currency,
    val type: String,
    val token: String
)
