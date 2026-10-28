package com.seed4j.project.domain;

import com.seed4j.project.domain.download.Project;
import java.util.Optional;

public interface ProjectsRepository {
  Optional<Project> get(ProjectPath path);
}
