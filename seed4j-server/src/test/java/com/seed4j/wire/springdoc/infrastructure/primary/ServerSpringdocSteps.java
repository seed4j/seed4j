package com.seed4j.wire.springdoc.infrastructure.primary;

import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.client.RestTestClient;

public class ServerSpringdocSteps {

  @Autowired
  private RestTestClient rest;

  @When("I get api documentation")
  public void getApiDocumentation() {
    rest.get().uri("/v3/api-docs/all").exchange();
  }

  @Then("I should have schema for {string}")
  public void shouldHaveSchema(String schema) {
    assertThatLastResponse()
      .hasOkStatus()
      .hasElement("$.components.schemas." + schema + ".description")
      .withValue("Definitions for properties in this module");
  }
}
