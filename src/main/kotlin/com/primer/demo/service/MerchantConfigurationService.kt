package com.primer.demo.service

import com.primer.demo.model.MerchantConfiguration
import java.util.UUID

interface MerchantConfigurationService {

    fun getMerchantConfiguration(merchantId: UUID) : MerchantConfiguration
}
