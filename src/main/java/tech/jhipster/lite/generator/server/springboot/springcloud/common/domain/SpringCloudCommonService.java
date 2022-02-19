package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringCloudCommonService {
  void addOrMergeBootstrapProperties(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder);
}
