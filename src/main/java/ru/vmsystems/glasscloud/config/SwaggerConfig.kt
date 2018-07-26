package ru.vmsystems.glasscloud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

//http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return buildDocket("/api")
    }

    private fun buildDocket(serviceUrl: String): Docket {

        val docket = Docket(DocumentationType.SWAGGER_2)
                .groupName("$serviceUrl/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("$serviceUrl.*"))
                .build()
                .apiInfo(metadata())

        val requestParams = ArrayList<Parameter>()

        docket.globalOperationParameters(requestParams)
        return docket
    }

    private fun metadata(): ApiInfo {
        return ApiInfoBuilder()
                .title(TITLE_FOR_API)
                .description(DESCRIPTION_FOR_API)
                .version(VERSION)
                .contact(CONTACT)
                .build()
    }

    companion object {

        private const val TITLE_FOR_API = "title"
        private const val VERSION = "1.0"
        private const val DESCRIPTION_FOR_API = "description"
        private val CONTACT = Contact("admin", "vmsystems.ru", "???@vmsystems.ru")
    }
}
