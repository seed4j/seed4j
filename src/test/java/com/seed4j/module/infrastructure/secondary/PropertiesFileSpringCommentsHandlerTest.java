package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.*;
import static com.seed4j.module.domain.JHipsterModule.comment;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.seed4j.UnitTest;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertiesFileSpringCommentsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Path.of(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );

  @Test
  void shouldNotCommentWhenFileDoesNotExist() {
    String path = tmpDirForTest();
    Path propertiesFile = Path.of(path, "src/main/resources/config/application.properties");

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("spring.application.name"), comment("This is a comment"));

    Throwable thrown = catchThrowable(() -> content(Path.of(path, "src/main/resources/config/application.properties")));
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  @Test
  void shouldNotCommentWhenKeyDoesNotExist() {
    Path propertiesFile = Path.of(tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("foo.bar"), comment("This is a comment"));

    assertThat(content(propertiesFile)).doesNotContain("This is a comment");
  }

  @Test
  void shouldAddCommentToFirstPartiallyMatchingKey() {
    Path propertiesFile = Path.of(tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("logging.level"), comment("Logging configuration"));

    assertThat(content(propertiesFile)).contains(
        """
        # Logging configuration
        logging.level.com.seed4j=INFO"""
      );
  }

  @Test
  void shouldAddSingleLineCommentForExistingProperty() {
    Path propertiesFile = Path.of(tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("spring.application.name"), comment("This is a comment"));

    assertThat(contentNormalizingNewLines(propertiesFile)).contains(
        """
        # This is a comment
        spring.application.name=seed4j
        """
      );
  }

  @Test
  void shouldAddMultilineCommentForExistingProperty() {
    Path propertiesFile = Path.of(tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(
        propertyKey("spring.application.name"),
        comment(
          """
          This is a
          multiline
          comment
          """
        )
      );

    assertThat(contentNormalizingNewLines(propertiesFile)).contains(
        """
        # This is a
        # multiline
        # comment
        spring.application.name=seed4j
        """
      );
  }
}
