package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;

@UnitTest
class JHipsterFileContentTest {

  @Test
  void shouldNotReadUnknownFile() {
    JHipsterFileContent content = new JHipsterFileContent(Paths.get("dummy"));

    assertThatThrownBy(() -> content.read(context())).isExactlyInstanceOf(GeneratorException.class).hasMessageContaining("dummy");
  }

  @Test
  void shouldReadNotTemplatedContent() {
    JHipsterFileContent content = new JHipsterFileContent(Paths.get("/generator/content/no-template.txt"));

    assertThat(content.read(context())).isEqualTo("This is my content");
  }

  @Test
  void shouldReadTemplatedContent() {
    JHipsterFileContent content = new JHipsterFileContent(Paths.get("/generator/content/template.txt.mustache"));

    assertThat(content.read(context())).isEqualTo("This is com.test.myapp");
  }
}
