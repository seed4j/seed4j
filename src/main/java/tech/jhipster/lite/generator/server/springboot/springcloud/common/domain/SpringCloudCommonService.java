package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import tech.jhipster.lite.generator.tools.domain.Project;

public interface SpringCloudCommonService {
  void addSpringCloudCommonDependencies(Project project);

  void addJhipsterRegistryDockerCompose(Project project);

  void addOrMergeBootstrapProperties(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder);
}
