package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.Seed4JModule.javaAnnotationProcessorDependency;
import static com.seed4j.module.domain.Seed4JModulesFixture.currentJavaDependenciesVersion;
import static com.seed4j.module.domain.Seed4JModulesFixture.hibernateAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.springBootVersion;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaAnnotationProcessorDependencyTest {

  @Test
  void shouldBuildWithStringClassifier() {
    JavaAnnotationProcessorDependency dependency = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .classifier("tests")
      .build();

    assertThat(dependency.classifier()).contains(new JavaDependencyClassifier("tests"));
  }

  @Test
  void shouldBuildWithExclusion() {
    JavaAnnotationProcessorDependency dependency = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .addExclusion(new GroupId("com.example"), new ArtifactId("excluded-lib"))
      .build();

    assertThat(dependency.exclusions()).containsExactly(DependencyId.of(new GroupId("com.example"), new ArtifactId("excluded-lib")));
  }

  @Test
  void shouldAddUnknownAnnotationProcessorDependency() {
    JavaBuildCommands commands = changes().build();

    assertThat(commands.get()).containsExactly(new AddJavaAnnotationProcessor(hibernateAnnotationProcessor()));
  }

  @Test
  void shouldAddUnknownAnnotationProcessorDependencyWithVersion() {
    JavaAnnotationProcessorDependency withVersion = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .versionSlug("spring-boot")
      .build();

    JavaBuildCommands commands = changes().dependency(withVersion).build();

    assertThat(commands.get()).containsExactly(new AddJavaAnnotationProcessor(withVersion), new SetVersion(springBootVersion()));
  }

  @Test
  void shouldNotUpdateExistingAnnotationProcessorDependency() {
    JavaAnnotationProcessorDependency existing = hibernateAnnotationProcessor();

    JavaBuildCommands commands = changes().projectDependencies(projectWithAnnotationProcessor(existing)).build();

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldUpdateAnnotationProcessorDependencyVersion() {
    JavaAnnotationProcessorDependency withVersion = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .versionSlug("spring-boot")
      .build();
    JavaDependencyVersion updatedVersion = new JavaDependencyVersion("spring-boot", "2.0.0");
    JavaDependenciesVersions currentVersions = new JavaDependenciesVersions(List.of(updatedVersion));

    JavaAnnotationProcessorDependency existingWithVersion = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .versionSlug("spring-boot")
      .build();
    JavaBuildCommands commands = changes()
      .dependency(withVersion)
      .currentVersions(currentVersions)
      .projectDependencies(projectWithAnnotationProcessor(existingWithVersion))
      .build();

    assertThat(commands.get()).containsExactly(new SetVersion(updatedVersion));
  }

  @Test
  void shouldKeepVersionFromProject() {
    JavaAnnotationProcessorDependency withoutVersion = hibernateAnnotationProcessor();

    JavaAnnotationProcessorDependency existingWithVersion = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .versionSlug("spring-boot")
      .build();
    JavaBuildCommands commands = changes()
      .dependency(withoutVersion)
      .projectDependencies(projectWithAnnotationProcessor(existingWithVersion))
      .build();

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldUpdateAnnotationProcessorWhenVersionChanges() {
    JavaAnnotationProcessorDependency withNewVersion = javaAnnotationProcessorDependency()
      .groupId("org.hibernate.orm")
      .artifactId("hibernate-processor")
      .versionSlug("spring-boot")
      .build();

    JavaAnnotationProcessorDependency existingWithoutVersion = hibernateAnnotationProcessor();
    JavaBuildCommands commands = changes()
      .dependency(withNewVersion)
      .projectDependencies(projectWithAnnotationProcessor(existingWithoutVersion))
      .build();

    assertThat(commands.get()).containsExactly(
      new RemoveJavaAnnotationProcessor(hibernateAnnotationProcessor().id()),
      new AddJavaAnnotationProcessor(withNewVersion),
      new SetVersion(springBootVersion())
    );
  }

  private ProjectJavaDependencies projectWithAnnotationProcessor(JavaAnnotationProcessorDependency dep) {
    return ProjectJavaDependencies.builder()
      .versions(new ProjectJavaDependenciesVersions(List.of(springBootVersion())))
      .dependenciesManagement(JavaDependencies.EMPTY)
      .dependencies(JavaDependencies.EMPTY)
      .annotationProcessingDependencies(new JavaAnnotationProcessorDependencies(List.of(dep)));
  }

  private static ChangesBuilder changes() {
    return new ChangesBuilder();
  }

  private static final class ChangesBuilder {

    private JavaAnnotationProcessorDependency dependency = hibernateAnnotationProcessor();
    private JavaDependenciesVersions currentVersions = currentJavaDependenciesVersion();
    private ProjectJavaDependencies projectDependencies = ProjectJavaDependencies.EMPTY;

    private ChangesBuilder dependency(JavaAnnotationProcessorDependency dependency) {
      this.dependency = dependency;

      return this;
    }

    private ChangesBuilder currentVersions(JavaDependenciesVersions currentVersions) {
      this.currentVersions = currentVersions;

      return this;
    }

    private ChangesBuilder projectDependencies(ProjectJavaDependencies projectDependencies) {
      this.projectDependencies = projectDependencies;

      return this;
    }

    private JavaBuildCommands build() {
      return dependency.changeCommands(currentVersions, projectDependencies);
    }
  }
}
