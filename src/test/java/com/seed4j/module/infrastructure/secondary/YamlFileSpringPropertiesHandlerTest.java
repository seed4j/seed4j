package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javaproperties.Comment;
import com.seed4j.shared.error.domain.GeneratorException;
import java.nio.file.Path;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class YamlFileSpringPropertiesHandlerTest {

  public static final Path EXISTING_SPRING_CONFIGURATION = Path.of(
    "src/test/resources/projects/project-with-spring-application-yaml/application.yml"
  );

  @Nested
  class SetValue {

    @Test
    void shouldCreateUnknownFile() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

      assertThat(content(yamlFile)).contains(
          """
          springdoc:
            swagger-ui:
              operationsSorter: alpha
          """
        );
    }

    @Test
    void shouldAppendPropertyToFileWithProperties() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

      assertThat(content(yamlFile)).contains(
          """
          spring:
            application:
              name: seed4j # This is the name of the application

          # Logging
          logging:
            level:
              com.seed4j: INFO
          springdoc:
            swagger-ui:
              operationsSorter: alpha
          """
        );
    }

    @Test
    void shouldKeepExistingOrderWhenReplacingAProperty() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(
        Path.of("src/test/resources/projects/project-with-spring-application-yaml/more-complex-application.yml"),
        yamlFile
      );
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("spring.datasource.driver-class-name"), propertyValue("org.postgresql.Driver"));

      assertThat(content(yamlFile)).contains(
          """
          spring:
            data:
              jpa:
                repositories:
                  bootstrap-mode: deferred
            datasource:
              driver-class-name: org.postgresql.Driver
              hikari:
                auto-commit: false
                poolName: Hikari
              password: ''
              type: com.zaxxer.hikari.HikariDataSource
              url: jdbc:postgresql://localhost:5432/myapp
              username: myapp
          """
        );
    }

    @Test
    void shouldRespectProjectIndentation() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.from(4));

      handler.setValue(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

      assertThat(content(yamlFile)).contains(
          """
          springdoc:
              swagger-ui:
                  operationsSorter: alpha
          """
        );
    }

    @Test
    void shouldPreserveExistingComments() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.from(4));

      handler.setValue(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));

      assertThat(content(yamlFile)).contains(
          """
          # Logging
          logging:
          """
        );
    }

    @Test
    void shouldReplaceExistingProperty() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("spring.application.name"), propertyValue("alpha"));

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
              name: seed4j
          """
        );
    }

    @Test
    void shouldHandleEscapedKeyWithDot() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(
        propertyKey("kafka.consumer.'[key.deserializer]'"),
        propertyValue("org.apache.kafka.common.serialization.StringDeserializer")
      );

      assertThat(content(yamlFile)).contains(
          """
          kafka:
            consumer:
              '[key.deserializer]': org.apache.kafka.common.serialization.StringDeserializer
          """
        );
    }

    @Test
    void shouldHandleBooleanValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.enabled"), propertyValue(true));

      assertThat(content(yamlFile)).contains(
          """
          coverage:
            enabled: true
          """
        );
    }

    @Test
    void shouldHandleIntegerValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.count"), propertyValue(10));

      assertThat(content(yamlFile)).contains(
          """
          coverage:
            count: 10
          """
        );
    }

    @Test
    void shouldHandleLongValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.count"), propertyValue(10L));

      assertThat(content(yamlFile)).contains(
          """
          coverage:
            count: 10
          """
        );
    }

    @Test
    void shouldHandleDoubleValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.count"), propertyValue(10.5));

      assertThat(content(yamlFile)).contains(
          """
          coverage:
            count: 10.5
          """
        );
    }

    @Test
    void shouldHandleFloatValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.count"), propertyValue(10.5f));

      assertThat(content(yamlFile)).contains(
          """
          coverage:
            count: 10.5
          """
        );
    }

    @Test
    void shouldHandleCollectionValue() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setValue(propertyKey("coverage.count"), propertyValue(10, 50));

      assertThat(content(yamlFile)).contains(
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
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);
      handler.setValue(propertyKey("coverage.count"), propertyValue(10));

      assertThatExceptionOfType(GeneratorException.class).isThrownBy(() ->
        handler.setValue(propertyKey("coverage.count.value"), propertyValue(10))
      );
    }
  }

  @Nested
  class SetComment {

    @Test
    void shouldAddCommentToExistingPropertyKey() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setComment(propertyKey("spring.application.name"), new Comment("Application Name"));

      assertThat(content(yamlFile)).contains(
          """
          spring:
            application:
              # Application Name
              name: seed4j # This is the name of the application
          """
        );
    }

    @Test
    void shouldAddMultiLineCommentToExistingPropertyKey() {
      Path yamlFile = Path.of(TestFileUtils.tmpDirForTest(), "src/main/resources/application.yml");
      loadDefaultProperties(EXISTING_SPRING_CONFIGURATION, yamlFile);
      YamlFileSpringPropertiesHandler handler = new YamlFileSpringPropertiesHandler(yamlFile, Indentation.DEFAULT);

      handler.setComment(
        propertyKey("spring.application.name"),
        new Comment(
          """
          This is a
          multiline
          comment
          """
        )
      );

      assertThat(content(yamlFile)).contains(
          """
          spring:
            application:
              # This is a
              # multiline
              # comment
              name: seed4j # This is the name of the application
          """
        );
    }
  }
}
