package tech.jhipster.lite.generator.client.common.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonDomainService;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class ClientCommonBeanConfiguration {

  private final ProjectRepository projectRepository;

  public ClientCommonBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public ClientCommonService clientCommonService() {
    return new ClientCommonDomainService(projectRepository);
  }
}
