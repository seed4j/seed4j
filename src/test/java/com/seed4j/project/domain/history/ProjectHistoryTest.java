package com.seed4j.project.domain.history;

import static com.seed4j.project.domain.history.ProjectHistoryFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class ProjectHistoryTest {

  @Test
  void shouldGetMergedProperties() {
    ProjectHistory history = new ProjectHistory(projectPath(), List.of(firstAction(), projectAction()));

    ModuleParameters properties = history.latestProperties();

    assertThat(properties.get()).containsExactlyInAnyOrderEntriesOf(Map.of("key", "value", "port", 8080));
  }

  private ProjectAction firstAction() {
    return ProjectAction.builder()
      .module("test-module")
      .date(Instant.parse("2020-12-03T10:16:30.00Z"))
      .parameters(Map.of("key", "old-value", "port", 8080));
  }
}
