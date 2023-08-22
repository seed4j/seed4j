package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javaproperties.*;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemSpringFactoriesCommandsHandlerTest {

  public static final Path EXISTING_SPRING_FACTORIES = Paths.get(
    "src/test/resources/projects/project-with-spring-factories/spring.factories"
  );
  private static final FileSystemSpringFactoriesCommandsHandler handler = new FileSystemSpringFactoriesCommandsHandler();

  @Test
  void shouldCreateDefaultTestPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestFactory()));

    assertThat(contentNormalizingNewLines(Paths.get(folder, "src/test/resources/META-INF/spring.factories")))
      .contains(
        """
        o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
        """
      );
  }

  @Test
  void shouldUpdateTestProperties() {
    String folder = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(folder, "src/test/resources/META-INF/spring.factories");
    loadDefaultProperties(EXISTING_SPRING_FACTORIES, propertiesFile);

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestFactory()));

    assertThat(contentNormalizingNewLines(propertiesFile))
      .contains(
        """
        o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
        """
      );
  }

  private SpringFactories properties(SpringFactory factory) {
    return new SpringFactories(List.of(factory));
  }

  public SpringFactory springTestFactory() {
    return SpringFactory
      .builder(SpringFactoryType.TEST_FACTORIES)
      .key(propertyKey("o.s.c.ApplicationListener"))
      .value(propertyValue("c.m.m.MyListener1", "c.m.m.MyListener2"));
  }
}
