package tech.jhipster.lite.project.domain.history;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.project.domain.history.ProjectHistoryFixture.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ProjectHistoryTest {

  @Test
  void shouldAddActionToHistory() {
    ProjectHistory history = projectHistory();

    history.append(firstAction());

    assertThat(history.actions()).usingRecursiveFieldByFieldElementComparator().containsExactly(firstAction(), projectAction());
  }

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
