package com.seed4j.project.application;

import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.download.ProjectsDownloader;
import com.seed4j.project.domain.history.ProjectActionToAppend;
import com.seed4j.project.domain.history.ProjectActionsAppender;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectsApplicationService {

  private final ProjectsRepository projects;
  private final ProjectsDownloader downloader;
  private final ProjectActionsAppender actionsAppender;

  public ProjectsApplicationService(ProjectFolder projectFolder, ProjectsRepository projects) {
    this.projects = projects;

    downloader = new ProjectsDownloader(projectFolder, projects);
    actionsAppender = new ProjectActionsAppender(projects);
  }

  public Project get(ProjectPath path) {
    return downloader.download(path);
  }

  public void append(ProjectActionToAppend actionToAppend) {
    actionsAppender.append(actionToAppend);
  }

  public ProjectHistory getHistory(ProjectPath path) {
    return projects.getHistory(path);
  }
}
