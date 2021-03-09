package com.primer.demo.model

import java.util.UUID

data class MerchantConfiguration(
    val id: UUID? = null,
    val merchantId: UUID,
    val processorType: ProcessorType,
    val processorMerchantId: String,
    val publicKey: String,
    val privateKey: String,
    val status: MerchantConfigurationStatus
)

enum class MerchantConfigurationStatus {
    ACTIVE, DISABLED
}
