package com.bluenile.scrabble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * This application starts a web service that returns Scrabble suggestions for
 * a given set of letters. The highest-scoring words are listed first. For
 * example, an HTTP GET request to http://local.bluenile.com:18080/words/hat
 * returns:
 *
 * <pre>
 * [
 *   "hat",
 *   "ah",
 *   "ha",
 *   "th",
 *   "at",
 *   "a"
 * ]
 * </pre>
 **/
@SpringBootApplication
@EnableSwagger2
public class ScrabbleMain {

  public static void main(String[] args) {
    SpringApplication.run(ScrabbleMain.class, args);
  }

  @Bean
  public Docket swaggerConfiguration() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .paths(PathSelectors.ant("/words/*"))
            .apis(RequestHandlerSelectors.basePackage("com.bluenile.scrabble"))
            .build()
            .apiInfo(apiInfoDetails())
            .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfoDetails() {
    return new ApiInfo(
            "Scrabble solver service",
            "Scrabble service API",
            "1,0",
            "All rights reserved",
            new springfox.documentation.service.Contact("Juan De La Mora", "https://github.com/JuanDeLaMora", "JuanDeLaMora@outlook.com"),
            "API License",
            "https://github.com/JuanDeLaMora",
            Collections.emptyList()
    );
  }
}
