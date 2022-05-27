package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;

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
}
