package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.secondary;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.Npm;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmRepository;
import tech.jhipster.lite.generator.tools.domain.Project;

@Repository
public class NpmLocalRepository implements NpmRepository {

  private final Logger log = LoggerFactory.getLogger(NpmLocalRepository.class);

  private final Executor taskExecutor;

  @Value("${application.cmd.timeout:120}")
  private Integer timeout;

  public NpmLocalRepository(Executor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void npmInstall(Project project) {
    try {
      this.runProcess(project, Npm.getExecutableCommand(), "install");
    } catch (IOException e) {
      throw new GeneratorException("Error when running \"npm install\"", e);
    }
  }

  @Override
  public void npmPrettierFormat(Project project) {
    try {
      this.runProcess(project, Npm.getExecutableCommand(), "run", "prettier:format");
    } catch (IOException e) {
      throw new GeneratorException("Error when running \"npm run prettier:format\"");
    }
  }

  private void runProcess(Project project, String... command) throws IOException {
    File workingDir = new File(project.getFolder());
    String line;
    ProcessBuilder processBuilder = new ProcessBuilder()
      .directory(workingDir)
      .command(command)
      .redirectError(ProcessBuilder.Redirect.DISCARD);
    Process process = processBuilder.start();
    taskExecutor.execute(() -> wait(process));

    try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      while ((line = input.readLine()) != null) {
        log.info(line);
      }
    }
  }

  public void wait(Process process) {
    try {
      process.waitFor(timeout, TimeUnit.SECONDS);
      process.destroyForcibly();
    } catch (InterruptedException e) {
      log.error("Unable to execute process successfully.", e);
      Thread.currentThread().interrupt();
    }
  }
}
