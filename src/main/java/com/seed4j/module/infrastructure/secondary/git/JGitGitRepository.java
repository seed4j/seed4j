package com.seed4j.module.infrastructure.secondary.git;

import com.seed4j.module.domain.git.GitCommitMessage;
import com.seed4j.module.domain.git.GitRepository;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

    try (Git gitFolder = Git.open(gitRepository)) {
      gitFolder.add().setUpdate(true).addFilepattern(".").call(); // stage modified and deleted
      gitFolder.add().addFilepattern(".").call(); // stage modified and new

      gitFolder.commit().setSign(false).setMessage(message.get()).call();
    } catch (IOException | GitAPIException | JGitInternalException e) {
      throw new GitCommitException("Can't commit :" + e.getMessage(), e);
    }
  }

  private File folderFile(JHipsterProjectFolder folder) {
    return Path.of(folder.get()).toFile();
  }
}
