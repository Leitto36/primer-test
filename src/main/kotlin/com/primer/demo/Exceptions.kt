package com.primer.demo

import java.lang.RuntimeException

class SqlOperationFailedException(message: String) : RuntimeException(message)

class UnknownPaymentProcessorException(message: String) : RuntimeException(message)

class UnsuccessfulProcessorResponseException(message: String) : RuntimeException(message)
