package tech.jhipster.lite.generator;

import static tech.jhipster.lite.generator.ProjectsSteps.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

public abstract class ModulesSteps {

  @Autowired
  private TestRestTemplate rest;

  protected void applyModuleForDefaultProject(String... moduleUrl) {
    ProjectDTO project = newDefaultProjectDto();

    for (String url : moduleUrl) {
      post(url, JsonHelper.writeAsString(project));
    }
  }

  protected void applyModuleForDefaultProjectWithMavenFile(String moduleUrl) {
    ProjectDTO project = newDefaultProjectDto();

    addPomToproject(project.getFolder());

    post(moduleUrl, JsonHelper.writeAsString(project));
  }

  private static void addPomToproject(String folder) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folderPath.resolve("pom.xml");
    try {
      Files.copy(Paths.get("src/test/resources/projects/maven/pom.xml"), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static void addPackageToproject(String folder) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folderPath.resolve("pom.xml");
    try {
      Files.copy(Paths.get("src/test/resources/projects/maven/pom.xml"), pomPath);
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
}
