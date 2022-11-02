package com.info.workcast;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @ignore",
        features = {"classpath:features"},
        glue = {"com.info.workcast"},
        publish = true)
public class AppTest {
}
