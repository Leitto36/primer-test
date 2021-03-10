package com.primer.demo.rest.mapper

import com.primer.demo.rest.input.CreateTransactionInput
import com.primer.demo.rest.output.CreateTransactionOutput
import com.primer.demo.model.Transaction
import com.primer.demo.model.TransactionType

fun toTransaction(input: CreateTransactionInput) : Transaction = Transaction(
    token = input.token,
    merchantId = input.merchantId,
    amount = input.amount,
    currency = input.currency,
    type =  TransactionType.valueOf(input.type)
)

fun toCreateTransactionOutput(transaction: Transaction) : CreateTransactionOutput = CreateTransactionOutput(
    id = transaction.processorTransactionId!!,
    token = transaction.token,
    amount = transaction.amount,
    currency = transaction.currency,
    type = transaction.type.name,
    createdAt = transaction.createdAt!!
)
