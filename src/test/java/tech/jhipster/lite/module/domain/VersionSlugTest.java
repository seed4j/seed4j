package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;

@UnitTest
class VersionSlugTest {

  @Test
  void shouldGetVariablesCleanedSlug() {
    assertThat(new VersionSlug("${with-suffix.version}").propertyName()).isEqualTo("with-suffix.version");
  }

  @Test
  void shouldNotCleanWithOnlyVariableStart() {
    assertThat(new VersionSlug("${with-suffix.version").propertyName()).isEqualTo("${with-suffix.version");
  }

  @Test
  void shouldNotCleanWithOnlyVariableEnd() {
    assertThat(new VersionSlug("with-suffix}").propertyName()).isEqualTo("with-suffix}.version");
  }

  @Test
  void shouldGetPropertyWithOneSuffixForSlugWithSuffix() {
    assertThat(new VersionSlug("with-suffix.version").propertyName()).isEqualTo("with-suffix.version");
  }

  @Test
  void shouldGetPropertyWithSuffixForSlugWithoutSuffix() {
    assertThat(new VersionSlug("no-suffix").propertyName()).isEqualTo("no-suffix.version");
  }

  @Test
  void shouldGetEmptySlugFromNullSlug() {
    assertThat(VersionSlug.of(null)).isEmpty();
  }

  @Test
  void shouldGetEmptySlugFromBlankSlug() {
    assertThat(VersionSlug.of(" ")).isEmpty();
  }

  @Test
  void shouldGetSlugFromActualSlug() {
    assertThat(VersionSlug.of("slug")).contains(new VersionSlug("slug"));
  }
}
