package md.jack.web.config;

import md.jack.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(ant("/api/**"))
                .build()
                .securitySchemes(singletonList(apiKey()))
                .securityContexts(singletonList(securityContext()));
    }

    private ApiKey apiKey()
    {
        return new ApiKey(Constants.Http.Header.TOKEN_HEADER_API_KEY, "api_key", "header");
    }

    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(singletonList(securityReference()))
                .forPaths(ant("/api/**"))
                .build();
    }

    private SecurityReference securityReference()
    {
        return new SecurityReference(
                Constants.Http.Header.TOKEN_HEADER_API_KEY,
                Stream.of(new AuthorizationScope("global", "accessEverything"))
                        .toArray(AuthorizationScope[]::new)
        );
    }
}
