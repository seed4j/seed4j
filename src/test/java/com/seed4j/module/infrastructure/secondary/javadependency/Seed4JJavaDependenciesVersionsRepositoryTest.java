package com.seed4j.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JJavaDependenciesVersionsRepositoryTest {

  @Test
  void shouldGetVersionsFromReaders() {
    JavaDependenciesReader customVersions = () -> versions(version("json-web-token", "1.2.3"));
    JavaDependenciesReader defaultVersions = () -> versions(version("json-web-token", "1.1.3"), version("spring", "2.1.2"));

    Seed4JJavaDependenciesVersionsRepository repository = new Seed4JJavaDependenciesVersionsRepository(
      List.of(customVersions, defaultVersions)
    );

    assertThat(repository.get())
      .usingRecursiveComparison()
      .isEqualTo(versions(version("json-web-token", "1.2.3"), version("spring", "2.1.2")));
  }

  private static JavaDependenciesVersions versions(JavaDependencyVersion... versions) {
    return new JavaDependenciesVersions(List.of(versions));
  }

  private static JavaDependencyVersion version(String slug, String version) {
    return new JavaDependencyVersion(slug, version);
  }
}
