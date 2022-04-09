package tech.jhipster.lite.generator.server.springboot.mvc.dummy.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain.DummyDomainService;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain.DummyService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class DummyBeanConfiguration {

  private final ProjectRepository projectRepository;

  public DummyBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public DummyService dummyService() {
    return new DummyDomainService(projectRepository);
  }
}
