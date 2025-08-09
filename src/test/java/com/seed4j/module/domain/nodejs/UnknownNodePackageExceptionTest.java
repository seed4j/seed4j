package com.seed4j.module.domain.nodejs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class UnknownNodePackageExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownNodePackageException exception = new UnknownNodePackageException(
      new NodePackageName("package-name"),
      SeedNodePackagesVersionSource.ANGULAR.build()
    );

    assertThat(exception.getMessage()).isEqualTo("Can't find package-name version in angular package.json, forgot to add it?");
    assertThat(exception.key()).isEqualTo(NodeErrorKey.UNKNOWN_PACKAGE);
    assertThat(exception.status()).isEqualTo(ErrorStatus.INTERNAL_SERVER_ERROR);
    assertThat(exception.parameters()).containsOnly(entry("packageName", "package-name"), entry("packageSource", "angular"));
  }
}
