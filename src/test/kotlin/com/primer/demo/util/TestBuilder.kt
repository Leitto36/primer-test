package com.primer.demo.util

import com.primer.demo.model.MerchantConfiguration
import com.primer.demo.model.MerchantConfigurationStatus
import com.primer.demo.model.ProcessorType
import java.util.UUID

fun buildMerchantConfiguration(merchantId: UUID) : MerchantConfiguration = MerchantConfiguration(
    id = UUID.randomUUID(),
    merchantId = merchantId,
    processorType = ProcessorType.BRAIN_TREE,
    processorMerchantId = UUID.randomUUID().toString(),
    publicKey = UUID.randomUUID().toString(),
    privateKey = UUID.randomUUID().toString(),
    status = MerchantConfigurationStatus.ACTIVE
)
