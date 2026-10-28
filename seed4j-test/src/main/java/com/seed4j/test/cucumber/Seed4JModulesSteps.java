package com.seed4j.test.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.module.application.Seed4JModulesApplicationService;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JModuleToApply;
import com.seed4j.module.domain.Seed4JModulesToApply;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;

/**
 * Steps applying modules to a project.
 */
public class Seed4JModulesSteps {

  private static final Map<String, Object> DEFAULT_PARAMETERS = Map.of(
    "projectName",
    "Growth Project",
    "baseName",
    "growth",
    "packageName",
    "com.seed4j.growth",
    "serverPort",
    8080
  );

  private final Seed4JModulesApplicationService modules;
  private @Nullable Seed4JModulePropertiesDefinition propertiesDefinition;

  public Seed4JModulesSteps(Seed4JModulesApplicationService modules) {
    this.modules = modules;
  }

  @When("I apply modules to default project")
  public void applyModulesToDefaultProject(List<String> modulesSlugs) {
    modules.apply(
      new Seed4JModulesToApply(
        modulesSlugs.stream().map(Seed4JModuleSlug::new).toList(),
        new Seed4JModuleProperties(TestProject.newFolder(), false, DEFAULT_PARAMETERS)
      )
    );
  }

  @When("I apply {string} module to default project")
  public void applyModuleToDefaultProject(String moduleSlug, Map<String, String> parameters) {
    apply(TestProject.newFolder(), parameters, moduleSlug);
  }

  @When("I apply {string} module to default project without parameters")
  public void applyModuleToDefaultProjectWithoutParameters(String moduleSlug) {
    apply(TestProject.newFolder(), Map.of(), moduleSlug);
  }

  @When("I apply {string} module to default project with package json")
  public void applyModuleToDefaultProjectWithPackageJson(String moduleSlug, Map<String, String> parameters) {
    apply(TestProject.newFolder(), parameters, "init", moduleSlug);
  }

  @When("I apply {string} module to default project with package json without parameters")
  public void applyModuleToDefaultProjectWithPackageJsonWithoutParameters(String moduleSlug) {
    apply(TestProject.newFolder(), Map.of(), "init", moduleSlug);
  }

  @When("I apply {string} module to default project with maven file")
  public void applyModuleToDefaultProjectWithMavenFile(String moduleSlug, Map<String, String> parameters) {
    apply(TestProject.newFolder(), parameters, "init", "maven-java", moduleSlug);
  }

  @When("I apply {string} module to default project with maven file without parameters")
  public void applyModuleToDefaultProjectWithMavenFileWithoutParameters(String moduleSlug) {
    apply(TestProject.newFolder(), Map.of(), "init", "maven-java", moduleSlug);
  }

  @When("I apply {string} module to default project with gradle build")
  public void applyModuleToDefaultProjectWithGradleBuild(String moduleSlug, Map<String, String> parameters) {
    apply(TestProject.newFolder(), parameters, "init", "gradle-java", moduleSlug);
  }

  @When("I apply {string} module with parameters to last project")
  public void applyModuleWithParametersToLastProject(String moduleSlug, Map<String, String> parameters) {
    apply(TestProject.folder(), parameters, moduleSlug);
  }

  @When("I apply {string} module without parameters to last project")
  public void applyModuleWithoutParametersToLastProject(String moduleSlug) {
    apply(TestProject.folder(), Map.of(), moduleSlug);
  }

  @When("I get module {string} properties definition")
  public void getModulePropertiesDefinition(String moduleSlug) {
    propertiesDefinition = modules.resources().get(new Seed4JModuleSlug(moduleSlug)).propertiesDefinition();
  }

  @Then("I should have properties definitions")
  public void shouldHavePropertiesDefinitions(List<Map<String, String>> expectedDefinitions) {
    Seed4JModulePropertiesDefinition readDefinition = propertiesDefinition;
    if (readDefinition == null) {
      throw new IllegalStateException("No module properties definition read yet");
    }

    List<Map<String, String>> definitions = readDefinition
      .stream()
      .map(definition ->
        Map.of("Key", definition.key().get(), "Type", definition.type().name(), "Mandatory", String.valueOf(definition.isMandatory()))
      )
      .toList();

    assertThat(definitions).containsExactlyElementsOf(expectedDefinitions);
  }

  private void apply(String projectFolder, Map<String, String> parameters, String... modulesSlugs) {
    Seed4JModuleProperties properties = new Seed4JModuleProperties(projectFolder, false, toParameters(parameters));

    for (String moduleSlug : modulesSlugs) {
      modules.apply(new Seed4JModuleToApply(new Seed4JModuleSlug(moduleSlug), properties));
    }
  }

  private static Map<String, Object> toParameters(Map<String, String> parameters) {
    Map<String, Object> result = new LinkedHashMap<>();

    parameters.forEach((key, value) -> result.put(key, toParameter(value)));

    return result;
  }

  private static Object toParameter(String value) {
    if ("true".equals(value) || "false".equals(value)) {
      return Boolean.valueOf(value);
    }

    if (StringUtils.isNumeric(value)) {
      return Integer.valueOf(value);
    }

    return value;
  }
}
