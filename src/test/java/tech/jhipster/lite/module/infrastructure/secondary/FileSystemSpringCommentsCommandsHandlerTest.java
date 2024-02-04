package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tech.jhipster.lite.TestFileUtils.content;
import static tech.jhipster.lite.TestFileUtils.loadDefaultProperties;
import static tech.jhipster.lite.module.domain.JHipsterModule.comment;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javaproperties.SpringComment;
import tech.jhipster.lite.module.domain.javaproperties.SpringComments;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemSpringCommentsCommandsHandlerTest {

  public static final Path EXISTING_SPRING_PROPERTIES = Paths.get(
    "src/test/resources/projects/project-with-spring-application-properties/application.properties"
  );
  private static final FileSystemSpringCommentsCommandsHandler handler = new FileSystemSpringCommentsCommandsHandler();

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/application.properties", "src/main/resources/application.properties" })
  void shouldCommentMainProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain("spring.application.name"));

    assertThat(content(propertiesFile))
      .contains(
        """
        # This is a comment
        spring.application.name"""
      );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/bootstrap.properties", "src/main/resources/bootstrap.properties" })
  void shouldCommentMainBootStrapProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMainBootstrap("spring.application.name"));

    assertThat(content(propertiesFile))
      .contains(
        """
        # This is a comment
        spring.application.name"""
      );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/application.properties", "src/test/resources/application.properties" })
  void shouldCommentTestProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnTest("logging.level.tech.jhipster.lite"));

    assertThat(content(propertiesFile))
      .contains(
        """
        # This is a comment
        logging.level.tech.jhipster.lite"""
      );
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/bootstrap.properties", "src/test/resources/bootstrap.properties" })
  void shouldCommentTestBootStrapProperties(String propertiesPath) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, propertiesPath);
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnTestBootstrap("logging.level.tech.jhipster.lite"));

    assertThat(content(propertiesFile))
      .contains(
        """
        # This is a comment
        logging.level.tech.jhipster.lite"""
      );
  }

  @Test
  void shouldNotCommentWhenFileNotExists() {
    String path = TestFileUtils.tmpDirForTest();

    handler.handle(folder(path), commentOnMain("spring.application.name"));

    Throwable thrown = catchThrowable(() -> {
      content(Paths.get(path, "src/main/resources/config/application.properties"));
    });
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  @Test
  void shouldNotCommentWhenPropertyKeyDoesNotExists() {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, "src/main/resources/config/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain("springdoc.swagger-ui.operationsSorter"));

    assertThat(content(propertiesFile))
      .doesNotContain(
        """
        # This is a comment
        springdoc.swagger-ui.operationsSorter"""
      );
  }

  @ParameterizedTest
  @ValueSource(strings = { "spring.application.name", "logging.level.tech.jhipster.lite" })
  void shouldOverwritePreviousComment(String propertyKey) {
    String path = TestFileUtils.tmpDirForTest();
    Path propertiesFile = Paths.get(path, "src/main/resources/config/application.properties");
    loadDefaultProperties(EXISTING_SPRING_PROPERTIES, propertiesFile);

    handler.handle(folder(path), commentOnMain(propertyKey));
    handler.handle(folder(path), commentOnMain(propertyKey));

    assertThat(content(propertiesFile))
      .doesNotContain(
        """
        # This is a comment
        # This is a comment
        """ +
        propertyKey
      );
    assertThat(content(propertiesFile))
      .contains(
        """
        # This is a comment
        """ +
        propertyKey
      );
  }

  @Test
  void shouldNotOrganizePropertiesWhenFileNotExists() {
    String path = TestFileUtils.tmpDirForTest();

    handler.handle(folder(path), emptySpringComments());

    Throwable thrown = catchThrowable(() -> {
      content(Paths.get(path, "src/main/resources/config/application.properties"));
    });
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  private JHipsterProjectFolder folder(String path) {
    return new JHipsterProjectFolder(path);
  }

  private SpringComments commentOnMain(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment
          .builder(SpringPropertyType.MAIN_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnMainBootstrap(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment
          .builder(SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnTest(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment
          .builder(SpringPropertyType.TEST_PROPERTIES)
          .key(propertyKey(propertyKey))
          .comment(comment("This is a comment"))
          .build()
      )
    );
  }

  private SpringComments commentOnTestBootstrap(String propertyKey) {
    return new SpringComments(
      List.of(
        SpringComment
          .builder(SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES)
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
