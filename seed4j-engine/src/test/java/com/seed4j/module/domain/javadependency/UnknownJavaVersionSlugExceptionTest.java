package com.seed4j.module.domain.javadependency;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

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
