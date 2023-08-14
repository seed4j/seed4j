package tech.jhipster.lite.shared.projectfolder.infrastructure.primary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

@RestController
@RequestMapping("/api/project-folders")
class ProjectFoldersResource {

  private final ProjectFolder jHipsterProjectFolderFactory;

  ProjectFoldersResource(ProjectFolder jHipsterProjectFolderFactory) {
    this.jHipsterProjectFolderFactory = jHipsterProjectFolderFactory;
  }

  @GetMapping
  public String getAvailableFolderName() {
    return jHipsterProjectFolderFactory.generatePath();
  }
}
