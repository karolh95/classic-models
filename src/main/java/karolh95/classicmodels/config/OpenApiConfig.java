package karolh95.classicmodels.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Classic Models Application API")
                .description("Spring Boot application for a retailer of scale models of classic cars.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
