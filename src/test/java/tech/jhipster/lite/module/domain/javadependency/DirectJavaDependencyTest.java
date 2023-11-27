package tech.jhipster.lite.module.domain.javadependency;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

@UnitTest
class DirectJavaDependencyTest {

  @Test
  void shouldAddUnknownMinimalDependency() {
    JavaBuildCommands commands = changes().build();

    assertThat(commands.get()).containsExactly(new AddDirectJavaDependency(defaultVersionDependency()));
  }

  @Test
  void shouldAddUnknownFullDependency() {
    JavaBuildCommands commands = changes().dependency(optionalTestDependency()).build();

    assertThat(commands.get()).containsExactly(new SetVersion(springBootVersion()), new AddDirectJavaDependency(optionalTestDependency()));
  }

  @Test
  void shouldNotUpdateExistingOptionalTestDependency() {
    JavaBuildCommands commands = changes().dependency(optionalTestDependency()).projectDependencies(projectJavaDependencies()).build();

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldNotUpdateExistingDefaultVersionDependency() {
    ProjectJavaDependencies projectJavaDependencies = ProjectJavaDependencies
      .builder()
      .versions(projectVersions())
      .dependenciesManagements(null)
      .dependencies(new JavaDependencies(List.of(defaultVersionDependency())));

    JavaBuildCommands commands = changes().projectDependencies(projectJavaDependencies).build();

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldUpgradeDependencyOptionality() {
    ProjectJavaDependencies projectJavaDependencies = ProjectJavaDependencies
      .builder()
      .versions(projectVersions())
      .dependenciesManagements(null)
      .dependencies(new JavaDependencies(List.of(optionalSpringBootDependency())));

    JavaBuildCommands commands = changes().projectDependencies(projectJavaDependencies).build();

    assertThat(commands.get())
      .containsExactly(
        new RemoveDirectJavaDependency(optionalSpringBootDependency().id()),
        new AddDirectJavaDependency(defaultVersionDependency())
      );
  }

  @Test
  void shouldNotDowngradeDependencyOptionality() {
    ProjectJavaDependencies projectJavaDependencies = ProjectJavaDependencies
      .builder()
      .versions(projectVersions())
      .dependenciesManagements(null)
      .dependencies(new JavaDependencies(List.of(defaultVersionDependency())));

    JavaBuildCommands commands = changes().dependency(optionalSpringBootDependency()).projectDependencies(projectJavaDependencies).build();

    assertThat(commands.get()).isEmpty();
  }

  private JavaDependency optionalSpringBootDependency() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter").optional().build();
  }

  @Test
  void shouldUpdateDependencyVersion() {
    JavaDependencyVersion updatedVersion = new JavaDependencyVersion("spring-boot", "1.2.4");
    JavaDependenciesVersions currentVersions = new JavaDependenciesVersions(List.of(updatedVersion));

    JavaBuildCommands commands = changes()
      .dependency(optionalTestDependency())
      .currentVersions(currentVersions)
      .projectDependencies(projectJavaDependencies())
      .build();

    assertThat(commands.get()).containsExactly(new SetVersion(updatedVersion));
  }

  @Test
  void shouldUpgradeDependencyScopeAndOptionality() {
    JavaDependency upgraded = optionalTestDependencyBuilder().optional(false).scope(null).build();

    JavaBuildCommands commands = changes().dependency(upgraded).projectDependencies(projectJavaDependencies()).build();

    assertThat(commands.get()).containsExactly(new RemoveDirectJavaDependency(upgraded.id()), new AddDirectJavaDependency(upgraded));
  }

  @Test
  void shouldKeepVersionFromNewDependency() {
    JavaDependency upgraded = optionalTestDependency();

    JavaBuildCommands commands = changes().dependency(upgraded).projectDependencies(projectDependenciesWithoutJunitVersion()).build();

    assertThat(commands.get()).containsExactly(new RemoveDirectJavaDependency(upgraded.id()), new AddDirectJavaDependency(upgraded));
  }

  @Test
  void shouldUpdateVersionSlug() {
    JavaDependency upgraded = optionalTestDependencyBuilder().versionSlug("updated-junit-jupiter").build();
    JavaDependencyVersion updatedJunitVersion = new JavaDependencyVersion("updated-junit-jupiter", "1.2.4");
    JavaDependenciesVersions currentVersions = new JavaDependenciesVersions(List.of(springBootVersion(), updatedJunitVersion));

    JavaBuildCommands commands = changes()
      .dependency(upgraded)
      .currentVersions(currentVersions)
      .projectDependencies(projectDependenciesWithoutJunitVersion())
      .build();

    assertThat(commands.get())
      .containsExactly(
        new SetVersion(updatedJunitVersion),
        new RemoveDirectJavaDependency(upgraded.id()),
        new AddDirectJavaDependency(upgraded)
      );
  }

  private ProjectJavaDependencies projectDependenciesWithoutJunitVersion() {
    return ProjectJavaDependencies
      .builder()
      .versions(projectVersions())
      .dependenciesManagements(projectDependenciesManagement())
      .dependencies(noJunitVersionInCurrentProject());
  }

  private JavaDependencies noJunitVersionInCurrentProject() {
    return new JavaDependencies(List.of(junitWithoutVersion()));
  }

  @Test
  void shouldKeepVersionFromProject() {
    JavaDependency upgraded = junitWithoutVersion();

    JavaBuildCommands commands = changes().dependency(upgraded).projectDependencies(projectJavaDependencies()).build();

    assertThat(commands.get()).isEmpty();
  }

  private JavaDependency junitWithoutVersion() {
    return optionalTestDependencyBuilder().versionSlug((String) null).build();
  }

  private ProjectJavaDependencies projectJavaDependencies() {
    return ProjectJavaDependencies.builder().versions(projectVersions()).dependenciesManagements(null).dependencies(projectDependencies());
  }

  private ProjectJavaDependenciesVersions projectVersions() {
    return new ProjectJavaDependenciesVersions(List.of(springBootVersion()));
  }

  private JavaDependencies projectDependenciesManagement() {
    return new JavaDependencies(List.of(SpringBootDependencyManager.springBootDependencyManagement()));
  }

  private JavaDependencies projectDependencies() {
    return new JavaDependencies(List.of(optionalTestDependency()));
  }

  private static ChangesBuilder changes() {
    return new ChangesBuilder();
  }

  private static class ChangesBuilder {

    private JavaDependency dependency = defaultVersionDependency();
    private JavaDependenciesVersions currentVersions = currentJavaDependenciesVersion();
    private ProjectJavaDependencies projectDependencies = ProjectJavaDependencies.EMPTY;

    public ChangesBuilder dependency(JavaDependency dependency) {
      this.dependency = dependency;

      return this;
    }

    public ChangesBuilder currentVersions(JavaDependenciesVersions currentVersions) {
      this.currentVersions = currentVersions;

      return this;
    }

    public ChangesBuilder projectDependencies(ProjectJavaDependencies projectDependencies) {
      this.projectDependencies = projectDependencies;

      return this;
    }

    public JavaBuildCommands build() {
      return new DirectJavaDependency(dependency).changeCommands(currentVersions, projectDependencies);
    }
  }
}
