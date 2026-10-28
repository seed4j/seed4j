package com.seed4j.test.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Starts the seed4j engine, with the modules found on the classpath, for the Cucumber scenarios.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = Seed4JCucumberConfiguration.Seed4JEngine.class, webEnvironment = WebEnvironment.NONE)
public class Seed4JCucumberConfiguration {

  @SpringBootConfiguration
  @EnableAutoConfiguration
  static class Seed4JEngine {}
}
