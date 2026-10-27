package com.seed4j.shared.projectfolder.infrastructure.primary;

import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project-folders")
class ProjectFoldersResource {

  private final ProjectFolder projectFolder;

  ProjectFoldersResource(ProjectFolder projectFolder) {
    this.projectFolder = projectFolder;
  }

  @GetMapping
  public String getAvailableFolderName() {
    return projectFolder.generatePath();
  }
}
