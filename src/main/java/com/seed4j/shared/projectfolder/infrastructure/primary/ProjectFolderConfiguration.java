package com.seed4j.shared.projectfolder.infrastructure.primary;

import com.seed4j.shared.projectfolder.domain.ForcedProjectFolder;
import com.seed4j.shared.projectfolder.domain.FreeProjectFolder;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectFolderConfiguration {

  @Bean
  @ConditionalOnProperty(value = "seed4j.forced-project-folder")
  ProjectFolder forcedProjectFolder(@Value("${seed4j.forced-project-folder}") String forcedProjectFolder) {
    return new ForcedProjectFolder(forcedProjectFolder);
  }

  @Bean
  @ConditionalOnProperty(value = "seed4j.forced-project-folder", matchIfMissing = true, havingValue = "dummy")
  ProjectFolder freeProjectFolder() {
    return new FreeProjectFolder();
  }
}
