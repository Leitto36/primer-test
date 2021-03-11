package com.primer.demo.config

import java.util.Locale
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@Profile("!test")
class SwaggerConfig {

    @Bean
    fun documentation(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
            .paths(PathSelectors.any())
            .build()
            .ignoredParameterTypes(Locale::class.java)

    private fun apiInfo(): ApiInfo =
        ApiInfoBuilder()
            .title("Primer Demo")
            .description("2 endpoints app")
            .version("1.0")
            .build()
}
