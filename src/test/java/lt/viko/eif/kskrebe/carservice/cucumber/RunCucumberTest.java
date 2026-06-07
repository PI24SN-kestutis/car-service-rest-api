package lt.viko.eif.kskrebe.carservice.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;


/**
 * Cucumber testų paleidimo klasė.
 *
 * Paleidžia visus feature failus iš src/test/resources/features.
 * Naudojama kartu su Spring Boot ir H2 testine duomenų baze.
 */

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "lt.viko.eif.kskrebe.carservice.cucumber"
)
public class RunCucumberTest {
}