package com.seed4j.test.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Starts the seed4j engine, with the modules found on the classpath, for the Cucumber scenarios.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = Seed4JCucumberConfiguration.Seed4JEngine.class, webEnvironment = WebEnvironment.NONE)
public class Seed4JCucumberConfiguration {

  @SpringBootApplication(scanBasePackages = { "com.seed4j.module", "com.seed4j.history", "com.seed4j.shared" })
  static class Seed4JEngine {}
}
