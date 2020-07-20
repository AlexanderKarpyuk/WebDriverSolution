package ru.lanit.webdriver;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        glue = "ru.lanit.webdriver",
        tags = "@test",
        features = "src/test/resources/features"
)
public class RunnerTest extends AbstractTestNGCucumberTests {
}
