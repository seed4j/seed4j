package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestProjects.*;
import static tech.jhipster.lite.cucumber.CucumberAssertions.*;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import tech.jhipster.lite.cucumber.CucumberTestContext;
import tech.jhipster.lite.module.infrastructure.secondary.git.GitTestUtil;

public class ModulesSteps {

  @Autowired
  private TestRestTemplate rest;

  private static final String MODULE_APPLICATION_TEMPLATE =
    """
      {
      "projectFolder": "{PROJECT_FOLDER}",
      "parameters": {{ PARAMETERS }}
      }
      """;

  private static final String MODULE_APPLY_AND_COMMIT_TEMPLATE =
    """
      {
      "projectFolder": "{PROJECT_FOLDER}",
      "commit": true,
      "parameters": {{ PARAMETERS }}
      }
      """;

  private static final String DEFAULT_MODULES_PROPERTIES_TEMPLATE =
    """
      {
        "modules": [{MODULES}],
        "properties":
          {
            "projectFolder": "{PROJECT_FOLDER}",
            "parameters": {
              "projectName": "Chips Project",
              "baseName": "chips",
              "packageName": "tech.jhipster.chips",
              "serverPort": 8080
            }
          }
      }
      """;

  @When("I apply modules to default project")
  public void applyModulesForDefaultProject(List<String> modulesSlugs) {
    String projectFolder = newTestFolder();

    String query = DEFAULT_MODULES_PROPERTIES_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{MODULES}", buildModulesList(modulesSlugs));

    post("/api/apply-patches", query);
  }

  private String buildModulesList(List<String> modulesSlugs) {
    return modulesSlugs.stream().map(slug -> "\"" + slug + "\"").collect(Collectors.joining(","));
  }

  @When("I get module {string} properties definition")
  public void getModulePropertiesDefinition(String moduleSlug) {
    rest.getForEntity(moduleUrl(moduleSlug), Void.class);
  }

  @When("I apply {string} module to default project with package json")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug, Map<String, String> parameters) {
    String projectFolder = newTestFolder();

    addPackageJsonToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  @When("I apply {string} module to default project with package json without parameters")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug) {
    String projectFolder = newTestFolder();

    addPackageJsonToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, null));
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

    addPomToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, parameters));
  }

  @When("I apply {string} module to default project without parameters")
  public void applyModuleForDefaultProjectWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProject(moduleSlug, null);
  }

  @When("I apply and commit {string} module to default project")
  public void applyAndCommitModuleForDefaultProject(String moduleSlug, Map<String, String> parameters) throws IOException {
    String projectFolder = newTestFolder();

    Path projectPath = Paths.get(projectFolder);
    Files.createDirectories(projectPath);

    loadGitConfig(projectPath);

    String query = MODULE_APPLY_AND_COMMIT_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{{ PARAMETERS }}", buildModuleParameters(parameters));

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
    return MODULE_APPLICATION_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{{ PARAMETERS }}", buildModuleParameters(parameters));
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

  private static void addPackageJsonToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/empty-node/package.json", "package.json");
  }

  private static void addPomToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/init-maven/pom.xml", "pom.xml");
  }

  private static void addFileToProject(String folder, String source, String destination) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path filePath = folderPath.resolve(destination);
    try {
      Files.copy(Paths.get(source), filePath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private void post(String uri, String content) {
    rest.exchange(uri, HttpMethod.POST, new HttpEntity<>(content, jsonHeaders()), Void.class);
  }

  private HttpHeaders jsonHeaders() {
    HttpHeaders headers = new HttpHeaders();

    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  @Then("I should have files in {string}")
  public void shouldHaveFiles(String basePath, List<String> files) {
    assertThatLastResponse().hasOkStatus();

    SoftAssertions assertions = new SoftAssertions();

    files.stream().map(file -> Paths.get(lastProjectFolder(), basePath, file)).forEach(assertFileExist(assertions));

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

    files.stream().map(file -> Paths.get(lastProjectFolder(), basePath, file)).forEach(assertFileNotExist(assertions));

    assertions.assertAll();
  }

  private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
    return path -> assertions.assertThat(!Files.exists(path)).as(fileFoundMessage(path)).isTrue();
  }

  private Supplier<String> fileFoundMessage(Path path) {
    return () -> "Can find file " + path + " in project folder, found " + projectFiles();
  }

  private String projectFiles() {
    try {
      return Files.walk(Paths.get(lastProjectFolder())).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.joining(", "));
    } catch (IOException e) {
      return "unreadable folder";
    }
  }

  @Then("I should have properties definitions")
  public void shouldHaveModulePropertiesDefintions(List<Map<String, Object>> propertiesDefintion) {
    assertThatLastResponse().hasOkStatus().hasElement("$.definitions").containingExactly(propertiesDefintion);
  }

  @Then("I should have landscape level {int} with elements")
  public void shouldHavelandscapeLevelElements(int level, List<Map<String, String>> elements) {
    assertThatLastResponse().hasOkStatus();

    elements.forEach(element -> {
      JSONArray types = (JSONArray) CucumberTestContext.getElement(
        "$.levels[" + level + "].elements[?(@.slug=='" + element.get("Slug") + "')].type"
      );

      assertThat(types.get(0)).isEqualTo(element.get("Type"));
    });
  }

  @Then("I should have {int} file in {string}")
  public void shouldHaveFilesCountInDirectory(int filesCount, String directory) throws IOException {
    assertThatLastResponse().hasOkStatus();

    assertThat(Files.list(Paths.get(lastProjectFolder(), directory)).count()).isEqualTo(filesCount);
  }

  @Then("I should have unknown slug {string} error message")
  public void shouldHaveUnknownSlugErrorMessage(String slugName) {
    assertThatLastResponse()
      .hasHttpStatus(HttpStatus.BAD_REQUEST)
      .hasElement("$.title")
      .withValue("Module " + slugName + " does not exist");
  }
}
