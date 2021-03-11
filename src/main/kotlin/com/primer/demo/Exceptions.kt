package com.primer.demo

import java.lang.RuntimeException

class UnknownPaymentProcessorException(message: String) : RuntimeException(message)

class InvalidCreateCardProcessorResponseException(message: String) : RuntimeException(message)

class InvalidTransactionProcessorResponseException(message: String) : RuntimeException(message)

class InvalidCustomerProcessorResponseException(message: String) : RuntimeException(message)
