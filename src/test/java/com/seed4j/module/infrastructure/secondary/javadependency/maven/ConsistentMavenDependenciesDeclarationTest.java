package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.VersionSlug;
import io.fabric8.maven.Maven;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class ConsistentMavenDependenciesDeclarationTest {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";

  public static Stream<Arguments> versions() {
    Model mavenModel = readMavenModel();
    return mavenModel.getProperties().keySet().stream().map(Object::toString).map(VersionSlug::new).map(Arguments::of);
  }

  @MethodSource("versions")
  @ParameterizedTest
  void allDeclaredVersionsShouldBeAssociatedWithADependency(VersionSlug versionSlug) {
    Model mavenModel = readMavenModel();

    assertThat(mavenModel.getDependencyManagement().getDependencies()).anyMatch(dependency ->
      dependency.getVersion().equals(versionSlug.mavenVariable())
    );
  }

  public static Stream<Arguments> dependencies() {
    Model mavenModel = readMavenModel();
    return mavenModel.getDependencyManagement().getDependencies().stream().map(Arguments::of);
  }

  @MethodSource("dependencies")
  @ParameterizedTest
  void allDeclaredDependenciesShouldUseAVersion(Dependency dependency) {
    assertThat(dependency.getVersion()).isNotBlank();
  }

  private static Model readMavenModel() {
    var reader = new InputStreamReader(
      Objects.requireNonNull(ConsistentMavenDependenciesDeclarationTest.class.getResourceAsStream(CURRENT_VERSIONS_FILE)),
      UTF_8
    );
    return Maven.readModel(reader);
  }
}
