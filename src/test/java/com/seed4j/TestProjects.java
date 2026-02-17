package com.seed4j;

import org.jspecify.annotations.Nullable;

public final class TestProjects {

  private static @Nullable String lastProjectFolder;

  private TestProjects() {}

  public static String newTestFolder() {
    lastProjectFolder = TestFileUtils.tmpDirForTest();
    return lastProjectFolder;
  }

  public static String lastProjectFolder() {
    if (lastProjectFolder == null) {
      throw new IllegalStateException("No existing project folder for test");
    }
    return lastProjectFolder;
  }
}
