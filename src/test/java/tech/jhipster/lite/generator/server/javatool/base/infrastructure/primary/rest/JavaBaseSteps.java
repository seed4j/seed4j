package tech.jhipster.lite.generator.server.javatool.base.infrastructure.primary.rest;

import io.cucumber.java.en.When;
import tech.jhipster.lite.generator.ModulesSteps;

public class JavaBaseSteps extends ModulesSteps {

  @When("I add Java base to default project")
  public void addJavaBase() {
    applyModuleForDefaultProject("/api/servers/java/base");
  }
}
