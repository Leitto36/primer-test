package com.primer.demo.model

import java.util.UUID

data class Merchant(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val company: String,
    val email: String,
    val phone: String,
    val website: String
)

data class MerchantConfiguration(
    val id: UUID? = null,
    val merchantId: UUID,
    val customerId: String?,
    val processorType: ProcessorType,
    val processorMerchantId: String,
    val publicKey: String,
    val privateKey: String,
    val status: MerchantConfigurationStatus
)

enum class MerchantConfigurationStatus {
    ACTIVE, DISABLED
}
