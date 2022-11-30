package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigProjectFolderTest {

  @Test
  void shouldReturnPath() {
    ConfigProjectFolder config = new ConfigProjectFolder("/tmp/test");
    assertEquals(config.filePath("app"), Path.of("/tmp/test", "app"));
  }

  @Test
  void shouldReturnFolder() {
    ConfigProjectFolder config = new ConfigProjectFolder("/tmp/test");
    assertEquals("/tmp/test", config.get());
  }
}
