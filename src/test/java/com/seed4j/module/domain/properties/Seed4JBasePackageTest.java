package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JBasePackageTest {

  @Test
  void shouldGetDefaultPackageFromNullPackage() {
    assertThat(new Seed4JBasePackage(null).get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetDefaultPackageFromBlankPackage() {
    assertThat(new Seed4JBasePackage(" ").get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetPackage() {
    assertThat(new Seed4JBasePackage("com.company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldReplaceSlashesInPackage() {
    assertThat(new Seed4JBasePackage("com/company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldGetPackagePath() {
    assertThat(new Seed4JBasePackage("com.company.test.value").path()).isEqualTo("com/company/test/value");
  }
}
