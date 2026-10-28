package com.seed4j.module.infrastructure.secondary.git;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ArrayUtils;

public final class GitTestUtil {

  private GitTestUtil() {}

  public static List<String> getCommits(Path project) {
    Process process = execute(project, "log", "--pretty=format:%s");

    return readCommandResultLines(process);
  }

  public static List<String> getTrackedFiles(Path project, String folder) {
    Process process = execute(project, "ls-files", "--", folder);

    return readCommandResultLines(process);
  }

  public static List<String> getUncommittedChanges(Path project, String folder) {
    Process process = execute(project, "status", "--porcelain", "--untracked-files=all", "--", folder);

    return readCommandResultLines(process);
  }

  public static String getCurrentBranch(Path project) {
    Process process = execute(project, "rev-parse", "--abbrev-ref", "HEAD");

    return readCommandResult(process);
  }

  private static List<String> readCommandResultLines(Process process) {
    return readCommandResult(process)
      .lines()
      .filter(line -> !line.isBlank())
      .toList();
  }

  private static String readCommandResult(Process process) throws AssertionError {
    try {
      return new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).intern();
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }

  public static Process execute(Path project, String... commands) {
    try {
      Process process = new ProcessBuilder(ArrayUtils.addAll(new String[] { "git" }, commands)).directory(project.toFile()).start();

      if (!process.waitFor(5, TimeUnit.SECONDS)) {
        throw new AssertionError("Can't execute command");
      }

      return process;
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new AssertionError(e.getMessage(), e);
    }
  }
}
