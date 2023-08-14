package tech.jhipster.lite.module.domain.npm;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownNpmPackageExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownNpmPackageException exception = new UnknownNpmPackageException(new NpmPackageName("package-name"), NpmVersionSource.ANGULAR);

    assertThat(exception.getMessage()).isEqualTo("Can't find package-name version in ANGULAR package.json, forgot to add it?");
    assertThat(exception.key()).isEqualTo(NpmErrorKey.UNKNOWN_PACKAGE);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.parameters()).containsOnly(entry("packageName", "package-name"), entry("packageSource", "ANGULAR"));
  }
}
