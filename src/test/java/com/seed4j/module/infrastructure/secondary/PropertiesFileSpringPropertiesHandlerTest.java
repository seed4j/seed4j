package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.module.domain.SeedModule.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertiesFileSpringPropertiesHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Path.of(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );

  @Test
  void shouldCreateUnknownFile() {
    Path propertiesFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldAppendPropertyToFileWithProperties() {
    Path propertiesFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile))
      .contains("spring.application.name=seed4j")
      .endsWith("\nspringdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldReplaceExistingProperty() {
    Path propertiesFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("spring.application.name"), propertyValue("alpha"));

    assertThat(content(propertiesFile)).startsWith("spring.application.name=alpha").doesNotContain("spring.application.name=seed4j");
  }
}
