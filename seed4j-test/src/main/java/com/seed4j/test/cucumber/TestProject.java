package com.seed4j.test.cucumber;

import com.seed4j.TestFileUtils;
import org.jspecify.annotations.Nullable;

final class TestProject {

  private static @Nullable String folder;

  private TestProject() {}

  static void reset() {
    folder = null;
  }

  static String newFolder() {
    folder = TestFileUtils.tmpDirForTest();

    return folder;
  }

  static String folder() {
    if (folder == null) {
      throw new IllegalStateException("No module applied to a project yet");
    }

    return folder;
  }
}
