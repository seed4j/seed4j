package tech.jhipster.lite.generator.project.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithBuildGradle;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.lite.common.domain.WordUtils.CRLF;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.UnauthorizedValueException;

@UnitTest
class ProjectTest {

  @Nested
  class BuildTest {

    @Test
    void shouldBuildMinimalProject() {
      String folder = tmpDirForTest();
      Project project = minimalBuilder(folder).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
      assertThat(project.getConfig()).isEmpty();

      assertThat(project.getRemoteUrl()).isEmpty();
      assertThat(project.getLanguage()).isEmpty();
      assertThat(project.getBuildTool()).isEmpty();
      assertThat(project.getServer()).isEmpty();
      assertThat(project.getClient()).isEmpty();

      assertThat(project.getDatabase()).isEmpty();
      assertThat(project.getDatabaseMigration()).isEmpty();
      assertThat(project.getCache()).isEmpty();
      assertThat(project.getSecurity()).isEmpty();
    }

    @Test
    void shouldBuildWithNullConfig() {
      String folder = tmpDirForTest();
      Project project = minimalBuilder(folder).config(null).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
      assertThat(project.getConfig()).isEmpty();
    }

    @Test
    void shouldBuildFullProject() {
      String folder = tmpDirForTest();
      Project project = fullBuilder(folder).config(Map.of(PROJECT_NAME, "JHipster Lite")).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
      assertThat(project.getConfig()).isEqualTo(Map.of(PROJECT_NAME, "JHipster Lite"));

      assertThat(project.getRemoteUrl()).contains("https://github.com/jhipster/jhipster-lite");
      assertThat(project.getLanguage()).contains(LanguageType.JAVA);
      assertThat(project.getBuildTool()).contains(BuildToolType.MAVEN);
      assertThat(project.getServer()).contains(ServerFrameworkType.SPRING);
      assertThat(project.getClient()).contains(ClientFrameworkType.ANGULAR);

      assertThat(project.getDatabase()).contains(DatabaseType.POSTGRESQL);
      assertThat(project.getDatabaseMigration()).contains(DatabaseMigrationType.LIQUIBASE);
      assertThat(project.getCache()).contains(CacheType.EHCACHE);
      assertThat(project.getSecurity()).contains(SecurityType.JWT);
    }

    @Test
    void shouldNotBuildWithNullFolder() {
      Project.ProjectBuilder builder = Project.builder().folder(null);

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("folder");
    }

    @Test
    void shouldNotBuildWithRootFolder() {
      Project.ProjectBuilder builder = Project.builder().folder("/");

      assertThatThrownBy(builder::build).isExactlyInstanceOf(UnauthorizedValueException.class).hasMessageContaining("generation folder");
    }

    @Test
    void shouldNotBuildWithBlankPath() {
      Project.ProjectBuilder builder = Project.builder().folder(" ");

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("folder");
    }
  }

  @Nested
  class DetectEndOfLineTest {

    @Test
    void shouldBuildProjectCrlf() throws IOException {
      String folder = tmpDirForTest();

      Files.createDirectory(Path.of(folder));
      Files.writeString(Path.of(folder, "file.txt"), "my file with \r\ncrlf", StandardOpenOption.CREATE);

      Project project = minimalBuilder(folder).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(CRLF);
    }

    @Test
    void shouldBuildProjectLf() throws IOException {
      String folder = tmpDirForTest();

      Files.createDirectory(Path.of(folder));
      Files.writeString(Path.of(folder, "file.txt"), "my file with \nlf", StandardOpenOption.CREATE);

      Project project = minimalBuilder(folder).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
    }

    @Test
    void shouldBuildProjectNoNewline() throws IOException {
      String folder = tmpDirForTest();

      Files.createDirectory(Path.of(folder));
      Files.writeString(Path.of(folder, "file.txt"), "my file with only 1 line", StandardOpenOption.CREATE);

      Project project = minimalBuilder(folder).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
    }

    @Test
    void shouldBuildProjectDefault() {
      String folder = tmpDirForTest();

      Project project = minimalBuilder(folder).build();

      assertThat(project.getFolder()).isEqualTo(folder);
      assertThat(project.getEndOfLine()).isEqualTo(LF);
    }
  }

  @Nested
  class GetConfigTest {

    @Test
    void shouldGetConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).config(Map.of(PROJECT_NAME, "JHipster Lite")).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Lite");

      assertThat(project.getConfig(BASE_NAME)).isEmpty();
      assertThat(project.getConfig("projectname")).isEmpty();
      assertThat(project.getConfig("test")).isEmpty();
    }
  }

  @Nested
  class AddConfigTest {

    @Test
    void shouldAddConfigInEmptyConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).build();

      assertThat(project.getConfig("apero")).isEmpty();

      project.addConfig("apero", "beer");
      assertThat(project.getConfig("apero")).contains("beer");

      project.addConfig("apero", "chips");
      assertThat(project.getConfig("apero")).contains("beer");
    }

    @Test
    void shouldAddConfigInExistingConfig() {
      // Given
      String path = FileUtils.tmpDirForTest();
      Map<String, Object> config = new HashMap<>(Map.of(PROJECT_NAME, "JHipster Lite"));
      Project project = Project.builder().folder(path).config(config).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Lite");
      assertThat(project.getConfig("apero")).isEmpty();

      // When + Then
      project.addConfig("apero", "beer");
      assertThat(project.getConfig("apero")).contains("beer");

      // When + Then
      project.addConfig(PROJECT_NAME, "chips");
      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Lite");
    }

    @Test
    void shouldNotAddConfigWithBadBaseName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> project.addConfig(BASE_NAME, "jhipster with space"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining(BASE_NAME);
    }

    @Test
    void shouldNotAddConfigWithBadPackageName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> project.addConfig(PACKAGE_NAME, "tech jhipster lite"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining(PACKAGE_NAME);
    }

    @Test
    void shouldAddDefaultConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).build();

      project.addDefaultConfig(BASE_NAME);

      assertThat(project.getConfig(BASE_NAME)).contains("jhipster");
    }

    @Test
    void shouldNotAddDefaultConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).build();

      project.addDefaultConfig("apero");

      assertThat(project.getConfig("apero")).isEmpty();
    }
  }

  @Nested
  class PackageNameTest {

    @Test
    void shouldGetPackageName() {
      Project project = tmpProject();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.lite");

      assertThat(project.getPackageName()).contains("tech.jhipster.lite");
    }

    @Test
    void shouldNotGetPackageNameWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getPackageName()).isEmpty();
    }

    @Test
    void shouldGetPackageNamePath() {
      Project project = tmpProject();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.lite");

      assertThat(project.getPackageNamePath()).contains(getPath("tech/jhipster/lite"));
    }

    @Test
    void shouldNotGetPackageNamePathForDefault() {
      Project project = tmpProject();

      assertThat(project.getPackageNamePath()).isEmpty();
    }
  }

  @Nested
  class GetStringConfigTest {

    @Test
    void shouldGetStringConfig() {
      Project project = tmpProject();

      project.addConfig("apero", "beer");

      assertThat(project.getStringConfig("apero")).contains("beer");
    }

    @Test
    void shouldGetStringConfigWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getStringConfig("apero")).isEmpty();
    }

    @Test
    void shouldNotGetStringConfig() {
      Project project = tmpProject();

      project.addConfig("apero", List.of("beer"));

      assertThatThrownBy(() -> project.getStringConfig("apero"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("apero");
    }
  }

  @Nested
  class GetIntegerConfigTest {

    @Test
    void shouldGetIntegerConfig() {
      Project project = tmpProject();

      project.addConfig("serverPort", 1337);

      assertThat(project.getIntegerConfig("serverPort")).contains(1337);
    }

    @Test
    void shouldGetIntegerConfigWithEmpty() {
      Project project = tmpProject();

      assertThat(project.getIntegerConfig("serverPort")).isEmpty();
    }

    @Test
    void shouldNotGetIntegerConfig() {
      Project project = tmpProject();

      project.addConfig("serverPort", List.of(1337));

      assertThatThrownBy(() -> project.getIntegerConfig("serverPort"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("serverPort");
    }
  }

  @Nested
  class GetServerPortTest {

    @Test
    void shouldGetDefaultServerPort() {
      Project project = tmpProject();
      assertThat(project.getServerPort()).isEqualTo(8080);
    }

    @Test
    void shouldGetPresetServerPort() {
      Project project = tmpProject();
      project.addConfig("serverPort", 1337);
      assertThat(project.getServerPort()).isEqualTo(1337);
    }

    @Test
    void shouldGetDefaultServerPortForInvalidProperty() {
      Project project = tmpProject();

      project.addConfig("serverPort", List.of(1337));

      assertThat(project.getServerPort()).isEqualTo(8080);
    }
  }

  @Test
  void shouldBeMavenProject() throws Exception {
    Project project = Project.builder().folder(tmpDirForTest()).build();
    TestUtils.copyPomXml(project);

    assertThat(project.isMavenProject()).isTrue();
  }

  @Test
  void shouldNotBeMavenProjectWithGradle() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isMavenProject()).isFalse();
  }

  @Test
  void shouldNotBeMavenProjectWithoutBuildTool() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isMavenProject()).isFalse();
  }

  @Test
  void shouldBeGradleProject() throws Exception {
    Project project = Project.builder().folder(tmpDirForTest()).build();
    TestUtils.copyBuildGradle(project);

    assertThat(project.isGradleProject()).isTrue();
  }

  @Test
  void shouldNotBeGradleProjectWithMaven() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isGradleProject()).isFalse();
  }

  @Test
  void shouldNotBeGradleProjectWithoutBuildTool() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isGradleProject()).isFalse();
  }

  @Test
  void shouldCheckBuildToolWithException() {
    Project project = tmpProject();
    assertThatThrownBy(project::checkBuildTool).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldCheckBuildToolWithMaven() {
    Project project = tmpProjectWithPomXml();
    assertThatCode(project::checkBuildTool).doesNotThrowAnyException();
  }

  @Test
  void shouldCheckBuildToolWithGradle() throws Exception {
    Project project = tmpProjectWithBuildGradle();
    assertThatCode(project::checkBuildTool).doesNotThrowAnyException();
  }

  private Project.ProjectBuilder minimalBuilder(String tmpFolder) {
    return Project.builder().folder(tmpFolder);
  }

  private Project.ProjectBuilder fullBuilder(String tmpFolder) {
    return minimalBuilder(tmpFolder)
      .remoteUrl("https://github.com/jhipster/jhipster-lite")
      .language(LanguageType.JAVA)
      .buildTool(BuildToolType.MAVEN)
      .server(ServerFrameworkType.SPRING)
      .client(ClientFrameworkType.ANGULAR)
      .database(DatabaseType.POSTGRESQL)
      .databaseMigration(DatabaseMigrationType.LIQUIBASE)
      .cache(CacheType.EHCACHE)
      .security(SecurityType.JWT);
  }
}
