package tech.jhipster.lite.generator.server.springboot.springcloud.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@Service
public class SpringCloudCommonApplicationService {

  private final SpringCloudCommonService springCloudCommonService;

  public SpringCloudCommonApplicationService(SpringCloudCommonService springCloudCommonService) {
    this.springCloudCommonService = springCloudCommonService;
  }

  public void addOrMergeBootstrapProperties(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder) {
    springCloudCommonService.addOrMergeBootstrapProperties(project, sourceFolderPath, sourceFileName, destinationFileFolder);
  }
}
