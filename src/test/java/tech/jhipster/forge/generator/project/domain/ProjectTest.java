package tech.jhipster.forge.generator.project.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.forge.TestUtils.tmpProjectDomain;
import static tech.jhipster.forge.common.domain.FileUtils.getPath;
import static tech.jhipster.forge.common.domain.FileUtils.tmpDirForTest;
import static tech.jhipster.forge.generator.project.domain.BuildToolType.GRADLE;
import static tech.jhipster.forge.generator.project.domain.BuildToolType.MAVEN;
import static tech.jhipster.forge.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.forge.generator.project.domain.Language.JAVA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.forge.TestUtils;
import tech.jhipster.forge.UnitTest;
import tech.jhipster.forge.common.domain.FileUtils;
import tech.jhipster.forge.error.domain.GeneratorException;
import tech.jhipster.forge.error.domain.MissingMandatoryValueException;
import tech.jhipster.forge.error.domain.UnauthorizedValueException;
import tech.jhipster.forge.generator.project.domain.added.BuildToolAdded;

@UnitTest
class ProjectTest {

  @Nested
  class Build {

    @Test
    void shouldBuildMinimalProject() {
      Project project = minimalBuilder().build();

      assertThat(project.getFolder()).isEqualTo(FileUtils.tmpDir());

      assertThat(project.getLanguage()).isEmpty();
      assertThat(project.getBuildTool()).isEmpty();
      assertThat(project.getServer()).isEmpty();
      assertThat(project.getClient()).isEmpty();
      assertThat(project.getConfig()).isEmpty();
    }

    @Test
    void shouldBuildWithNullConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).config(null).build();

      assertThat(project.getFolder()).isEqualTo(path);
      assertThat(project.getConfig()).isEqualTo(Map.of());
    }

    @Test
    void shouldBuildFullProject() {
      Project project = fullBuilder().build();

      assertThat(project.getFolder()).isEqualTo(FileUtils.tmpDir());
      assertThat(project.getLanguage()).contains(JAVA);
      assertThat(project.getBuildTool()).contains(MAVEN);
      assertThat(project.getServer()).contains(new Server(ServerFramework.SPRING));
      assertThat(project.getClient()).contains(new Client(ClientFramework.ANGULAR));
      assertThat(project.getConfig()).isEqualTo(Map.of(PROJECT_NAME, "JHipster Forge"));
    }

    @Test
    void shouldNotBuildWithNullFolder() {
      Project.ProjectBuilder builder = Project.builder().folder(null);

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("folder");
    }

    @Test
    void shouldNotBuildWithBlankPath() {
      Project.ProjectBuilder builder = Project.builder().folder(" ");

      assertThatThrownBy(builder::build).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("folder");
    }
  }

  @Nested
  class GetConfig {

    @Test
    void shouldGetConfig() {
      String path = FileUtils.tmpDirForTest();
      Project project = Project.builder().folder(path).config(Map.of(PROJECT_NAME, "JHipster Forge")).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");

      assertThat(project.getConfig(BASE_NAME)).isEmpty();
      assertThat(project.getConfig("projectname")).isEmpty();
      assertThat(project.getConfig("test")).isEmpty();
    }
  }

  @Nested
  class AddConfig {

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
      Map<String, Object> config = new HashMap<>(Map.of(PROJECT_NAME, "JHipster Forge"));
      Project project = Project.builder().folder(path).config(config).build();

      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");
      assertThat(project.getConfig("apero")).isEmpty();

      // When + Then
      project.addConfig("apero", "beer");
      assertThat(project.getConfig("apero")).contains("beer");

      // When + Then
      project.addConfig(PROJECT_NAME, "chips");
      assertThat(project.getConfig(PROJECT_NAME)).contains("JHipster Forge");
    }

    @Test
    void shouldNotAddConfigWithBadBaseName() {
      Project project = tmpProjectDomain();

      assertThatThrownBy(() -> project.addConfig(BASE_NAME, "jhipster with space"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining(BASE_NAME);
    }

    @Test
    void shouldNotAddConfigWithBadPackageName() {
      Project project = tmpProjectDomain();

      assertThatThrownBy(() -> project.addConfig(PACKAGE_NAME, "tech jhipster forge"))
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
  class PackageName {

    @Test
    void shouldGetPackageName() {
      Project project = tmpProjectDomain();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.forge");

      assertThat(project.getPackageName()).contains("tech.jhipster.forge");
    }

    @Test
    void shouldNotGetPackageNameWithEmpty() {
      Project project = tmpProjectDomain();

      assertThat(project.getPackageName()).isEmpty();
    }

    @Test
    void shouldGetPackageNamePath() {
      Project project = tmpProjectDomain();

      project.addConfig(PACKAGE_NAME, "tech.jhipster.forge");

      assertThat(project.getPackageNamePath()).contains(getPath("tech/jhipster/forge"));
    }

    @Test
    void shouldNotGetPackageNamePathForDefault() {
      Project project = tmpProjectDomain();

      assertThat(project.getPackageNamePath()).isEmpty();
    }
  }

  @Nested
  class GetStringConfig {

    @Test
    void shouldGetStringConfig() {
      Project project = tmpProjectDomain();

      project.addConfig("apero", "beer");

      assertThat(project.getStringConfig("apero")).contains("beer");
    }

    @Test
    void shouldGetStringConfigWithEmpty() {
      Project project = tmpProjectDomain();

      assertThat(project.getStringConfig("apero")).isEmpty();
    }

    @Test
    void shouldNotGetStringConfig() {
      Project project = tmpProjectDomain();

      project.addConfig("apero", List.of("beer"));

      assertThatThrownBy(() -> project.getStringConfig("apero"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("apero");
    }
  }

  @Nested
  class GetIntegerConfig {

    @Test
    void shouldGetIntegerConfig() {
      Project project = tmpProjectDomain();

      project.addConfig("serverPort", 1337);

      assertThat(project.getIntegerConfig("serverPort")).contains(1337);
    }

    @Test
    void shouldGetIntegerConfigWithEmpty() {
      Project project = tmpProjectDomain();

      assertThat(project.getIntegerConfig("serverPort")).isEmpty();
    }

    @Test
    void shouldNotGetIntegerConfig() {
      Project project = tmpProjectDomain();

      project.addConfig("serverPort", List.of(1337));

      assertThatThrownBy(() -> project.getIntegerConfig("serverPort"))
        .isExactlyInstanceOf(UnauthorizedValueException.class)
        .hasMessageContaining("serverPort");
    }
  }

  @Test
  void shouldBeMavenProject() throws Exception {
    Project project = Project.builder().folder(tmpDirForTest()).language(JAVA).buildTool(MAVEN).build();
    TestUtils.copyPomXml(project);

    assertThat(project.isMavenProject()).isTrue();
  }

  @Test
  void shouldNotBeMavenProjectWithGradle() {
    Project project = Project.builder().folder(tmpDirForTest()).language(JAVA).buildTool(GRADLE).build();

    assertThat(project.isMavenProject()).isFalse();
  }

  @Test
  void shouldNotBeMavenProjectWithoutBuildTool() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isMavenProject()).isFalse();
  }

  @Test
  void shouldBeGradleProject() throws Exception {
    Project project = Project.builder().folder(tmpDirForTest()).language(JAVA).buildTool(GRADLE).build();
    TestUtils.copyBuildGradle(project);

    assertThat(project.isGradleProject()).isTrue();
  }

  @Test
  void shouldNotBeGradleProjectWithMaven() {
    Project project = Project.builder().folder(tmpDirForTest()).language(JAVA).buildTool(MAVEN).build();

    assertThat(project.isGradleProject()).isFalse();
  }

  @Test
  void shouldNotBeGradleProjectWithoutBuildTool() {
    Project project = Project.builder().folder(tmpDirForTest()).build();

    assertThat(project.isGradleProject()).isFalse();
  }

  @Nested
  class AddBuildTool {

    @Test
    void shouldNotAddBuildToolWithNullBuildTool() {
      assertThatThrownBy(() -> minimalBuilder().build().addBuildTool(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("buildTool");
    }

    @Test
    void shouldNotChangeBuildTool() {
      assertThatThrownBy(() -> fullBuilder().build().addBuildTool(GRADLE))
        .isExactlyInstanceOf(GeneratorException.class)
        .hasMessageContaining("already existing build tool");
    }

    @Test
    void shouldAddBuildTool() {
      Project project = minimalBuilder().build();

      BuildToolAdded event = project.addBuildTool(MAVEN);

      assertThat(event).usingRecursiveComparison().isEqualTo(BuildToolAdded.of(project, MAVEN));
      assertThat(project.getBuildTool()).contains(MAVEN);
    }
  }

  private Project.ProjectBuilder fullBuilder() {
    return minimalBuilder()
      .language(JAVA)
      .buildTool(MAVEN)
      .server(new Server(ServerFramework.SPRING))
      .client(new Client(ClientFramework.ANGULAR))
      .config(Map.of(PROJECT_NAME, "JHipster Forge"));
  }

  private Project.ProjectBuilder minimalBuilder() {
    return Project.builder().folder(FileUtils.tmpDir());
  }
}
