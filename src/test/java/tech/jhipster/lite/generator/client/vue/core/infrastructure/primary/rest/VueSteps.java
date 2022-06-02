package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary.rest;

import io.cucumber.java.en.When;
import tech.jhipster.lite.generator.ModulesSteps;

public class VueSteps extends ModulesSteps {

  @When("I generate vue application")
  public void addVue() {
    applyModuleForDefaultProject("/api/clients/vue");
  }

  @When("I add jwt to vue application")
  public void addVueJwt() {
    applyModuleForDefaultProject("/api/clients/vue/jwt");
  }
}
