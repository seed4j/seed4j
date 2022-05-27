package tech.jhipster.lite.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.*;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.BuildToolType;
import tech.jhipster.lite.generator.project.domain.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class BuildToolDomainServiceTest {

  @Mock
  MavenService mavenService;

  @Mock
  GradleService gradleService;

  @InjectMocks
  BuildToolDomainService buildToolDomainService;

  @Nested
  class MavenTest {

    @Test
    void shouldAddParent() {
      Project project = tmpProjectWithPomXml();
      Parent parent = getParent();

      buildToolDomainService.addParent(project, parent);

      verify(mavenService).addParent(project, parent);
    }

    @Test
    void shouldAddDependency() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependency(project, dependency);

      verify(mavenService).addDependency(project, dependency);
    }

    @Test
    void shouldDeleteDependency() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();

      buildToolDomainService.deleteDependency(project, dependency);

      verify(mavenService).deleteDependency(project, dependency);
    }

    @Test
    void shouldAddDependencyWithExclusion() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();
      List<Dependency> exclusions = getExclusions();

      buildToolDomainService.addDependency(project, dependency, exclusions);

      verify(mavenService).addDependency(project, dependency, exclusions);
    }

    @Test
    void shouldAddDependencyManagement() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependencyManagement(project, dependency);

      verify(mavenService).addDependencyManagement(project, dependency);
    }

    @Test
    void shouldAddPlugin() {
      Project project = tmpProjectWithPomXml();
      Plugin plugin = getPlugin();

      buildToolDomainService.addPlugin(project, plugin);

      verify(mavenService).addPlugin(project, plugin);
    }

    @Test
    void shouldAddPluginManagement() {
      Project project = tmpProjectWithPomXml();
      Plugin plugin = getPlugin();

      buildToolDomainService.addPluginManagement(project, plugin);

      verify(mavenService).addPluginManagement(project, plugin);
    }

    @Test
    void shouldAddProperty() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.addProperty(project, "testcontainers.version", "0.0.0");

      verify(mavenService).addProperty(project, "testcontainers.version", "0.0.0");
    }

    @Test
    void shouldAddRepository() {
      Project project = tmpProjectWithPomXml();
      Repository repository = getRepository();

      buildToolDomainService.addRepository(project, repository);

      verify(mavenService).addRepository(project, repository);
    }

    @Test
    void shouldAddPluginRepository() {
      Project project = tmpProjectWithPomXml();
      Repository repository = getRepository();

      buildToolDomainService.addPluginRepository(project, repository);

      verify(mavenService).addPluginRepository(project, repository);
    }

    @Test
    void shouldInit() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.init(project, BuildToolType.MAVEN);

      verify(mavenService).initJava(project);
    }

    @Test
    void shouldInitWithoutExistingPomXml() {
      Project project = tmpProject();

      buildToolDomainService.init(project, BuildToolType.MAVEN);

      verify(mavenService).initJava(project);
    }

    @Test
    void shouldGetVersion() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getVersion(project, "spring-boot");

      verify(mavenService).getVersion("spring-boot");
    }

    @Test
    void shouldGetGroup() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getGroup(project);

      verify(mavenService).getGroupId(project.getFolder());
    }

    @Test
    void shouldGetName() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getName(project);

      verify(mavenService).getName(project.getFolder());
    }
  }

  @Nested
  class GradleTest {

    @Test
    void shouldNotInit() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.init(project, BuildToolType.GRADLE)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotInitWithExistingGradle() {
      Project project = tmpProjectWithBuildGradle();

      assertThatThrownBy(() -> buildToolDomainService.init(project, BuildToolType.GRADLE)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldGetGroup() {
      Project project = tmpProjectWithBuildGradle();

      buildToolDomainService.getGroup(project);

      verify(gradleService).getGroup(project.getFolder());
    }

    @Test
    void shouldAddDependency() {
      Project project = tmpProjectWithBuildGradle();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependency(project, dependency);

      verify(gradleService).addDependency(project, dependency);
    }

    @Test
    void shouldDeleteDependency() {
      Project project = tmpProjectWithBuildGradle();
      Dependency dependency = getDependency();

      buildToolDomainService.deleteDependency(project, dependency);

      verify(gradleService).deleteDependency(project, dependency);
    }

    @Test
    void shouldAddRepository() {
      Project project = tmpProjectWithBuildGradle();
      Repository repository = getRepository();

      buildToolDomainService.addRepository(project, repository);

      verify(gradleService).addRepository(project, repository);
    }
  }

  @Nested
  class NoBuildToolTest {

    @Test
    void shouldNotAddParent() {
      Project project = tmpProject();
      Parent parent = Parent
        .builder()
        .groupId("org.springframework.boot")
        .artifactId("spring-boot-starter-parent")
        .version("0.0.0")
        .build();

      assertThatThrownBy(() -> buildToolDomainService.addParent(project, parent)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependency() {
      Project project = tmpProject();
      Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

      assertThatThrownBy(() -> buildToolDomainService.addDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependencyWithExclusion() {
      Project project = tmpProject();
      Dependency dependency = getDependency();
      List<Dependency> exclusions = getExclusions();

      assertThatThrownBy(() -> buildToolDomainService.addDependency(project, dependency, exclusions))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependencyManagement() {
      Project project = tmpProject();
      Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

      assertThatThrownBy(() -> buildToolDomainService.addDependencyManagement(project, dependency))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddPlugin() {
      Project project = tmpProject();
      Plugin plugin = getPlugin();

      assertThatThrownBy(() -> buildToolDomainService.addPlugin(project, plugin)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddPluginManagement() {
      Project project = tmpProject();
      Plugin plugin = getPlugin();

      assertThatThrownBy(() -> buildToolDomainService.addPluginManagement(project, plugin)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddProperty() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.addProperty(project, "testcontainers", "0.0.0"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddRepository() {
      Project project = tmpProject();
      Repository repository = getRepository();

      assertThatThrownBy(() -> buildToolDomainService.addRepository(project, repository)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddPluginRepository() {
      Project project = tmpProject();
      Repository repository = getRepository();

      assertThatThrownBy(() -> buildToolDomainService.addPluginRepository(project, repository))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetVersion() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getVersion(project, "spring-boot")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetGroup() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getGroup(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getName(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotDeleteDependency() {
      Project project = tmpProject();
      Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

      assertThatThrownBy(() -> buildToolDomainService.deleteDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  private Parent getParent() {
    return Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("0.0.0").build();
  }

  private Dependency getDependency() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
  }

  private List<Dependency> getExclusions() {
    return List.of(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-tomcat").build());
  }

  private Plugin getPlugin() {
    return Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
  }

  private Repository getRepository() {
    return Repository.builder().id("spring-milestone").url("https://repo.spring.io/milestone").build();
  }
}
