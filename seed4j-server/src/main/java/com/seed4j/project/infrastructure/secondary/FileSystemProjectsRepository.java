package com.seed4j.project.infrastructure.secondary;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.download.Project;
import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemProjectsRepository implements ProjectsRepository {

  private final FileSystemProjectDownloader downloader = new FileSystemProjectDownloader();

  @Override
  public Optional<Project> get(ProjectPath path) {
    Assert.notNull("path", path);

    return downloader.download(path);
  }
}
