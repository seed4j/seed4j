package tech.jhipster.lite.generator.server.javatool.frontendmaven.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.domain.FrontendMavenService;

@Service
public record FrontendMavenApplicationService(FrontendMavenService frontendMavenService) {
  public void addFrontendMavenPlugin(Project project) {
    frontendMavenService.addFrontendMavenPlugin(project);
  }
}
