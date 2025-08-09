package com.seed4j.module.domain.replacement;

import static com.seed4j.module.domain.JHipsterModule.*;
import static com.seed4j.module.domain.replacement.ReplacementCondition.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class JHipsterModuleOptionalReplacementsTest {

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownCurrentValue() {
    assertThatCode(() -> replaceIn("src/test/resources/projects/maven/pom.xml")).doesNotThrowAnyException();
  }

  private static String replaceIn(String file) {
    SeedProjectFolder folder = new SeedProjectFolder("src/test/resources/projects");
    SeedModuleBuilder module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(folder.get()).build());

    return SeedModuleOptionalReplacementsFactory.builder(module)
      .in(path(file))
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .buildReplacers(new SeedProjectFolder("dummy"), mock(GeneratedProjectRepository.class))
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
