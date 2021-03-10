package com.primer.demo.payment

import com.braintreegateway.BraintreeGateway
import com.nhaarman.mockitokotlin2.given
import com.primer.demo.service.MerchantConfigurationService
import org.junit.jupiter.api.Test
import com.nhaarman.mockitokotlin2.mock
import com.primer.demo.UnknownPaymentProcessorException
import com.primer.demo.model.ProcessorType
import com.primer.demo.payment.processor.BrainTreeProcessor
import com.primer.demo.util.buildMerchantConfiguration
import java.util.UUID
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat

class PaymentProcessorFactoryImplTest {

    private val merchantConfigurationServiceMock = mock<MerchantConfigurationService>()

    private val braintreeGatewayMock = mock<BraintreeGateway>()

    private val underTest = PaymentProcessorFactoryImpl(
        merchantConfigurationServiceMock,
        braintreeGatewayMock
    )

    @Test
    fun `should return expected payment processor`() {
        val merchantId = UUID.randomUUID()

        given(merchantConfigurationServiceMock.getMerchantConfiguration(merchantId))
            .willReturn(buildMerchantConfiguration(merchantId))

        assertThat(underTest.getPaymentProcessor(merchantId)).isInstanceOf(BrainTreeProcessor::class.java)
    }

    @Test
    fun `should throw an exception if payment processor not found by type`() {
        val merchantId = UUID.randomUUID()

        given(merchantConfigurationServiceMock.getMerchantConfiguration(merchantId))
            .willReturn(buildMerchantConfiguration(merchantId).copy(processorType = ProcessorType.STRIPE))

        Assertions.assertThatThrownBy {underTest.getPaymentProcessor(merchantId)}
            .isInstanceOf(UnknownPaymentProcessorException::class.java)
    }
}
