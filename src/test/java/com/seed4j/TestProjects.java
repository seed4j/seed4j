package com.seed4j;

import org.jspecify.annotations.Nullable;

public final class TestProjects {

  private static @Nullable String lastProjectFolder;

  private TestProjects() {}

  public static String newTestFolder() {
    lastProjectFolder = TestFileUtils.tmpDirForTest();
    return lastProjectFolder;
  }

  public static @Nullable String lastProjectFolder() {
    return lastProjectFolder;
  }
}
