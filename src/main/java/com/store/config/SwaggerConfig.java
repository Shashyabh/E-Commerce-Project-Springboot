package com.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayDeque;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        return docket;
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Backend Apis",
                "This is backend project created by Shashyabh Ray",
                "1.0.0V",
                "www.leetcode.code/shashyabhray",
                new Contact("Shashyabh","www.leetcode.code/shashyabhray","shashyabhray@gmail.com"),
                "Licence of APIs",
                "www.leetcode.code/shashyabhray",
                new ArrayDeque<>()
        );
        return apiInfo;
    }
}
