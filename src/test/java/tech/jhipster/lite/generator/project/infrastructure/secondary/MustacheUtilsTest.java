package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_TEMPLATE_RESOURCES;

import com.github.mustachejava.MustacheNotFoundException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;

@UnitTest
class MustacheUtilsTest {

  @Test
  void shouldTemplate() throws Exception {
    String file = FileUtils.getPath(TEST_TEMPLATE_RESOURCES, "common", "README.md.mustache");

    Chips chips = new Chips("chips");
    String result = MustacheUtils.template(file, chips);

    assertThat(result).contains("The apero can start when there is some chips");
  }

  @Test
  void shouldTemplateInClassPath() throws Exception {
    String file = FileUtils.getPath("generator/common/README.md.mustache");

    Chips chips = new Chips("chips");
    String result = MustacheUtils.template(file, chips);

    assertThat(result).contains("The apero can start when there is some chips");
  }

  @Test
  void shouldTemplateWithMap() throws Exception {
    String file = FileUtils.getPath(TEST_TEMPLATE_RESOURCES, "common", "README.md.mustache");

    Map<String, String> chips = Map.of("name", "chips");
    String result = MustacheUtils.template(file, chips);

    assertThat(result).contains("The apero can start when there is some chips");
  }

  @Test
  void shouldNotTemplateWithNonExistingFile() {
    String file = FileUtils.getPath(TEST_TEMPLATE_RESOURCES, "common", "README.md.bad.mustache");

    Chips chips = new Chips("chips");
    assertThatThrownBy(() -> MustacheUtils.template(file, chips)).isExactlyInstanceOf(MustacheNotFoundException.class);
  }

  @Test
  void shouldWithExtWhenNoExt() {
    assertThat(MustacheUtils.withExt("chips")).isEqualTo("chips.mustache");
  }

  @Test
  void shouldWithExtWhenAlreadyExt() {
    assertThat(MustacheUtils.withExt("chips.mustache")).isEqualTo("chips.mustache");
  }

  @Test
  void shouldWithoutExtWhenNoExt() {
    assertThat(MustacheUtils.withoutExt("chips")).isEqualTo("chips");
  }

  @Test
  void shouldWithoutExtWhenAlreadyExt() {
    assertThat(MustacheUtils.withoutExt("chips.mustache")).isEqualTo("chips");
  }

  static class Chips {

    private final String name;

    public Chips(String path) {
      this.name = path;
    }

    public String getName() {
      return name;
    }
  }
}
