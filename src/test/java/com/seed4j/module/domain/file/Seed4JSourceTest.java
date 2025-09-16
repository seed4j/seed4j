package com.seed4j.module.domain.file;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JSourceTest {

  @Test
  void shouldAppendMustacheExtensionWhenReadingTemplate() {
    assertThat(new Seed4JSource(Path.of("src/main/resources")).template("Assert.java").get().toString()).endsWith(".mustache");
  }

  @Test
  void shouldNotAppendMustacheExtensionTwiceWhenReadingTemplate() {
    assertThat(new Seed4JSource(Path.of("src/main/resources")).template("Assert.java.mustache").get().toString()).doesNotEndWith(
      ".mustache.mustache"
    );
  }

  @Test
  void shouldGetTemplateExtension() {
    assertThat(new Seed4JSource(Path.of("src/main/resources")).template("Assert.java.mustache").extension()).isEqualTo(".java");
  }

  @Test
  void shouldGetFileExtension() {
    assertThat(new Seed4JSource(Path.of("src/main/resources")).file("Assert.java").extension()).isEqualTo(".java");
  }

  @Test
  void testToStringShowsPath() {
    assertThat(new Seed4JSource(Path.of("sample"))).hasToString("sample");
  }
}
