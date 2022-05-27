package tech.jhipster.lite.generator;

import static tech.jhipster.lite.generator.ProjectsSteps.*;

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

  protected void applyModuleForDefaultProject(String moduleUrl) {
    ProjectDTO project = newDefaultProjectDto();

    post(moduleUrl, JsonHelper.writeAsString(project));
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
