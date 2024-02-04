package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import ch.qos.logback.classic.Level;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.Logs;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.LogsSpyExtension;
import tech.jhipster.lite.UnitTest;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class JHipsterModuleContextTest {

  @Logs
  private LogsSpy logs;

  @Test
  void shouldGetIndentSizeFromInvalidIndentation() {
    Indentation indentation = JHipsterModuleContext.builder(emptyModuleBuilder()).put("indentSize", "dummy").build().indentation();

    logs.shouldHave(Level.INFO, "invalid indentation, using default");
    assertThat(indentation).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetDefaultContext() {
    Map<String, Object> context = JHipsterModuleContext.builder(emptyModuleBuilder()).build().get();

    assertThat(context)
      .hasSize(6)
      .contains(
        entry("baseName", "jhipster"),
        entry("projectName", "JHipster Project"),
        entry("packageName", "com.mycompany.myapp"),
        entry("serverPort", 8080),
        entry("indentSize", 2),
        entry("javaVersion", "21")
      );
  }
}
