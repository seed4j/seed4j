package tech.jhipster.lite.wire.springdoc.infrastructure.primary;

import static tech.jhipster.lite.cucumber.CucumberAssertions.*;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class SpringDocSteps {

  @Autowired
  private TestRestTemplate rest;

  @When("I get api documentation")
  public void getApiDocumentation() {
    rest.getForEntity("/v3/api-docs/all", Void.class);
  }

  @Then("I should have schema for {string}")
  public void shouldHaveSchema(String schema) {
    assertThatLastResponse().hasOkStatus().hasElement("$.components.schemas." + schema + ".type").withValue("object");
  }
}
