package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.module.domain.git.GitRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ArrayUtils;

public final class GitTestUtil {

  private GitTestUtil() {}

  public static String getCommits(Path project) {
    Process process = execute(project, "log", "--pretty=format:%s");

    return readCommandResult(process);
  }

  public static String getCurrentBranch(Path project) {
    Process process = execute(project, "rev-parse", "--abbrev-ref", "HEAD");

    return readCommandResult(process);
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

  public static GitRepository gitRepository() {
    return new JGitGitRepository();
  }
}
