package com.primer.demo.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig {

    @Bean
    fun dataSource(
        @Value("\${db.url}") url: String,
        @Value("\${db.username}") username: String,
        @Value("\${db.password}") password: String
    ): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = url
        config.username = username
        config.password = password

        return HikariDataSource(config)
    }
}
