package com.primer.demo.rest.mapper

import com.primer.demo.rest.input.CreateTransactionInput
import com.primer.demo.rest.output.CreateTransactionOutput
import com.primer.demo.rest.output.CreateTransactionOutputType
import com.primer.demo.model.Transaction
import com.primer.demo.model.TransactionType
import java.util.UUID

fun toTransaction(input: CreateTransactionInput, merchantId: UUID) : Transaction = Transaction(
    token = input.token,
    merchantId = merchantId,
    amount = input.amount,
    currency = input.currency,
    type =  TransactionType.valueOf(input.type.name)
)

fun toCreateTransactionOutput(transaction: Transaction) : CreateTransactionOutput = CreateTransactionOutput(
    id = transaction.processorTransactionId!!,
    token = transaction.token,
    amount = transaction.amount,
    currency = transaction.currency,
    type = CreateTransactionOutputType.valueOf(transaction.type.name),
    createdAt = transaction.createdAt!!
)
