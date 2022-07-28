package TestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)


@CucumberOptions(

        features = "src/test/java/features",
        glue = {"src/test/java/stepdefinitions"} ,
        dryRun =false,
        monochrome = true,
        plugin = {"pretty",
                "html:test-output"}
)
public class testRunner{

        }
