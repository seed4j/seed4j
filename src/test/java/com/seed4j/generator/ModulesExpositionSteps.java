package com.seed4j.generator;

import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;

public class ModulesExpositionSteps {

  @Autowired
  private TestRestTemplate rest;

  @When("I get modules list")
  public void listModules() {
    rest.getForEntity("/api/modules", Void.class);
  }

  @Then("I should have category {string} with module")
  public void shouldHaveModule(String category, Map<String, String> module) {
    assertThatLastResponse()
      .hasOkStatus()
      .hasElement("$.categories[?(@.name=='" + category + "')].modules[?(@.slug=='" + module.get("Slug") + "')].description")
      .withValues(module.get("Description"));
  }
}
