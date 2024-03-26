package tech.jhipster.lite.project.application;

import java.util.Collection;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.ProjectsRepository;
import tech.jhipster.lite.project.domain.download.Project;
import tech.jhipster.lite.project.domain.download.ProjectsDownloader;
import tech.jhipster.lite.project.domain.history.ProjectActionToAppend;
import tech.jhipster.lite.project.domain.history.ProjectActionsAppender;
import tech.jhipster.lite.project.domain.history.ProjectHistory;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

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

  public void format(ProjectPath path) {
    projects.format(path);
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

  public Collection<String> getAppliedModules(JHipsterProjectFolder projectFolder) {
    return projects.getHistory(new ProjectPath(projectFolder.folder())).getAppliedModules();
  }
}
