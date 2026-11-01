package com.seed4j.project.application;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.download.ProjectsDownloader;
import com.seed4j.project.domain.history.ProjectHistories;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectsApplicationService {

  private final ProjectHistories histories;
  private final ProjectsDownloader downloader;

  public ProjectsApplicationService(ProjectFolder projectFolder, ProjectsRepository projects, ProjectHistories histories) {
    this.histories = histories;
    downloader = new ProjectsDownloader(projectFolder, projects);
  }

  public Project get(ProjectPath path) {
    return downloader.download(path);
  }

  public ProjectHistory getHistory(ProjectPath path) {
    return histories.get(path);
  }
}
