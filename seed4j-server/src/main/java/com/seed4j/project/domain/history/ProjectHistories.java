package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ProjectPath;

public interface ProjectHistories {
  ProjectHistory get(ProjectPath path);
}
