package com.primer.demo.controller.input

data class AddCardInput(
    val cardHolderName: String,
    val number: String,
    val expirationMonth: Short,
    val expirationYear: Short,
    val cvv: Short
)
