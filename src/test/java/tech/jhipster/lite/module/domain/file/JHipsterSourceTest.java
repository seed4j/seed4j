package tech.jhipster.lite.module.domain.file;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterSourceTest {

  @Test
  void shouldAppendMustacheExtensionWhenReadingTemplate() {
    assertThat(new JHipsterSource(Paths.get("src/main/resources")).template("Assert.java").get().toString()).endsWith(".mustache");
  }

  @Test
  void shouldNotAppendMustacheExtensionTwiceWhenReadingTemplate() {
    assertThat(new JHipsterSource(Paths.get("src/main/resources")).template("Assert.java.mustache").get().toString())
      .doesNotEndWith(".mustache.mustache");
  }

  @Test
  void shouldGetTemplateExtension() {
    assertThat(new JHipsterSource(Paths.get("src/main/resources")).template("Assert.java.mustache").extension()).isEqualTo(".java");
  }

  @Test
  void shouldGetFileExtension() {
    assertThat(new JHipsterSource(Paths.get("src/main/resources")).file("Assert.java").extension()).isEqualTo(".java");
  }

  @Test
  void testToString() {
    //Given
    final Path path = Paths.get("sample");
    //When
    final JHipsterSource source = new JHipsterSource(path);
    //Then
    assertThat(source.toString()).isEqualTo(path.toString());
  }
}
