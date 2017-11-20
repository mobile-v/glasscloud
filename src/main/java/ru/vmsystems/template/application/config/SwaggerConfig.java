package ru.vmsystems.template.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

//http://localhost:8080/swagger-ui.html
//http://82.202.226.239:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final static String TITLE_FOR_API = "glass gloud";
    private final static String VERSION = "1.0";
    private final static String DESCRIPTION_FOR_API = "glass gloud";
    private final static Contact CONTACT = new Contact("Vladimir Minikh", "vmsystems.ru", "minikh@mail.ru");

    @Bean
    public Docket apiOrder() {
        return buildDocket("/api/order");
    }

    @Bean
    public Docket apiClient() {
        return buildDocket("/api/client");
    }

    @Bean
    public Docket apiMaterial() {
        return buildDocket("/api/material");
    }

    @Bean
    public Docket apiProcess() {
        return buildDocket("/api/process");
    }

    @Bean
    public Docket apiReceptionOfOrder() {
        return buildDocket("/api/receptionOfOrder");
    }

    private Docket buildDocket(String serviceUrl) {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(serviceUrl + "/")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex(serviceUrl + ".*"))
                .build()
                .apiInfo(metadata());

        List<Parameter> requestParams = new ArrayList<>();
//        Parameter headerParam = new ParameterBuilder()
//                .name("Param")
//                .description("Param")
//                .modelRef(new ModelRef("string"))
//                .defaultValue(param)
//                .parameterType("header")
//                .required(true)
//                .build();
//        requestParams.add(headerParam);
        docket.globalOperationParameters(requestParams);
        return docket;
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title(TITLE_FOR_API)
                .description(DESCRIPTION_FOR_API)
                .version(VERSION)
                .contact(CONTACT)
                .build();
    }
}
