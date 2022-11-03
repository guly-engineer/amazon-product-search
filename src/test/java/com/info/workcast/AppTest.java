package com.info.workcast;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        features = {"classpath:features"},
        plugin = {"pretty",
                "html:test-reports/cucumber-reports/index.html",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        glue = {"com.info.workcast"},
        publish = true)
public class AppTest {
}
