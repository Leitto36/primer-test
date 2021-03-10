package com.primer.demo.test.config

import com.braintreegateway.BraintreeGateway
import com.nhaarman.mockitokotlin2.mock
import com.primer.demo.config.processor.BrainTreeProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("test")
@Configuration
@EnableConfigurationProperties(BrainTreeProperties::class)
class BrainTreeConfig {

    @Bean
    fun brainTreeGateway(properties: BrainTreeProperties) =
        mock<BraintreeGateway>()
}
