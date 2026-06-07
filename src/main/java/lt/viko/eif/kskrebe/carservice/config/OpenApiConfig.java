package lt.viko.eif.kskrebe.carservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Service REST API")
                        .version("1.0")
                        .description(
                                "Automobilių serviso valdymo sistema")
                        .contact(new Contact()
                                .name("Kęstutis Skrebė")
                                .email("kestutis.skrebe@stud.viko.lt")));
    }
}
