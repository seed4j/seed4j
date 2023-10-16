package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class YamlFileSpringPropertiesHandlerTest {

  public static final Path EXISTING_SPRING_CONFIGURATION = Paths.get(
    "src/test/resources/projects/project-with-spring-application-yaml/application.yml"
  );

  @Test
  void shouldCreateUnknownFile() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

    assertThat(content(yamlFile))
      .contains(
        """
        springdoc:
          swagger-ui:
            operationsSorter: alpha
        """
      );
  }

  @Test
  void shouldAppendPropertyToFileWithProperties() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

    assertThat(content(yamlFile))
      .contains(
        """
        spring:
          application:
            name: JHLite
        logging:
          level:
            tech.jhipster.lite: INFO
        springdoc:
          swagger-ui:
            operationsSorter: alpha
        """
      );
  }

  @Test
  void shouldRespectProjectIndentation() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.from(4));

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

    assertThat(content(yamlFile))
      .contains(
        """
        springdoc:
            swagger-ui:
                operationsSorter: alpha
        """
      );
  }

  @Test
  void shouldReplaceExistingProperty() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("spring.application.name"), propertyValue("alpha"));

    assertThat(content(yamlFile))
      .contains(
        """
        spring:
          application:
            name: alpha
        """
      )
      .doesNotContain(
        """
        spring:
          application:
            name: JHLite
        """
      );
  }

  @Test
  void shouldHandleEscapedKeyWithDot() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(
      propertyKey("kafka.consumer.'[key.deserializer]'"),
      propertyValue("org.apache.kafka.common.serialization.StringDeserializer")
    );

    assertThat(content(yamlFile))
      .contains(
        """
        kafka:
          consumer:
            '[key.deserializer]': org.apache.kafka.common.serialization.StringDeserializer
        """
      );
  }

  @Test
  void shouldHandleBooleanValue() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("coverage.enabled"), propertyValue(true));

    assertThat(content(yamlFile))
      .contains(
        """
        coverage:
          enabled: true
        """
      );
  }

  @Test
  void shouldHandleNumericValue() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("coverage.count"), propertyValue(10));

    assertThat(content(yamlFile))
      .contains(
        """
        coverage:
          count: 10
        """
      );
  }

  @Test
  void shouldHandleCollectionValue() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

    handler.set(propertyKey("coverage.count"), propertyValue(10, 50));

    assertThat(content(yamlFile))
      .contains(
        """
        coverage:
          count:
          - 10
          - 50
        """
      );
  }

  @Test
  void shouldGenerateExceptionWhenConfigurationIsInconsistent() {
    Path yamlFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
    YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);
    handler.set(propertyKey("coverage.count"), propertyValue(10));

    assertThatExceptionOfType(GeneratorException.class)
      .isThrownBy(() -> handler.set(propertyKey("coverage.count.value"), propertyValue(10)));
  }
}
