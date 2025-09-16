package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.Seed4JModule.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javaproperties.*;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemSpringFactoriesCommandsHandlerTest {

  public static final Path EXISTING_SPRING_FACTORIES = Path.of(
    "src/test/resources/projects/project-with-spring-factories/spring.factories"
  );
  private static final FileSystemSpringFactoriesCommandsHandler handler = new FileSystemSpringFactoriesCommandsHandler();

  @Test
  void shouldCreateDefaultTestPropertiesForProjectWithoutProperties() {
    String folder = tmpDirForTest();

    handler.handle(new Seed4JProjectFolder(folder), properties(springTestFactory()));

    assertThat(contentNormalizingNewLines(Path.of(folder, "src/test/resources/META-INF/spring.factories"))).contains(
      """
      o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
      """
    );
  }

  @Test
  void shouldUpdateTestProperties() {
    String folder = tmpDirForTest();
    Path propertiesFile = Path.of(folder, "src/test/resources/META-INF/spring.factories");
    loadDefaultProperties(EXISTING_SPRING_FACTORIES, propertiesFile);

    handler.handle(new Seed4JProjectFolder(folder), properties(springTestFactory()));

    assertThat(contentNormalizingNewLines(propertiesFile)).contains(
      """
      o.s.c.ApplicationListener=c.m.m.MyListener1,c.m.m.MyListener2
      """
    );
  }

  private SpringFactories properties(SpringFactory factory) {
    return new SpringFactories(List.of(factory));
  }

  public SpringFactory springTestFactory() {
    return SpringFactory.builder(SpringFactoryType.TEST_FACTORIES)
      .key(propertyKey("o.s.c.ApplicationListener"))
      .value(propertyValue("c.m.m.MyListener1", "c.m.m.MyListener2"));
  }
}
