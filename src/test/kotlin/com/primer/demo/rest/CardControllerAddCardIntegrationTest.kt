package com.primer.demo.rest

import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import com.primer.demo.AbstractIntegrationTest
import com.primer.demo.rest.input.AddCardInput
import com.primer.demo.model.Card
import com.primer.demo.model.ProcessorType
import com.primer.demo.test.trait.PaymentProcessorMock
import com.primer.demo.test.trait.PaymentProcessorMockTrait
import com.primer.demo.util.CARD_URL
import java.util.UUID
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CardControllerAddCardIntegrationTest(
    paymentProcessorMock: PaymentProcessorMock
) : PaymentProcessorMockTrait by paymentProcessorMock, AbstractIntegrationTest() {

    @Test
    fun `should return card token` () {
        val cardToken = UUID.randomUUID().toString()
        val addCardInput = AddCardInput(
            merchantId = UUID.randomUUID(),
            cardHolderName = "John Doe",
            number = "5555555555554444",
            expirationMonth = 1,
            expirationYear = 2022,
            cvv = 100
        )

        val card = Card(
            merchantId = addCardInput.merchantId,
            cardHolderName = addCardInput.cardHolderName,
            number = addCardInput.number,
            expirationMonth = addCardInput.expirationMonth,
            expirationYear = addCardInput.expirationYear,
            cvv = addCardInput.cvv
        )

        mockProcessorSuccessfulCreateCreditCard(card = card, processorType = ProcessorType.BRAIN_TREE, token = cardToken)

        val response = RestAssured
            .given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(addCardInput)
            .post(CARD_URL)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .body()
            .asString()
            .let { jsonTree(it) }

        val expected = jsonTree(
            // language=JSON
            """
                {
                    "token": "$cardToken"
                }
        """.trimIndent()
        )

        assertEquals(expected, response)
    }

    @Test
    fun `should return bad request if payment processor validation failed` () {
        val addCardInput = AddCardInput(
            merchantId = UUID.randomUUID(),
            cardHolderName = "John Doe",
            number = "5555555555554444",
            expirationMonth = 1,
            expirationYear = 2022,
            cvv = 12345
        )

        val card = Card(
            merchantId = addCardInput.merchantId,
            cardHolderName = addCardInput.cardHolderName,
            number = addCardInput.number,
            expirationMonth = addCardInput.expirationMonth,
            expirationYear = addCardInput.expirationYear,
            cvv = addCardInput.cvv
        )

        mockProcessorUnsuccessfulCreateCreditCard(card = card, processorType = ProcessorType.BRAIN_TREE)

        RestAssured
            .given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(addCardInput)
            .post(CARD_URL)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
    }

}
