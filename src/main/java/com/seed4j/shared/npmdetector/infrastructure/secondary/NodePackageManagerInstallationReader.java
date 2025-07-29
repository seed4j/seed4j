package com.seed4j.shared.npmdetector.infrastructure.secondary;

import com.seed4j.module.domain.nodejs.NodePackageManager;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@ExcludeFromGeneratedCodeCoverage(reason = "Cases can only be tested by using different computers")
public class NodePackageManagerInstallationReader {

  private static final Logger log = LoggerFactory.getLogger(NodePackageManagerInstallationReader.class);

  public NodePackageManagerInstallationType get(NodePackageManager nodePackageManager) {
    if (has(nodePackageManager.command(), "--version")) {
      return NodePackageManagerInstallationType.UNIX;
    }

    if (has(nodePackageManager.windowsCommand(), "--version")) {
      return NodePackageManagerInstallationType.WINDOWS;
    }

    return NodePackageManagerInstallationType.NONE;
  }

  private static boolean has(String... commands) {
    try {
      Process process = new ProcessBuilder(commands).start();

      if (process.waitFor(5, TimeUnit.SECONDS)) {
        return true;
      }
    } catch (IOException e) {
      log.debug("Error executing commands: {}", e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      log.debug("Error executing commands: {}", e.getMessage(), e);
    }

    return false;
  }
}
