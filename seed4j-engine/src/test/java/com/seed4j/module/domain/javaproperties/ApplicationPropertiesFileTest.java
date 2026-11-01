package com.seed4j.module.domain.javaproperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.MissingMandatoryValueException;
import com.seed4j.shared.error.domain.NullElementInCollectionException;
import org.junit.jupiter.api.Test;

@UnitTest
class ApplicationPropertiesFileTest {

  private static final ApplicationPropertiesFile APPLICATION = ApplicationPropertiesFile.named("application").in(
    "src/main/resources/config/",
    "src/main/resources/"
  );

  @Test
  void shouldNotBuildWithoutName() {
    assertThatThrownBy(() -> ApplicationPropertiesFile.named(" ")).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldNotBuildWithBlankFolder() {
    assertThatThrownBy(() -> ApplicationPropertiesFile.named("application").in("src/main/resources/", " ")).isExactlyInstanceOf(
      MissingMandatoryValueException.class
    );
  }

  @Test
  @SuppressWarnings("NullAway")
  void shouldNotBuildWithNullFolder() {
    assertThatThrownBy(() -> ApplicationPropertiesFile.named("application").in("src/main/resources/", (String) null))
      .isExactlyInstanceOf(NullElementInCollectionException.class)
      .hasMessageContaining("folders");
  }

  @Test
  void shouldNotAddBlankProfile() {
    assertThatThrownBy(() -> APPLICATION.profile(" ")).isExactlyInstanceOf(MissingMandatoryValueException.class);
  }

  @Test
  void shouldKeepFoldersInDeclarationOrder() {
    assertThat(APPLICATION.folders()).containsExactly("src/main/resources/config/", "src/main/resources/");
  }

  @Test
  void shouldUseNameAsFilenameWithoutProfile() {
    assertThat(APPLICATION.filename()).isEqualTo("application");
    assertThat(APPLICATION.profile()).isEmpty();
  }

  @Test
  void shouldAppendProfileToFilename() {
    ApplicationPropertiesFile local = APPLICATION.profile("local");

    assertThat(local.filename()).isEqualTo("application-local");
    assertThat(local.profile()).contains("local");
    assertThat(local.folders()).isEqualTo(APPLICATION.folders());
  }

  @Test
  void shouldNotChangeOriginalFileWhenAddingProfile() {
    APPLICATION.profile("local");

    assertThat(APPLICATION.filename()).isEqualTo("application");
  }

  @Test
  void shouldBeEqualToFileWithSameNameProfileAndFolders() {
    ApplicationPropertiesFile file = ApplicationPropertiesFile.named("application")
      .in("src/main/resources/config/", "src/main/resources/")
      .profile("local");

    assertThat(file).isEqualTo(APPLICATION.profile("local")).hasSameHashCodeAs(APPLICATION.profile("local"));
  }

  @Test
  void shouldNotBeEqualToFileWithOtherName() {
    assertThat(APPLICATION).isNotEqualTo(
      ApplicationPropertiesFile.named("bootstrap").in("src/main/resources/config/", "src/main/resources/")
    );
  }

  @Test
  void shouldNotBeEqualToFileWithOtherProfile() {
    assertThat(APPLICATION.profile("local")).isNotEqualTo(APPLICATION.profile("test")).isNotEqualTo(APPLICATION);
  }

  @Test
  void shouldNotBeEqualToFileWithOtherFolders() {
    assertThat(APPLICATION).isNotEqualTo(ApplicationPropertiesFile.named("application").in("src/main/resources/"));
  }

  @Test
  @SuppressWarnings("EqualsWithItself")
  void shouldBeEqualToItself() {
    assertThat(APPLICATION.equals(APPLICATION)).isTrue();
  }

  @Test
  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  void shouldNotBeEqualToOtherType() {
    assertThat(APPLICATION.equals("application")).isFalse();
  }

  @Test
  void shouldShowFoldersAndFilename() {
    assertThat(APPLICATION.profile("local")).hasToString("src/main/resources/config/, src/main/resources/ -> application-local");
  }
}
