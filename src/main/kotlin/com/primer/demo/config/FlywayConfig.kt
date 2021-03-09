package com.primer.demo.config

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!test")
@Configuration
class FlywayConfig {

    @Bean
    fun validateOnlyStrategy() = FlywayMigrationStrategy { it.validate() }
}
