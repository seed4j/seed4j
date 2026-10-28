package com.seed4j.history.domain;

public interface ProjectHistoryRepository {
  void save(ProjectPath path, ProjectAction action);

  ProjectHistory get(ProjectPath path);
}
