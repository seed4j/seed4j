package com.seed4j.project.infrastructure.primary;

import static com.seed4j.TestProjects.*;
import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.module.infrastructure.secondary.git.GitTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class ProjectsSteps {

  private final TestRestTemplate rest;

  public ProjectsSteps(TestRestTemplate rest) {
    this.rest = rest;
  }

  @When("I download the created project")
  public void downloadCreatedProject() {
    rest.exchange("/api/projects?path=" + lastProjectFolder(), HttpMethod.GET, new HttpEntity<>(octetsHeaders()), Void.class);
  }

  private HttpHeaders octetsHeaders() {
    var headers = new HttpHeaders();

    headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

    return headers;
  }

  @When("I format the created project")
  public void formatCreatedProject() {
    rest.postForEntity("/api/format-project?path=" + lastProjectFolder(), null, Void.class);
  }

  @When("I get the created project information")
  public void getCreatedProjectInformation() {
    rest.exchange("/api/projects?path=" + lastProjectFolder(), HttpMethod.GET, new HttpEntity<>(jsonHeaders()), Void.class);
  }

  private HttpHeaders jsonHeaders() {
    var headers = new HttpHeaders();

    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  @Then("I should have {string} in {string}")
  public void shouldHaveFileContent(String content, String file) throws IOException {
    assertThatLastResponse().hasHttpStatusIn(200, 201);

    assertThat(Files.readString(Path.of(lastProjectFolder(), file))).contains(content);
  }

  @Then("I should not have {string} in {string}")
  public void shouldNotHaveFileContent(String content, String file) throws IOException {
    assertThatLastResponse().hasHttpStatusIn(200, 201);

    assertThat(Files.readString(Path.of(lastProjectFolder(), file))).doesNotContain(content);
  }

  @Then("I should have entries in {string}")
  public void shouldHaveStringsInFile(String file, List<String> values) throws IOException {
    assertThatLastResponse().hasHttpStatusIn(200, 201);
    assertThat(Files.readString(Path.of(lastProjectFolder(), file))).contains(values);
  }

  @Then("I should have {string} project")
  public void shouldHaveProjectFile(String file) {
    assertThatLastResponse()
      .hasOkStatus()
      .hasHeader(HttpHeaders.CONTENT_DISPOSITION)
      .containing("attachment; filename=" + file)
      .and()
      .hasHeader("X-Suggested-Filename")
      .containing(file);
  }

  @Then("I should have modules")
  public void shouldHaveModules(List<Map<String, String>> modules) {
    assertThatLastResponse().hasOkStatus().hasElement("$.modules").containingExactly(modules);
  }

  @Then("I should have properties")
  public void shouldHaveProperties(Map<String, Object> properties) {
    assertThatLastResponse().hasOkStatus().hasElement("$.properties").containing(properties).withElementsCount(properties.size());
  }

  @Then("I should have commit {string}")
  public void shouldHaveCommit(String commitMessage) {
    assertThatLastResponse().hasOkStatus();

    assertThat(GitTestUtil.getCommits(lastProjectPath())).contains(commitMessage);
  }

  @Then("I should not have any commit")
  public void shouldNotHaveCommits() {
    assertThatLastResponse().hasOkStatus();

    assertThat(GitTestUtil.getCommits(lastProjectPath())).isEmpty();
  }

  private Path lastProjectPath() {
    return Path.of(lastProjectFolder());
  }
}
