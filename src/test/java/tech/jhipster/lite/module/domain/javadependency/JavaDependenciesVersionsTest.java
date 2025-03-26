package tech.jhipster.lite.module.domain.javadependency;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;

@UnitTest
class JavaDependenciesVersionsTest {

  @Test
  void shouldNotGetUnknownDependency() {
    assertThatThrownBy(() -> currentJavaDependenciesVersion().get(new VersionSlug("unknown"))).isExactlyInstanceOf(
      UnknownJavaVersionSlugException.class
    );
  }
}
