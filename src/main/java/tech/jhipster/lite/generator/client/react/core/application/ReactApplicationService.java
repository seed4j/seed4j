package tech.jhipster.lite.generator.client.react.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.core.domain.ReactService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class ReactApplicationService {

  private final ReactService reactService;

  public ReactApplicationService(ReactService reactService) {
    this.reactService = reactService;
  }

  public void addReact(Project project) {
    reactService.addReact(project);
  }

  public void addStyledReact(Project project) {
    reactService.addStyledReact(project);
  }
}
