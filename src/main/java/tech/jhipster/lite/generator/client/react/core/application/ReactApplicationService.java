package tech.jhipster.lite.generator.client.react.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.core.domain.ReactService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class ReactApplicationService {

  private final ReactService reactService;

  public ReactApplicationService(ReactService reactService) {
    this.reactService = reactService;
  }

  public void init(Project project) {
    reactService.init(project);
  }
}
