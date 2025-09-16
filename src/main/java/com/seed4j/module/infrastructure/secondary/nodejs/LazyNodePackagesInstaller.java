package com.seed4j.module.infrastructure.secondary.nodejs;

import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import com.seed4j.shared.npmdetector.infrastructure.secondary.NodePackageManagerInstallationReader;
import com.seed4j.shared.npmdetector.infrastructure.secondary.NodePackageManagerInstallationType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Launches install of Node.js packages with the specified {@link NodePackageManager} if its command is detected and if an existing lock file is present.
 */
@Service
@ExcludeFromGeneratedCodeCoverage(reason = "Hard to test, requires npm/pnpm installed")
class LazyNodePackagesInstaller implements NodeLazyPackagesInstaller {

  private static final Logger log = LoggerFactory.getLogger(LazyNodePackagesInstaller.class);
  private final NodePackageManagerInstallationReader nodePackageManagerInstallationReader = new NodePackageManagerInstallationReader();

  @Override
  public void runInstallIn(Seed4JProjectFolder folder, NodePackageManager nodePackageManager) {
    Assert.notNull("folder", folder);

    String packageLockFile = nodePackageManager.packageLockFile();
    if (!folder.fileExists(packageLockFile)) {
      log.info("No {} found, install of package with {} skipped", packageLockFile, nodePackageManager);
      return;
    }

    NodePackageManagerInstallationType nodePackageManagerInstallationType = nodePackageManagerInstallationReader.get(nodePackageManager);
    switch (nodePackageManagerInstallationType) {
      case UNIX -> execute(nodePackageManager, folder, nodePackageManager.command(), "install");
      case WINDOWS -> execute(nodePackageManager, folder, nodePackageManager.windowsCommand(), "install");
      case NONE -> log.info("No {} installed, can't install Node.js dependencies", nodePackageManager);
    }
  }

  private void execute(NodePackageManager nodePackageManager, Seed4JProjectFolder path, String... commands) {
    try {
      Process process = new ProcessBuilder(commands).directory(folderFile(path)).start();

      if (failedExecution(process)) {
        throw new NodePackagesInstallException(
          "Error during installation of Node.js dependencies with %s, process failed".formatted(nodePackageManager)
        );
      }

      traceProcess(String.join(" ", commands), process);
    } catch (IOException exception) {
      throw new NodePackagesInstallException(
        "Error during installation of Node.js dependencies with %s: %s".formatted(nodePackageManager, exception.getMessage()),
        exception
      );
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();

      throw new NodePackagesInstallException(
        "Error during installation of Node.js dependencies with %s: %s".formatted(nodePackageManager, exception.getMessage()),
        exception
      );
    }
  }

  private File folderFile(Seed4JProjectFolder path) {
    return new File(path.get());
  }

  private boolean failedExecution(Process process) throws InterruptedException {
    return !process.waitFor(1, TimeUnit.MINUTES);
  }

  private void traceProcess(String command, Process process) throws IOException {
    if (log.isTraceEnabled()) {
      log.trace("{}: {}", command, read(process.getInputStream()));
    }

    String errors = read(process.getErrorStream());

    if (StringUtils.isNotBlank(errors)) {
      log.error("Error during {}: {}", command, errors);
    }
  }

  private String read(InputStream stream) throws IOException {
    return new String(stream.readAllBytes(), StandardCharsets.UTF_8).intern();
  }
}
