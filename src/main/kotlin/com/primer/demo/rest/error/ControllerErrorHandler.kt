package com.primer.demo.rest.error

import com.primer.demo.InvalidCreateCardProcessorResponseException
import com.primer.demo.InvalidCustomerProcessorResponseException
import com.primer.demo.InvalidTransactionProcessorResponseException
import com.primer.demo.UnknownPaymentProcessorException
import com.primer.demo.util.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackages = ["com.primer.demo.rest"])
class ControllerErrorHandler {

    @ExceptionHandler(InvalidCustomerProcessorResponseException::class)
    fun handleInvalidCustomerProcessorResponseException(e: InvalidCustomerProcessorResponseException) =
        errorHandler("process='controller_error_handler', result='invalid_merchant_details'", e)

    @ExceptionHandler(InvalidTransactionProcessorResponseException::class)
    fun handleInvalidTransactionProcessorResponseException(e: InvalidTransactionProcessorResponseException) =
        defaultHandler("process='controller_error_handler', result='invalid_transaction'", e)

    @ExceptionHandler(InvalidCreateCardProcessorResponseException::class)
    fun handleInvalidCreateCardProcessorResponseException(e: InvalidCreateCardProcessorResponseException) =
        defaultHandler("process='controller_error_handler', result='invalid_card_details'", e)

    @ExceptionHandler(UnknownPaymentProcessorException::class)
    fun handleUnknownPaymentProcessorException(e: UnknownPaymentProcessorException): ResponseEntity<Any> =
        errorHandler("process='controller_error_handler', result='missed_payment_processor'", e)

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): ResponseEntity<Any> =
        errorHandler("process='controller_error_handler', result='unknown_error'", e)

    private fun defaultHandler(
        result: String,
        e: Exception,
        status: HttpStatus = HttpStatus.BAD_REQUEST
    ): ResponseEntity<Any> {
        log.info("process='controller_error_handler', result='$result', message='{}'", e.message)
        return ResponseEntity.status(status).build()
    }

    private fun errorHandler(result: String, e: Throwable) : ResponseEntity<Any> {
        log.error(result, e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}
