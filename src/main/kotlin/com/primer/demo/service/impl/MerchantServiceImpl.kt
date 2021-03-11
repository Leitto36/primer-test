package com.primer.demo.service.impl

import com.primer.demo.model.Merchant
import com.primer.demo.service.MerchantService
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class MerchantServiceImpl : MerchantService {

    override fun getMerchant(merchantId: UUID): Merchant = Merchant(
        id = merchantId,
        firstName = "John",
        lastName = "Doe",
        company = "Beer&Grill",
        email = "john.doe@gmail.com",
        phone = "+44 20 8759 9036",
        website = "http://google.com/"
    )
}
