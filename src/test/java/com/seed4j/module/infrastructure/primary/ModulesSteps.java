package com.seed4j.module.infrastructure.primary;

import static com.seed4j.TestProjects.*;
import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.cucumber.rest.CucumberRestTestContext;
import com.seed4j.module.infrastructure.secondary.git.GitTestUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

public class ModulesSteps {

  private static final String MODULE_APPLICATION_TEMPLATE = """
    {
    "projectFolder": "{PROJECT_FOLDER}",
    "parameters": {{ PARAMETERS }}
    }
    """;

  private static final String MODULE_APPLY_AND_COMMIT_TEMPLATE = """
    {
    "projectFolder": "{PROJECT_FOLDER}",
    "commit": true,
    "parameters": {{ PARAMETERS }}
    }
    """;

  private static final String DEFAULT_MODULES_PROPERTIES_TEMPLATE = """
    {
      "modules": [{MODULES}],
      "properties":
        {
          "projectFolder": "{PROJECT_FOLDER}",
          "parameters": {
            "projectName": "Growth Project",
            "baseName": "growth",
            "packageName": "com.seed4j.growth",
            "serverPort": 8080
          }
        }
    }
    """;

  private final TestRestTemplate rest;

  public ModulesSteps(TestRestTemplate rest) {
    this.rest = rest;
  }

  @When("I apply modules to default project")
  public void applyModulesForDefaultProject(List<String> modulesSlugs) {
    String projectFolder = newTestFolder();

    String query = DEFAULT_MODULES_PROPERTIES_TEMPLATE.replace("{PROJECT_FOLDER}", projectFolder).replace(
      "{MODULES}",
      buildModulesList(modulesSlugs)
    );

    post("/api/apply-patches", query);
  }

  private String buildModulesList(List<String> modulesSlugs) {
    return modulesSlugs
      .stream()
      .map(slug -> "\"" + slug + "\"")
      .collect(Collectors.joining(","));
  }

  @When("I get module {string} properties definition")
  public void getModulePropertiesDefinition(String moduleSlug) {
    rest.getForEntity(moduleUrl(moduleSlug), Void.class);
  }

  @When("I apply {string} module to default project with package json")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug, Map<String, String> parameters) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl("init"), buildModuleQuery(projectFolder, parameters));

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  @When("I apply {string} module to default project with package json without parameters")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug) {
    applyModuleForDefaultProjectWithPackageJson(moduleSlug, null);
  }

  @When("I apply {string} module without parameters to last project")
  public void applyModuleForLastProject(String moduleSlug) {
    post(applyModuleUrl(moduleSlug), buildModuleQuery(lastProjectFolder(), null));
  }

  @When("I apply {string} module with parameters to last project")
  public void applyModuleForLastProject(String moduleSlug, Map<String, String> parameters) {
    post(applyModuleUrl(moduleSlug), buildModuleQuery(lastProjectFolder(), parameters));
  }

  @When("I apply {string} module to default project with maven file without parameters")
  public void applyModuleForDefaultProjectWithMavenFileWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProjectWithMavenFile(moduleSlug, null);
  }

  @When("I apply {string} module to default project with maven file")
  public void applyModuleForDefaultProjectWithMavenFile(String moduleSlug, Map<String, String> parameters) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl("init"), buildModuleQuery(projectFolder, parameters));
    post(applyModuleUrl("maven-java"), buildModuleQuery(projectFolder, parameters));

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  @When("I apply {string} module to default project with gradle build")
  public void applyModuleForDefaultProjectWithGradle(String moduleSlug, Map<String, String> parameters) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl("init"), buildModuleQuery(projectFolder, parameters));
    post(applyModuleUrl("gradle-java"), buildModuleQuery(projectFolder, parameters));

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  @When("I apply {string} module to default project without parameters")
  public void applyModuleForDefaultProjectWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProject(moduleSlug, null);
  }

  @When("I apply and commit {string} module to default project")
  public void applyAndCommitModuleForDefaultProject(String moduleSlug, Map<String, String> parameters) throws IOException {
    String projectFolder = newTestFolder();

    Path projectPath = Path.of(projectFolder);
    Files.createDirectories(projectPath);

    loadGitConfig(projectPath);

    String query = MODULE_APPLY_AND_COMMIT_TEMPLATE.replace("{PROJECT_FOLDER}", projectFolder).replace(
      "{{ PARAMETERS }}",
      buildModuleParameters(parameters)
    );

    post(applyModuleUrl(moduleSlug), query);
  }

  @When("I get modules landscape")
  public void getModuleLandscape() {
    rest.getForEntity("/api/modules-landscape", Void.class);
  }

  private void loadGitConfig(Path project) {
    GitTestUtil.execute(project, "init");
    GitTestUtil.execute(project, "config", "init.defaultBranch", "main");
    GitTestUtil.execute(project, "config", "user.email", "\"test@jhipster.com\"");
    GitTestUtil.execute(project, "config", "user.name", "\"Test\"");
  }

  @When("I apply {string} module to default project")
  public void applyModuleForDefaultProject(String moduleSlug, Map<String, String> parameters) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  private String applyModuleUrl(String moduleSlug) {
    return moduleUrl(moduleSlug) + "/apply-patch";
  }

  private String moduleUrl(String moduleSlug) {
    return "/api/modules/" + moduleSlug;
  }

  private String buildModuleQuery(String projectFolder, Map<String, String> parameters) {
    return MODULE_APPLICATION_TEMPLATE.replace("{PROJECT_FOLDER}", projectFolder).replace(
      "{{ PARAMETERS }}",
      buildModuleParameters(parameters)
    );
  }

  private String buildModuleParameters(Map<String, String> parameters) {
    if (parameters == null) {
      return "null";
    }

    return parameters
      .entrySet()
      .stream()
      .map(entry -> "\"" + entry.getKey() + "\":" + buildParameterValue(entry.getValue()))
      .collect(Collectors.joining(",", "{", "}"));
  }

  private String buildParameterValue(String value) {
    if (value == null) {
      return "null";
    }

    if ("true".equals(value)) {
      return "true";
    }

    if ("false".equals(value)) {
      return "false";
    }

    if (StringUtils.isNumeric(value)) {
      return value;
    }

    return "\"" + value + "\"";
  }

  private void post(String uri, String content) {
    rest.exchange(uri, HttpMethod.POST, new HttpEntity<>(content, jsonHeaders()), Void.class);
  }

  private HttpHeaders jsonHeaders() {
    var headers = new HttpHeaders();

    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  @Then("I should have files in {string}")
  public void shouldHaveFiles(String basePath, List<String> files) {
    assertThatLastResponse().hasOkStatus();

    SoftAssertions assertions = new SoftAssertions();

    files
      .stream()
      .map(file -> Path.of(lastProjectFolder(), basePath, file))
      .forEach(assertFileExist(assertions));

    assertions.assertAll();
  }

  private Consumer<Path> assertFileExist(SoftAssertions assertions) {
    return path -> assertions.assertThat(Files.exists(path)).as(fileNotFoundMessage(path)).isTrue();
  }

  private Supplier<String> fileNotFoundMessage(Path path) {
    return () -> "Can't find file " + path + " in project folder, found " + projectFiles();
  }

  @Then("I should not have files in {string}")
  public void shouldNotHaveFiles(String basePath, List<String> files) {
    assertThatLastResponse().hasHttpStatusIn(200, 201);

    SoftAssertions assertions = new SoftAssertions();

    files
      .stream()
      .map(file -> Path.of(lastProjectFolder(), basePath, file))
      .forEach(assertFileNotExist(assertions));

    assertions.assertAll();
  }

  private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
    return path -> assertions.assertThat(!Files.exists(path)).as(fileFoundMessage(path)).isTrue();
  }

  private Supplier<String> fileFoundMessage(Path path) {
    return () -> "Can find file " + path + " in project folder, found " + projectFiles();
  }

  private String projectFiles() {
    try (Stream<Path> files = Files.walk(Path.of(lastProjectFolder()))) {
      return files.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.joining(", "));
    } catch (IOException e) {
      return "unreadable folder";
    }
  }

  @Then("I should have properties definitions")
  public void shouldHaveModulePropertiesDefinitions(List<Map<String, Object>> propertiesDefinition) {
    assertThatLastResponse().hasOkStatus().hasElement("$.definitions").containingExactly(propertiesDefinition);
  }

  @Then("I should have landscape level {int} with elements")
  public void shouldHaveLandscapeLevelElements(int level, List<Map<String, String>> elements) {
    assertThatLastResponse().hasOkStatus();

    elements.forEach(element -> {
      JSONArray types = (JSONArray) CucumberRestTestContext.getElement(
        "$.levels[" + level + "].elements[?(@.slug=='" + element.get("Slug") + "')].type"
      );

      assertThat(types.getFirst()).isEqualTo(element.get("Type"));
    });
  }

  @Then("I should have {int} file in {string}")
  @Then("I should have {int} files in {string}")
  public void shouldHaveFilesCountInDirectory(int filesCount, String directory) throws IOException {
    assertThatLastResponse().hasOkStatus();

    try (Stream<Path> files = Files.list(Path.of(lastProjectFolder(), directory))) {
      assertThat(files.count()).isEqualTo(filesCount);
    }
  }

  @Then("I should have unknown slug {string} error message")
  public void shouldHaveUnknownSlugErrorMessage(String slugName) {
    assertThatLastResponse().hasHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @When("I get the presets definition")
  public void getPresetsDefinition() {
    rest.exchange("/api/presets", HttpMethod.GET, new HttpEntity<>(jsonHeaders()), Void.class);
  }

  @Then("I should have preset names")
  public void shouldHavePresetName(List<String> names) {
    assertThatLastResponse().hasOkStatus().hasElement("$.presets.*.name").withValues(names);
  }

  @And("I should have preset modules")
  public void shouldHavePresetModules(List<String> modules) {
    assertThatLastResponse().hasOkStatus().hasElement("$.presets.*.modules.*.slug").withValues(modules);
  }
}
