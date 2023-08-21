package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class JHipsterModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("src/test/resources/projects/maven/pom.xml"))
      .isExactlyInstanceOf(UnknownCurrentValueException.class);
  }

  private static String replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModulesFixture.propertiesBuilder(folder.get()).build());

    return JHipsterModuleMandatoryReplacementsFactory
      .builder(module)
      .in(new JHipsterProjectFilePath(file))
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .replacers()
      .reduce(readContent(file), (content, replacer) -> replacer.apply(content), (first, second) -> first);
  }

  private static String readContent(String file) {
    try {
      return Files.readString(Paths.get(file));
    } catch (IOException e) {
      throw new AssertionError();
    }
  }
}
