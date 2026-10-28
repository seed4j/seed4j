package com.seed4j.project.infrastructure.secondary;

import com.seed4j.history.infrastructure.primary.JavaProjectHistories;
import com.seed4j.history.infrastructure.primary.ProjectHistorySummary;
import com.seed4j.project.domain.ModuleSlug;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.history.ModuleParameters;
import com.seed4j.project.domain.history.ProjectHistories;
import com.seed4j.project.domain.history.ProjectHistory;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Repository;

@Repository
class EngineProjectHistories implements ProjectHistories {

  private final JavaProjectHistories histories;

  public EngineProjectHistories(JavaProjectHistories histories) {
    this.histories = histories;
  }

  @Override
  public ProjectHistory get(ProjectPath path) {
    Assert.notNull("path", path);

    ProjectHistorySummary summary = histories.get(path.get());

    return new ProjectHistory(summary.modules().stream().map(ModuleSlug::new).toList(), new ModuleParameters(summary.properties()));
  }
}
