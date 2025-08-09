package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedBasePackageTest {

  @Test
  void shouldGetDefaultPackageFromNullPackage() {
    assertThat(new SeedBasePackage(null).get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetDefaultPackageFromBlankPackage() {
    assertThat(new SeedBasePackage(" ").get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetPackage() {
    assertThat(new SeedBasePackage("com.company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldReplaceSlashesInPackage() {
    assertThat(new SeedBasePackage("com/company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldGetPackagePath() {
    assertThat(new SeedBasePackage("com.company.test.value").path()).isEqualTo("com/company/test/value");
  }
}
