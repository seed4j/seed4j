package tech.jhipster.lite.generator.client.react.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.core.domain.ReactService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record ReactApplicationService(ReactService reactService) {
  public void addReact(Project project) {
    reactService.addReact(project);
  }

  public void addStyledReact(Project project) {
    reactService.addStyledReact(project);
  }
}
