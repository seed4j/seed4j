package tech.jhipster.lite.generator.client.tools.cypress.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressDomainService;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@Configuration
public class CypressBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;
  private final ClientCommonService clientCommonService;

  public CypressBeanConfiguration(ProjectRepository projectRepository, NpmService npmService, ClientCommonService clientCommonService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
    this.clientCommonService = clientCommonService;
  }

  @Bean
  public CypressService cypressService() {
    return new CypressDomainService(projectRepository, npmService, clientCommonService);
  }
}
