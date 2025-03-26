package tech.jhipster.lite.module.domain.javadependency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownJavaVersionSlugExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownJavaVersionSlugException exception = new UnknownJavaVersionSlugException(new VersionSlug("version-slug"));

    assertThat(exception.getMessage()).isEqualTo(
      "Can't find property version-slug.version, forgot to add it in \"src/main/resources/generator/dependencies/pom.xml\"?"
    );
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.key()).isEqualTo(JavaDependencyErrorKey.UNKNOWN_VERSION);
    assertThat(exception.parameters()).containsOnly(entry("versionSlug", "version-slug.version"));
  }
}
