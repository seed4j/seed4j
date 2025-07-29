package com.seed4j.project.domain.download;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.UnknownProjectException;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;

public class ProjectsDownloader {

  private final ProjectFolder projectFolder;
  private final ProjectsRepository projects;

  public ProjectsDownloader(ProjectFolder projectFolder, ProjectsRepository projects) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("projects", projects);

    this.projectFolder = projectFolder;
    this.projects = projects;
  }

  public Project download(ProjectPath path) {
    assertValidPath(path);

    return projects.get(path).orElseThrow(UnknownProjectException::new);
  }

  private void assertValidPath(ProjectPath path) {
    if (projectFolder.isInvalid(path.get())) {
      throw new InvalidDownloadException();
    }
  }
}
