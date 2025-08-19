package com.seed4j.project.domain;

import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.history.ProjectAction;
import com.seed4j.project.domain.history.ProjectHistory;
import java.util.Optional;

public interface ProjectsRepository {
  Optional<Project> get(ProjectPath path);

  void save(ProjectPath path, ProjectAction action);

  ProjectHistory getHistory(ProjectPath path);
}
