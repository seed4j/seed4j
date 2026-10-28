package com.seed4j.project.infrastructure.primary;

import static com.seed4j.TestProjects.*;
import static com.seed4j.cucumber.rest.CucumberRestAssertions.assertThatLastResponse;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.module.infrastructure.secondary.git.GitTestUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class ServerProjectsSteps {

  private final TestRestTemplate rest;

  public ServerProjectsSteps(TestRestTemplate rest) {
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

  @Then("I should have committed files in {string}")
  public void shouldHaveCommittedFiles(String folder) {
    assertThatLastResponse().hasOkStatus();

    assertThat(GitTestUtil.getTrackedFiles(lastProjectPath(), folder)).isNotEmpty();
    assertThat(GitTestUtil.getUncommittedChanges(lastProjectPath(), folder)).isEmpty();
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
