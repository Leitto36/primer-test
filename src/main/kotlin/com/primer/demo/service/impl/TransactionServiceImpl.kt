package com.primer.demo.service.impl

import com.primer.demo.model.Transaction
import com.primer.demo.payment.PaymentProcessorFactory
import com.primer.demo.service.TransactionService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val paymentProcessorFactory: PaymentProcessorFactory
) : TransactionService {

    override fun create(transaction: Transaction): Transaction =
        paymentProcessorFactory.getPaymentProcessor(transaction.merchantId).createTransaction(transaction)
}
