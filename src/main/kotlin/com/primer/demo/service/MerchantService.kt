package com.primer.demo.service

import com.primer.demo.model.Merchant
import java.util.UUID

interface MerchantService {
    fun getMerchant(merchantId: UUID) : Merchant
}
