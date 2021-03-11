package com.primer.demo.rest.input

import java.util.UUID

data class AddCardInput(
    val merchantId: UUID,
    val cardHolderName: String,
    val number: String,
    val expirationMonth: String,
    val expirationYear: String,
    val cvv: String
)
