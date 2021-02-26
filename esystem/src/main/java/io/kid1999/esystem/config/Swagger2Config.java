package io.kid1999.esystem.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author kid1999
 * @create 2020-10-01 10:58
 * @description swagger2 文档 配置
 **/
@Configuration
@EnableOpenApi
public class Swagger2Config {
    @Value("${swagger2.enable}") private boolean enable;

    @Bean("allApis")
    public Docket customApis() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("所有模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("物物交换平台接口文档")
                .description("提供各子模块的文档")
                .termsOfServiceUrl("https://github.com/kid1999/ExchangeSystem")
                .contact(new Contact("kid1999", "https://kid1999.github.io/", "kid.1447250889@live.com"))
                .version("0.1")
                .build();
    }
}