package com.seed4j.project.domain;

import com.seed4j.project.domain.download.Project;
import com.seed4j.project.domain.history.ProjectHistory;
import java.util.Optional;

public interface ProjectsRepository {
  Optional<Project> get(ProjectPath path);

  void save(ProjectHistory history);

  ProjectHistory getHistory(ProjectPath path);
}
