package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.module.domain.SeedModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.javaproperties.SpringProperties;
import com.seed4j.module.domain.javaproperties.SpringProperty;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class FileSystemSpringPropertiesCommandsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Path.of(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );
  private static final FileSystemSpringPropertiesCommandsHandler handler = new FileSystemSpringPropertiesCommandsHandler();

  @Test
  void shouldCreateDefaultMainPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new SeedProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(Path.of(folder, "src/main/resources/config/application.properties"))).contains(
      "springdoc.swagger-ui.operationsSorter=alpha,beta"
    );
  }

  @Test
  void shouldCreateProfileMainPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new SeedProjectFolder(folder), properties(springLocalMainProperty()));

    assertThat(content(Path.of(folder, "src/main/resources/config/application-local.properties"))).contains(
      "springdoc.swagger-ui.operationsSorter=alpha,beta"
    );
  }

  @Test
  void shouldCreateDefaultTestPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new SeedProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(Path.of(folder, "src/test/resources/config/application.properties"))).contains(
      "springdoc.swagger-ui.operationsSorter=alpha,beta"
    );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/application.properties", "src/main/resources/application.properties" })
  void shouldUpdateMainProperties(String propertiesPath) {
    String folder = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(folder, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(new SeedProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/application.properties", "src/test/resources/application.properties" })
  void shouldUpdateTestProperties(String propertiesPath) {
    String folder = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(folder, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(new SeedProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  private static SpringProperties properties(SpringProperty property) {
    return new SpringProperties(List.of(property));
  }
}
