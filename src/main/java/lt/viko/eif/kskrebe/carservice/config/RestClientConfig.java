package lt.viko.eif.kskrebe.carservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient meteoRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.meteo.lt/v1")
                .build();
    }
}