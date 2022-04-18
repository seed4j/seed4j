package tech.jhipster.lite.generator.client.tools.playwright.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.playwright.domain.PlaywrightService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public record PlaywrightApplicationService(PlaywrightService playwrightService) {
  public void init(Project project) {
    playwrightService.init(project);
  }
}
