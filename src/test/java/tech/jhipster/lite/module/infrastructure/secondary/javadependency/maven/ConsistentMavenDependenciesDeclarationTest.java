package tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven;

import static org.assertj.core.api.Assertions.assertThat;

import io.fabric8.maven.Maven;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;

@UnitTest
class ConsistentMavenDependenciesDeclarationTest {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";

  public static Stream<VersionSlug> versions() {
    Model mavenModel = readMavenModel();
    return mavenModel
      .getProperties()
      .keySet()
      .stream()
      .map(Object::toString)
      .filter(name -> !"maven.version".equals(name))
      .map(VersionSlug::new);
  }

  @MethodSource("versions")
  @ParameterizedTest
  void allDeclaredVersionsShouldBeAssociatedWithADependency(VersionSlug versionSlug) {
    Model mavenModel = readMavenModel();

    assertThat(mavenModel.getDependencyManagement().getDependencies()).anyMatch(
      dependency -> dependency.getVersion().equals(versionSlug.mavenVariable())
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
      Objects.requireNonNull(ConsistentMavenDependenciesDeclarationTest.class.getResourceAsStream(CURRENT_VERSIONS_FILE))
    );
    return Maven.readModel(reader);
  }
}
