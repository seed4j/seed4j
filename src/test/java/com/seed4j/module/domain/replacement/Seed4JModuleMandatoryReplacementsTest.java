package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("src/test/resources/projects/maven/pom.xml")).isExactlyInstanceOf(
      UnknownCurrentValueException.class
    );
  }

  private static String replaceIn(String file) {
    Seed4JProjectFolder folder = new Seed4JProjectFolder("src/test/resources/projects");
    Seed4JModuleBuilder module = moduleBuilder(Seed4JModulesFixture.propertiesBuilder(folder.get()).build());

    return Seed4JModuleMandatoryReplacementsFactory.builder(module)
      .in(new Seed4JProjectFilePath(file))
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .replacers()
      .reduce(readContent(file), (content, replacer) -> replacer.apply(content), (first, second) -> first);
  }

  private static String readContent(String file) {
    try {
      return Files.readString(Path.of(file));
    } catch (IOException _) {
      throw new AssertionError();
    }
  }
}
