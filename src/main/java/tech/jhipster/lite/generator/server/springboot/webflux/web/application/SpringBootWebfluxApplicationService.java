package tech.jhipster.lite.generator.server.springboot.webflux.web.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.webflux.web.domain.SpringBootWebfluxService;

@Service
public record SpringBootWebfluxApplicationService(SpringBootWebfluxService springBootWebfluxService) {
  public void addSpringBootWebflux(Project project) {
    this.springBootWebfluxService.addSpringBootWebflux(project);
  }
}
