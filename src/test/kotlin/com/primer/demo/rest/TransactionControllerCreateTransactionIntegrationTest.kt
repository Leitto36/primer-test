package com.primer.demo.rest

import com.jayway.restassured.RestAssured
import com.jayway.restassured.http.ContentType
import com.primer.demo.AbstractIntegrationTest
import com.primer.demo.model.ProcessorType
import com.primer.demo.model.Transaction
import com.primer.demo.model.TransactionType
import com.primer.demo.rest.input.CreateTransactionInput
import com.primer.demo.rest.output.CreateTransactionOutput
import com.primer.demo.test.trait.PaymentProcessorMock
import com.primer.demo.test.trait.PaymentProcessorMockTrait
import com.primer.demo.util.TRANSACTION_URL
import java.math.BigDecimal
import java.util.Currency
import java.util.UUID
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TransactionControllerCreateTransactionIntegrationTest(
    paymentProcessorMock: PaymentProcessorMock
) : PaymentProcessorMockTrait by paymentProcessorMock, AbstractIntegrationTest() {

    @Test
    fun `should create transaction` () {
        val transactionId = UUID.randomUUID().toString()
        val token = UUID.randomUUID().toString()

        val createTransactionInput = CreateTransactionInput(
            merchantId = UUID.randomUUID(),
            amount = BigDecimal("100"),
            currency = Currency.getInstance("EUR"),
            type = "SALE",
            token = token,
        )
        val transaction = Transaction(
            token = createTransactionInput.token,
            merchantId = createTransactionInput.merchantId,
            amount = createTransactionInput.amount,
            currency = createTransactionInput.currency,
            type = TransactionType.valueOf(createTransactionInput.type)
        )

        mockProcessorSuccessfulCreateTransaction(
            transaction = transaction,
            processorType = ProcessorType.BRAIN_TREE,
            transactionId = transactionId
        )

        val response = RestAssured
            .given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(createTransactionInput)
            .post(TRANSACTION_URL)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .body()
            .`as`(CreateTransactionOutput::class.java)

        Assertions.assertEquals(transactionId, response.id)
        Assertions.assertEquals(createTransactionInput.token, response.token)
        Assertions.assertEquals(createTransactionInput.amount, response.amount)
        Assertions.assertEquals(createTransactionInput.currency, response.currency)
        Assertions.assertEquals(createTransactionInput.type, response.type)
    }

    @Test
    fun `should return bad request if transaction details were invalid`() {
        val createTransactionInput = CreateTransactionInput(
            merchantId = UUID.randomUUID(),
            amount = BigDecimal("100"),
            currency = Currency.getInstance("EUR"),
            type = "SALE",
            token = UUID.randomUUID().toString(),
        )
        val transaction = Transaction(
            token = createTransactionInput.token,
            merchantId = createTransactionInput.merchantId,
            amount = createTransactionInput.amount,
            currency = createTransactionInput.currency,
            type = TransactionType.valueOf(createTransactionInput.type)
        )

        mockProcessorUnsuccessfulCreateTransaction(transaction = transaction, processorType = ProcessorType.BRAIN_TREE)

        RestAssured
            .given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(createTransactionInput)
            .post(TRANSACTION_URL)
            .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
    }
}
