package com.aws.hacker.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI AWSOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("AWS API")
                        .description("AWS API 명세서"));
    }

    @Bean
    public GroupedOpenApi exampleGroup(){
        return GroupedOpenApi.builder()
                .group("Example")
                .pathsToMatch("/example/**")
                .build();
    }
}
