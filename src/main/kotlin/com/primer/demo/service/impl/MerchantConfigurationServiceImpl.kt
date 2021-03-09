package com.primer.demo.service.impl

import com.primer.demo.model.MerchantConfiguration
import com.primer.demo.model.MerchantConfigurationStatus
import com.primer.demo.model.ProcessorType
import com.primer.demo.service.MerchantConfigurationService
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class MerchantConfigurationServiceImpl : MerchantConfigurationService {

    override fun getMerchantConfiguration(merchantId: UUID) : MerchantConfiguration = MerchantConfiguration(
        id = UUID.randomUUID(),
        merchantId = merchantId,
        processorType = ProcessorType.BRAIN_TREE,
        processorMerchantId = UUID.randomUUID().toString(),
        publicKey = UUID.randomUUID().toString(),
        privateKey = UUID.randomUUID().toString(),
        status = MerchantConfigurationStatus.ACTIVE
    )
}
