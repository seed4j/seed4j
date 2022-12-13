package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.project.domain.ProjectPath;

@UnitTest
@ExtendWith(LogsSpy.class)
class TraceProjectFormatterTest {

  private static final TraceProjectFormatter formatter = new TraceProjectFormatter();

  private final LogsSpy logs;

  public TraceProjectFormatterTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldTraceThatNoNpmIsInstalled() {
    formatter.format(new ProjectPath("dummy"));

    logs.shouldHave(Level.INFO, "No npm installed");
  }
}
