package com.primer.demo.service

import com.primer.demo.model.Transaction

interface TransactionService {

    fun create(transaction: Transaction) : Transaction
}
