package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.common.domain.Generated;

@Service
@Generated(reason = "Cases can only be tested by using different computers")
class DslNpmInstallationReader {

  private static final Logger log = LoggerFactory.getLogger(DslNpmInstallationReader.class);

  public NpmInstallationType get() {
    if (has("npm", "--version")) {
      return NpmInstallationType.UNIX;
    }

    if (has("npm.cmd", "--version")) {
      return NpmInstallationType.WINDOWS;
    }

    return NpmInstallationType.NONE;
  }

  private static boolean has(String... commands) {
    try {
      Process process = new ProcessBuilder(commands).redirectOutput(new File("/tmp/output")).start();

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
