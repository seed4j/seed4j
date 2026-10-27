package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ProjectPath;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class ProjectHistoryFixture {

  private ProjectHistoryFixture() {}

  public static ProjectHistory projectHistory() {
    return new ProjectHistory(projectPath(), List.of(projectAction()));
  }

  public static ProjectPath projectPath() {
    return new ProjectPath("/tmp/test-project");
  }

  public static ProjectAction projectAction() {
    return ProjectAction.builder().module("test-module").date(Instant.parse("2021-12-03T10:15:30.00Z")).parameters(Map.of("key", "value"));
  }

  public static ProjectAction firstProjectAction() {
    return ProjectAction.builder()
      .module("another-test-module")
      .date(Instant.parse("2025-12-03T10:15:30Z"))
      .parameters(Map.of("key", "first-value"));
  }

  public static ProjectAction secondProjectAction() {
    return ProjectAction.builder()
      .module("another-test-module")
      .date(Instant.parse("2025-12-03T10:15:31Z"))
      .parameters(Map.of("key", "second-value"));
  }
}
