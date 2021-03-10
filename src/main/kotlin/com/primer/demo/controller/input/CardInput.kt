package com.primer.demo.controller.input

import java.util.UUID

data class AddCardInput(
    val merchantId: UUID,
    val cardHolderName: String,
    val number: String,
    val expirationMonth: Short,
    val expirationYear: Short,
    val cvv: Short
)
