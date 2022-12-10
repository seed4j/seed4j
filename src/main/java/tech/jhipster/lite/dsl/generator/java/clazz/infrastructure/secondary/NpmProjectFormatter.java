package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

@Generated(reason = "Error cases are hard to test and not worth the efforts")
record NpmProjectFormatter(String command) implements DslProjectFormatter {
  private static final Logger log = LoggerFactory.getLogger(NpmProjectFormatter.class);

  public NpmProjectFormatter {
    Assert.notBlank("command", command);
  }

  @Override
  public void format(ProjectPath path) {
    execute(path, command, "i");
    execute(path, command, "run", "prettier:format");
  }

  private void execute(ProjectPath path, String... commands) {
    try {
      Process process = new ProcessBuilder(commands).directory(folderFile(path)).start();

      if (failedExecution(process)) {
        throw new ProjectFormattingException("Error during formatting, process failed");
      }

      traceProcess(Stream.of(commands).collect(Collectors.joining(" ")), process);
    } catch (IOException e) {
      throw new ProjectFormattingException("Error during formatting: " + e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new ProjectFormattingException("Error during formatting: " + e.getMessage(), e);
    }
  }

  private File folderFile(ProjectPath path) {
    return new File(path.get());
  }

  private boolean failedExecution(Process process) throws InterruptedException {
    return !process.waitFor(1, TimeUnit.MINUTES);
  }

  private void traceProcess(String command, Process process) throws IOException {
    if (log.isTraceEnabled()) {
      log.trace("{}: {}", command, read(process.getInputStream()));
    }

    if (log.isErrorEnabled()) {
      String errors = read(process.getErrorStream());

      if (StringUtils.isNotBlank(errors)) {
        log.error("Error during {}: {}", command, errors);
      }
    }
  }

  private String read(InputStream stream) throws IOException {
    return new String(stream.readAllBytes(), StandardCharsets.UTF_8).intern();
  }
}
