package com.test.app.languageapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * spring boot main application class
 */
@SpringBootApplication
@EnableSwagger2
public class LanguageMatchApplication {

    public static void main(String[] args) {


        SpringApplication.run(LanguageMatchApplication.class, args);
    }

}
