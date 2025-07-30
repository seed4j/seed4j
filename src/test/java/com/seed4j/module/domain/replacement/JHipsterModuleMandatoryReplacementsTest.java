package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.JHipsterModule.*;
import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("src/test/resources/projects/maven/pom.xml")).isExactlyInstanceOf(
      UnknownCurrentValueException.class
    );
  }

  private static String replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(folder.get()).build());

    return JHipsterModuleMandatoryReplacementsFactory.builder(module)
      .in(new JHipsterProjectFilePath(file))
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .replacers()
      .reduce(readContent(file), (content, replacer) -> replacer.apply(content), (first, second) -> first);
  }

  private static String readContent(String file) {
    try {
      return Files.readString(Path.of(file));
    } catch (IOException e) {
      throw new AssertionError();
    }
  }
}
