package com.primer.demo.service.impl

import com.primer.demo.model.Transaction
import com.primer.demo.payment.PaymentProcessorFactory
import com.primer.demo.service.MerchantConfigurationService
import com.primer.demo.service.TransactionService
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val paymentProcessorFactory: PaymentProcessorFactory,
    private val merchantConfigurationService: MerchantConfigurationService
) : TransactionService {

    override fun create(transaction: Transaction): Transaction =
        paymentProcessorFactory.getPaymentProcessor(
            merchantConfigurationService.getMerchantConfiguration(transaction.merchantId)
        ).createTransaction(transaction)
}
