package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterBasePackageTest {

  @Test
  void shouldGetDefaultPackageFromNullPackage() {
    assertThat(new JHipsterBasePackage(null).get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetDefaultPackageFromBlankPackage() {
    assertThat(new JHipsterBasePackage(" ").get()).isEqualTo("com.mycompany.myapp");
  }

  @Test
  void shouldGetPackage() {
    assertThat(new JHipsterBasePackage("com.company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldReplaceSlashesInPackage() {
    assertThat(new JHipsterBasePackage("com/company").get()).isEqualTo("com.company");
  }

  @Test
  void shouldGetPackagePath() {
    assertThat(new JHipsterBasePackage("com.company.test.value").path()).isEqualTo("com/company/test/value");
  }
}
