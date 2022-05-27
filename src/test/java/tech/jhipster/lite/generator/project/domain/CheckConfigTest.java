package tech.jhipster.lite.generator.project.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.UnauthorizedValueException;

@UnitTest
class CheckConfigTest {

  @Test
  void shouldValidBaseName() {
    assertThatCode(() -> CheckConfig.validBaseName("jhipster")).doesNotThrowAnyException();
    assertThatCode(() -> CheckConfig.validBaseName("JHipster")).doesNotThrowAnyException();
    assertThatCode(() -> CheckConfig.validBaseName("JHipster1664")).doesNotThrowAnyException();
    assertThatCode(() -> CheckConfig.validBaseName("1664")).doesNotThrowAnyException();
  }

  @Test
  void shouldNotValidBaseNameWithSpace() {
    assertThatThrownBy(() -> CheckConfig.validBaseName("jhipster app")).isExactlyInstanceOf(UnauthorizedValueException.class);
  }

  @Test
  void shouldNotValidBaseNameWithUnderscore() {
    assertThatThrownBy(() -> CheckConfig.validBaseName("jhipster_app")).isExactlyInstanceOf(UnauthorizedValueException.class);
  }

  @Test
  void shouldNotValidBaseNameWithSpecialCharacter() {
    assertThatThrownBy(() -> CheckConfig.validBaseName("jhipster$")).isExactlyInstanceOf(UnauthorizedValueException.class);
  }

  @Test
  void shouldNotValidBaseNameWithNull() {
    assertThatThrownBy(() -> CheckConfig.validBaseName(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("baseName");
  }

  @Test
  void shouldNotValidBaseNameWithBlank() {
    assertThatThrownBy(() -> CheckConfig.validBaseName(" "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("baseName");
  }

  @Test
  void shouldValidPackageName() {
    assertThatCode(() -> CheckConfig.validPackageName("tech.jhipster.lite")).doesNotThrowAnyException();
  }

  @Test
  void shouldNotValidPackageNameWithSpace() {
    assertThatThrownBy(() -> CheckConfig.validPackageName("tech jhipster lite")).isExactlyInstanceOf(UnauthorizedValueException.class);
  }

  @Test
  void shouldNotValidPackageNameWithDotAtBeginning() {
    assertThatThrownBy(() -> CheckConfig.validPackageName(".tech.jhipster.lite")).isExactlyInstanceOf(UnauthorizedValueException.class);
  }

  @Test
  void shouldNotValidPackageNameWithNull() {
    assertThatThrownBy(() -> CheckConfig.validPackageName(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("packageName");
  }

  @Test
  void shouldNotValidPackageNameWithBlank() {
    assertThatThrownBy(() -> CheckConfig.validPackageName(" "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("packageName");
  }
}
