package tech.jhipster.lite.generator.server.springboot.springcloud.common.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@Configuration
public class SpringCloudCommonBeanConfiguration {

  private final ProjectRepository projectRepository;

  public SpringCloudCommonBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public SpringCloudCommonService springCloudCommonService() {
    return new SpringCloudCommonDomainService(projectRepository);
  }
}
