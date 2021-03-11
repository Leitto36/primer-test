package com.primer.demo.util

import com.primer.demo.model.Merchant
import com.primer.demo.model.MerchantConfiguration
import com.primer.demo.model.MerchantConfigurationStatus
import com.primer.demo.model.ProcessorType
import java.util.UUID

fun buildMerchantConfiguration(merchantId: UUID, customerId: String? = null) : MerchantConfiguration =
    MerchantConfiguration(
        id = UUID.randomUUID(),
        merchantId = merchantId,
        customerId = customerId,
        processorType = ProcessorType.BRAIN_TREE,
        processorMerchantId = UUID.randomUUID().toString(),
        publicKey = UUID.randomUUID().toString(),
        privateKey = UUID.randomUUID().toString(),
        status = MerchantConfigurationStatus.ACTIVE
    )

fun buildMerchant(merchantId: UUID) : Merchant = Merchant(
    id = merchantId,
    firstName = "John",
    lastName = "Doe",
    company = "Beer&Grill",
    email = "john.doe@gmail.com",
    phone = "+44 20 8759 9036",
    website = "http://google.com/"
)
