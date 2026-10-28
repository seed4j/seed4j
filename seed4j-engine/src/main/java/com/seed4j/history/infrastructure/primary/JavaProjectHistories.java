package com.seed4j.history.infrastructure.primary;

import com.seed4j.history.application.ProjectHistoryApplicationService;
import com.seed4j.history.domain.ProjectAction;
import com.seed4j.history.domain.ProjectActionToAppend;
import com.seed4j.history.domain.ProjectHistory;
import com.seed4j.history.domain.ProjectPath;
import com.seed4j.module.domain.AppliedModulesHistory;
import com.seed4j.module.domain.Seed4JModuleApplied;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
public class JavaProjectHistories implements AppliedModulesHistory {

  private final ProjectHistoryApplicationService histories;

  public JavaProjectHistories(ProjectHistoryApplicationService histories) {
    this.histories = histories;
  }

  @Override
  public void append(Seed4JModuleApplied moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    histories.append(projectActionToAdd(moduleApplied));
  }

  private static ProjectActionToAppend projectActionToAdd(Seed4JModuleApplied moduleApplied) {
    ProjectPath path = new ProjectPath(moduleApplied.properties().projectFolder().get());

    ProjectAction action = ProjectAction.builder()
      .module(moduleApplied.slug().get())
      .date(moduleApplied.time())
      .parameters(moduleApplied.properties().getParameters());

    return new ProjectActionToAppend(path, action);
  }

  public ProjectHistorySummary get(String projectPath) {
    ProjectHistory history = histories.get(new ProjectPath(projectPath));

    return new ProjectHistorySummary(
      history
        .actions()
        .stream()
        .map(action -> action.module().get())
        .toList(),
      history.latestProperties().get()
    );
  }
}
