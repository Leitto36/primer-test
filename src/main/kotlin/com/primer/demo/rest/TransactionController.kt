package com.primer.demo.rest

import com.primer.demo.rest.input.CreateTransactionInput
import com.primer.demo.rest.mapper.toCreateTransactionOutput
import com.primer.demo.rest.mapper.toTransaction
import com.primer.demo.rest.output.CreateTransactionOutput
import com.primer.demo.service.TransactionService
import com.primer.demo.util.TRANSACTION_URL
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [TRANSACTION_URL])
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createTransactionInput: CreateTransactionInput) : CreateTransactionOutput = toCreateTransactionOutput(
        transactionService.create(
            toTransaction(input = createTransactionInput, merchantId = UUID.randomUUID())
        )
    )
}
