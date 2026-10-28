package com.seed4j.history.application;

import com.seed4j.history.domain.ProjectActionToAppend;
import com.seed4j.history.domain.ProjectActionsAppender;
import com.seed4j.history.domain.ProjectHistory;
import com.seed4j.history.domain.ProjectHistoryRepository;
import com.seed4j.history.domain.ProjectPath;
import org.springframework.stereotype.Service;

@Service
public class ProjectHistoryApplicationService {

  private final ProjectHistoryRepository histories;
  private final ProjectActionsAppender actionsAppender;

  public ProjectHistoryApplicationService(ProjectHistoryRepository histories) {
    this.histories = histories;
    actionsAppender = new ProjectActionsAppender(histories);
  }

  public void append(ProjectActionToAppend actionToAppend) {
    actionsAppender.append(actionToAppend);
  }

  public ProjectHistory get(ProjectPath path) {
    return histories.get(path);
  }
}
