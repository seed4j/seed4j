package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.VersionSlug;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaDependenciesVersionsTest {

  @Test
  void shouldNotGetUnknownDependency() {
    assertThatThrownBy(() -> currentJavaDependenciesVersion().get(new VersionSlug("unknown"))).isExactlyInstanceOf(
      UnknownJavaVersionSlugException.class
    );
  }
}
