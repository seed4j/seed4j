package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

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
