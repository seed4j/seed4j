package com.seed4j.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class MissingJavaBuildConfigurationExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    MissingJavaBuildConfigurationException exception = new MissingJavaBuildConfigurationException(new Seed4JProjectFolder("folder"));

    assertThat(exception.getMessage()).isEqualTo("Can't find any java build tool configuration in folder");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(JavaDependencyErrorKey.MISSING_BUILD_CONFIGURATION);
    assertThat(exception.parameters()).containsOnly(entry("folder", "folder"));
  }
}
