package tech.jhipster.lite.module.infrastructure.secondary.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.git.GitCommitMessage;
import tech.jhipster.lite.module.domain.git.GitRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Repository
class JGitGitRepository implements GitRepository {

  private static final Logger log = LoggerFactory.getLogger(JGitGitRepository.class);

  @Override
  public void init(JHipsterProjectFolder folder) {
    Assert.notNull("folder", folder);

    if (isGit(folder)) {
      log.trace("Folder {} is already a git project, not running init", folder.get());

      return;
    }

    try {
      Git.init().setInitialBranch("main").setDirectory(folderFile(folder)).call();
    } catch (IllegalStateException | GitAPIException | JGitInternalException e) {
      throw new GitInitException("Error during git init: " + e.getMessage(), e);
    }
  }

  private boolean isGit(JHipsterProjectFolder folder) {
    return findGitDirectory(folder).isPresent();
  }

  private Optional<File> findGitDirectory(JHipsterProjectFolder folder) {
    FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
    return Optional.ofNullable(repositoryBuilder.findGitDir(folderFile(folder)).getGitDir());
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "IOException are hard to test")
  public void commitAll(JHipsterProjectFolder folder, GitCommitMessage message) {
    Assert.notNull("folder", folder);
    Assert.notNull("message", message);

    File gitRepository = findGitDirectory(folder).orElseThrow(() -> new GitCommitException("Can't commit in non existing git repository"));

    doNpmInstall(folder);

    try (Git gitFolder = Git.open(gitRepository)) {
      gitFolder.add().setUpdate(true).addFilepattern(".").call(); // stage modified and deleted
      gitFolder.add().addFilepattern(".").call(); // stage modified and new

      gitFolder.commit().setSign(false).setMessage(message.get()).call();
    } catch (IOException | GitAPIException | JGitInternalException e) {
      throw new GitCommitException("Can't commit :" + e.getMessage(), e);
    }
  }

  private static void doNpmInstall(JHipsterProjectFolder folder) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command("npm", "install");
    processBuilder.directory(new File(folder.get()));

    try {
      Process process = processBuilder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line = null;
      while ((line = reader.readLine()) != null) {
        log.trace(line);
      }

      process.waitFor();
      log.trace("npm install finished");
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Interrupted: npm install failed", e);
    }
  }

  private File folderFile(JHipsterProjectFolder folder) {
    return new File(folder.get());
  }
}
