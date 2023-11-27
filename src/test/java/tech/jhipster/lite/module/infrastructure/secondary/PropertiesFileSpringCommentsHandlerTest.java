package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.comment;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;

@UnitTest
class PropertiesFileSpringCommentsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Paths.get(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );

  @Test
  void shouldNotCommentWhenFileDoesNotExist() {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, "src/main/resources/config/application.properties");

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("spring.application.name"), comment("This is a comment"));

    Throwable thrown = catchThrowable(() -> {
      content(Paths.get(path, "src/main/resources/config/application.properties"));
    });
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  @Test
  void shouldNotCommentWhenKeyDoesNotExist() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("foo.bar"), comment("This is a comment"));

    assertThat(content(propertiesFile)).doesNotContain("This is a comment");
  }

  @Test
  void shouldAddCommentToFirstPartiallyMatchingKey() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("logging.level"), comment("Logging configuration"));

    assertThat(content(propertiesFile))
      .contains(
        """
        # Logging configuration
        logging.level.tech.jhipster.lite=INFO"""
      );
  }

  @Test
  void shouldAddSingleLineCommentForExistingProperty() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile).set(propertyKey("spring.application.name"), comment("This is a comment"));

    assertThat(contentNormalizingNewLines(propertiesFile))
      .contains(
        """
        # This is a comment
        spring.application.name=JHLite
        """
      );
  }

  @Test
  void shouldAddMultilineCommentForExistingProperty() {
    Path propertiesFile = Paths.get(TestFileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    new PropertiesFileSpringCommentsHandler(propertiesFile)
      .set(
        propertyKey("spring.application.name"),
        comment(
          """
          This is a
          multiline
          comment
          """
        )
      );

    assertThat(contentNormalizingNewLines(propertiesFile))
      .contains(
        """
        # This is a
        # multiline
        # comment
        spring.application.name=JHLite
        """
      );
  }
}
