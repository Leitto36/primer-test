package com.primer.demo.test.trait

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.CreditCard
import com.braintreegateway.CreditCardGateway
import com.braintreegateway.Result
import com.braintreegateway.ValidationError
import com.braintreegateway.ValidationErrorCode
import com.braintreegateway.ValidationErrors
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.primer.demo.model.Card
import com.primer.demo.model.ProcessorType
import org.springframework.stereotype.Component

@Component
class PaymentProcessorMock(
    private val brainTreeGateway: BraintreeGateway
) : PaymentProcessorMockTrait {

    override fun mockProcessorSuccessfulCreateCreditCard(card: Card, processorType: ProcessorType, token: String) {
         when (processorType) {
            ProcessorType.BRAIN_TREE -> {
                val creditCardGatewayMock = mock<CreditCardGateway>()
                val creditCardMock = mock<CreditCard>()

                given(brainTreeGateway.creditCard()).willReturn(creditCardGatewayMock)
                given(creditCardGatewayMock.create(any())).willReturn(Result(creditCardMock))
                given(creditCardMock.token).willReturn(token)
            }
             else -> Unit
         }
    }

    override fun mockProcessorUnsuccessfulCreateCreditCard(card: Card, processorType: ProcessorType) {
        when (processorType) {
            ProcessorType.BRAIN_TREE -> {
                val creditCardGatewayMock = mock<CreditCardGateway>()
                val errors = ValidationErrors()

                errors.addError(ValidationError("cvv", ValidationErrorCode.CREDIT_CARD_CVV_IS_INVALID, "invalid cvv was supplied"))

                given(brainTreeGateway.creditCard()).willReturn(creditCardGatewayMock)
                given(creditCardGatewayMock.create(any())).willReturn(Result(errors))
            }
            else -> Unit
        }
    }
}

interface PaymentProcessorMockTrait {
    fun mockProcessorSuccessfulCreateCreditCard(card: Card, processorType: ProcessorType, token: String)
    fun mockProcessorUnsuccessfulCreateCreditCard(card: Card, processorType: ProcessorType)
}
