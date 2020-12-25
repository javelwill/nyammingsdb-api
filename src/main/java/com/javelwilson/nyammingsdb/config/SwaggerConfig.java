package com.javelwilson.nyammingsdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Contact contact = new Contact("Javel Wilson", "https://www.javelwilson.com", "hello@javelwilson.com");

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "NyammingsDB RESTful API Documentation",
            "This page provides documentation for NyammingsDB RESTful API endpoints",
            "1.0",
            "http://www.javelwilson.com/service.html",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions
    );

    @Bean
    public Docket internalDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("internal")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.javelwilson.nyammingsdb"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Time.class, String.class)
                .directModelSubstitute(DayOfWeek.class, String.class);
        return docket;
    }

    @Bean
    public Docket externalDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("external")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.javelwilson.nyammingsdb"))
                .paths(PathSelectors.ant("/restaurants/**"))
                .build()
                .directModelSubstitute(Time.class, String.class)
                .directModelSubstitute(DayOfWeek.class, String.class);
        return docket;
    }
}
