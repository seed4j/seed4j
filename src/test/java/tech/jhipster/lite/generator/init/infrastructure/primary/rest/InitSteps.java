package tech.jhipster.lite.generator.init.infrastructure.primary.rest;

import io.cucumber.java.en.When;
import tech.jhipster.lite.generator.ModulesSteps;

public class InitSteps extends ModulesSteps {

  @When("I init default project")
  public void initFull() {
    applyModuleForDefaultProject("/api/inits/full");
  }

  @When("I init minimal default project")
  public void initMinimal() {
    applyModuleForDefaultProject("/api/inits/minimal");
  }
}
