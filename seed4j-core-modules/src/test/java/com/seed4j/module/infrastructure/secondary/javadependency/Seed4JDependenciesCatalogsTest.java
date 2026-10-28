package com.seed4j.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.infrastructure.secondary.FileSystemProjectFiles;
import com.seed4j.module.infrastructure.secondary.javadependency.gradle.VersionsCatalog;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JDependenciesCatalogsTest {

  @Test
  void shouldNotDeclareGradleCatalogVersionsAlsoDeclaredInMavenCatalog() {
    JavaDependenciesVersions mavenVersions = new Seed4JMavenDependenciesReader(new FileSystemProjectFiles()).get();
    VersionsCatalog gradleCatalog = new VersionsCatalog(Path.of("src/main/resources/generator/dependencies/gradle/libs.versions.toml"));

    assertThat(gradleCatalog.retrieveVersions())
      .map(JavaDependencyVersion::slug)
      .filteredOn(slug -> catchThrowable(() -> mavenVersions.get(slug)) == null)
      .as("Versions declared in both Maven and Gradle catalogs: one silently overrides the other for every generated project")
      .isEmpty();
  }
}
