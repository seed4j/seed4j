package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.content;
import static com.seed4j.TestFileUtils.loadDefaultProperties;
import static com.seed4j.module.domain.Seed4JModule.comment;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.javaproperties.SpringComment;
import com.seed4j.module.domain.javaproperties.SpringComments;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class FileSystemSpringCommentsCommandsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Path.of(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );
  private static final FileSystemSpringCommentsCommandsHandler handler = new FileSystemSpringCommentsCommandsHandler();

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/application.properties", "src/main/resources/application.properties" })
  void shouldCommentMainProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain("spring.application.name"));

    assertThat(content(propertiesFile)).contains(
      """
      # This is a comment
      spring.application.name"""
    );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/bootstrap.properties", "src/main/resources/bootstrap.properties" })
  void shouldCommentMainBootStrapProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMainBootstrap("spring.application.name"));

    assertThat(content(propertiesFile)).contains(
      """
      # This is a comment
      spring.application.name"""
    );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/application.properties", "src/test/resources/application.properties" })
  void shouldCommentTestProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnTest("logging.level.com.seed4j"));

    assertThat(content(propertiesFile)).contains(
      """
      # This is a comment
      logging.level.com.seed4j"""
    );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/bootstrap.properties", "src/test/resources/bootstrap.properties" })
  void shouldCommentTestBootStrapProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnTestBootstrap("logging.level.com.seed4j"));

    assertThat(content(propertiesFile)).contains(
      """
      # This is a comment
      logging.level.com.seed4j"""
    );
  }

  @Test
  void shouldNotCommentWhenFileNotExists() {
    String path = TestFileUtils.tmpDirForTest();

    handler.handle(folder(path), commentOnMain("spring.application.name"));

    Throwable thrown = catchThrowable(() -> content(Path.of(path, "src/main/resources/config/application.properties")));
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  @Test
  void shouldNotCommentWhenPropertyKeyDoesNotExists() {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, "src/main/resources/config/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain("springdoc.swagger-ui.operationsSorter"));

    assertThat(content(propertiesFile)).doesNotContain(
      """
      # This is a comment
      springdoc.swagger-ui.operationsSorter"""
    );
  }

  @ParameterizedTest
  @ValueSource(strings = { "spring.application.name", "logging.level.com.seed4j" })
  void shouldOverwritePreviousComment(String propertyKey) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Path.of(path, "src/main/resources/config/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain(propertyKey));
    handler.handle(folder(path), commentOnMain(propertyKey));

    assertThat(content(propertiesFile)).doesNotContain(
      """
        # This is a comment
        # This is a comment
        """
        + propertyKey
    );
    assertThat(content(propertiesFile)).contains(
      """
        # This is a comment
        """
        + propertyKey
    );
  }

  @Test
  void shouldNotOrganizePropertiesWhenFileNotExists() {
    String path = TestFileUtils.tmpDirForTest();

    handler.handle(folder(path), emptySpringComments());

    Throwable thrown = catchThrowable(() -> content(Path.of(path, "src/main/resources/config/application.properties")));
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  private Seed4JProjectFolder folder(String path) {
    return new Seed4JProjectFolder(path);
  }

  private SpringComments commentOnMain(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment.builder(SpringPropertyType.MAIN_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnMainBootstrap(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment.builder(SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnTest(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment.builder(SpringPropertyType.TEST_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnTestBootstrap(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment.builder(SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments emptySpringComments() {
    return new SpringComments(List.of());
  }
}
