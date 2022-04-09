package tech.jhipster.lite.generator.server.springboot.mvc.dummy.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain.DummyService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class DummyApplicationService {

  private final DummyService dummyService;

  public DummyApplicationService(DummyService dummyService) {
    this.dummyService = dummyService;
  }

  public void applyDummyGitPatch(Project project) {
    dummyService.applyDummyGitPatch(project);
  }
}
