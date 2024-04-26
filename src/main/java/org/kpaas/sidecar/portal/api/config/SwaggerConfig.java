package org.kpaas.sidecar.portal.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration("swagger")
@EnableSwagger2
public class SwaggerConfig extends org.container.platform.api.config.SwaggerConfig {
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json"));

    @Bean
    @Override
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Container Platform API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.container.platform.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);

    }

    @Bean
    public Docket sidecarApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Sidecar Portal API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.kpaas.sidecar.portal.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);

    }

    @Bean
    @Override
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("K-PaaS Sidecar API Docs")
                .version("v1.0")
                .description("This is a API Document created with swagger.")
                .license("Apache2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
