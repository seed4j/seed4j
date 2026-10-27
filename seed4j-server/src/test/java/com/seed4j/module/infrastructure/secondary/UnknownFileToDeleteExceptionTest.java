package com.seed4j.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class UnknownFileToDeleteExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    UnknownFileToDeleteException exception = new UnknownFileToDeleteException(new Seed4JProjectFilePath("path"));

    assertThat(exception.getMessage()).isEqualTo("File to delete path, can't be found");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(ModuleSecondaryErrorKey.UNKNOWN_FILE_TO_DELETE);
    assertThat(exception.parameters()).containsOnly(entry("file", "path"));
  }
}
