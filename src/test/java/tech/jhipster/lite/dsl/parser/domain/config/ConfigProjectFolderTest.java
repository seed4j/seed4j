package tech.jhipster.lite.dsl.parser.domain.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigProjectFolder;

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
