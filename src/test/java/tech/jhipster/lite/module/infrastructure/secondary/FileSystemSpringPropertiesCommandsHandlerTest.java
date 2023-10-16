package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestFileUtils.content;
import static tech.jhipster.lite.TestFileUtils.loadDefaultProperties;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemSpringPropertiesCommandsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Paths.get(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );
  private static final FileSystemSpringPropertiesCommandsHandler handler = new FileSystemSpringPropertiesCommandsHandler();

  @Test
  void shouldCreateDefaultMainPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(Paths.get(folder, "src/main/resources/config/application.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldCreateProfileMainPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springLocalMainProperty()));

    assertThat(content(Paths.get(folder, "src/main/resources/config/application-local.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldCreateDefaultTestPropertiesForProjectWithoutProperties() {
    String folder = TestFileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(Paths.get(folder, "src/test/resources/config/application.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/application.properties", "src/main/resources/application.properties" })
  void shouldUpdateMainProperties(String propertiesPath) {
    String folder = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(folder, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(new JHipsterProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/application.properties", "src/test/resources/application.properties" })
  void shouldUpdateTestProperties(String propertiesPath) {
    String folder = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(folder, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  private static SpringProperties properties(SpringProperty property) {
    return new SpringProperties(List.of(property));
  }
}
