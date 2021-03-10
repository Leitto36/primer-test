package com.primer.demo.config.processor

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.Environment
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@ConfigurationProperties(prefix = "processor.braintree")
class BrainTreeProperties {

     lateinit var merchantId: String

     lateinit var publicKey: String

     lateinit var privateKey: String
}

@Profile("!test")
@Configuration
@EnableConfigurationProperties(BrainTreeProperties::class)
class BrainTreeConfig {

     @Bean
     fun brainTreeGateway(properties: BrainTreeProperties) = BraintreeGateway(
          Environment.SANDBOX,
          properties.merchantId,
          properties.publicKey,
          properties.privateKey
     )
}
