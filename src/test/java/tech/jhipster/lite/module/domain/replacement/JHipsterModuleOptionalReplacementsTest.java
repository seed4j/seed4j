package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class JHipsterModuleOptionalReplacementsTest {

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownCurrentValue() {
    assertThatCode(() -> replaceIn("src/test/resources/projects/maven/pom.xml")).doesNotThrowAnyException();
  }

  private static String replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(folder.get()).build());

    return JHipsterModuleOptionalReplacementsFactory.builder(module)
      .in(path(file))
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .buildReplacers(new JHipsterProjectFolder("dummy"), mock(GeneratedProjectRepository.class))
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
