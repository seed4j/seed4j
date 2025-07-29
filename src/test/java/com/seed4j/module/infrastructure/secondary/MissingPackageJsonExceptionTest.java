package com.seed4j.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class MissingPackageJsonExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    MissingPackageJsonException exception = new MissingPackageJsonException(new JHipsterProjectFolder("folder"));

    assertThat(exception.getMessage()).isEqualTo("package.json is missing in folder, can't apply module");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ModuleSecondaryErrorKey.MISSING_PACKAGE_JSON);
    assertThat(exception.parameters()).containsOnly(entry("folder", "folder"));
  }
}
