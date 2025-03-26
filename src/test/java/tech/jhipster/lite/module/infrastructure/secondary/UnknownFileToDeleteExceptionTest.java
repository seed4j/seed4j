package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.shared.error.domain.ErrorStatus;

@UnitTest
class UnknownFileToDeleteExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownFileToDeleteException exception = new UnknownFileToDeleteException(new JHipsterProjectFilePath("path"));

    assertThat(exception.getMessage()).isEqualTo("File to delete path, can't be found");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ModuleSecondaryErrorKey.UNKNOWN_FILE_TO_DELETE);
    assertThat(exception.parameters()).containsOnly(entry("file", "path"));
  }
}
