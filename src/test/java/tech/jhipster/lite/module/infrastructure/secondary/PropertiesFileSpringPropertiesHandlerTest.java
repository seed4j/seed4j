package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestFileUtils.content;
import static tech.jhipster.lite.TestFileUtils.loadDefaultProperties;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;

@UnitTest
class PropertiesFileSpringPropertiesHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Paths.get(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );

  @Test
  void shouldCreateUnknownFile() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldAppendPropertyToFileWithProperties() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile))
      .contains("spring.application.name=JHLite")
      .endsWith("\nspringdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldReplaceExistingProperty() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("spring.application.name"), propertyValue("alpha"));

    assertThat(content(propertiesFile)).startsWith("spring.application.name=alpha").doesNotContain("spring.application.name=JHLite");
  }
}
