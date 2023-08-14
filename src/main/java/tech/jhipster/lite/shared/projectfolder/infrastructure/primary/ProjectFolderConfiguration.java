package tech.jhipster.lite.shared.projectfolder.infrastructure.primary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.shared.projectfolder.domain.ForcedProjectFolder;
import tech.jhipster.lite.shared.projectfolder.domain.FreeProjectFolder;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

@Configuration
class ProjectFolderConfiguration {

  @Value("${application.forced-project-folder:}")
  private String forcedProjectFolder;

  @Bean
  @ConditionalOnProperty(value = "application.forced-project-folder")
  ProjectFolder forcedProjectFolder() {
    return new ForcedProjectFolder(forcedProjectFolder);
  }

  @Bean
  @ConditionalOnProperty(value = "application.forced-project-folder", matchIfMissing = true, havingValue = "dummy")
  ProjectFolder freeProjectFolder() {
    return new FreeProjectFolder();
  }
}
