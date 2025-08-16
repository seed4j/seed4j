package com.seed4j.shared.projectfolder.domain;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import java.io.File;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

@UnitTest
class FreeProjectFolderTest {

  private static final String ENDING_BY_UUID_REGEX = ".*\\w{8}(-\\w{4}){3}-\\w{12}$";

  private final FreeProjectFolder freeProjectFolder = new FreeProjectFolder();

  @Test
  void shouldBeValid() {
    assertThat(freeProjectFolder.isInvalid("/tmp/seed4j/project")).isFalse();
  }

  @Test
  void shouldGetValidPath() {
    assertThat(freeProjectFolder.generatePath()).startsWith(tmpDirWithSeparator()).matches(ENDING_BY_UUID_REGEX);
  }

  static String tmpDirWithSeparator() {
    String tmpDir = SystemUtils.JAVA_IO_TMPDIR;

    if (tmpDir.endsWith(File.separator)) {
      return tmpDir;
    }

    return tmpDir + File.separator;
  }
}
