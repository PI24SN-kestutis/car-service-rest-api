package lt.viko.eif.kskrebe.carservice.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import lt.viko.eif.kskrebe.carservice.CarServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = CarServiceApplication.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}