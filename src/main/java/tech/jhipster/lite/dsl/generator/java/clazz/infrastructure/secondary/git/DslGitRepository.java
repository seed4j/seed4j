package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.dsl.common.domain.git.GitCommitMessage;
import tech.jhipster.lite.dsl.common.domain.git.GitRepository;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

@Repository
class DslGitRepository implements GitRepository {

  private static final Logger log = LoggerFactory.getLogger(DslGitRepository.class);

  @Override
  public void init(ProjectPath folder) {
    Assert.notNull("folder", folder);

    if (isGit(folder)) {
      log.debug("Folder {} is already a git project, not running init", folder.get());

      return;
    }

    try {
      Git.init().setInitialBranch("main").setDirectory(folderFile(folder)).call();
    } catch (IllegalStateException | GitAPIException | JGitInternalException e) {
      throw new GitInitException("Error during git init: " + e.getMessage(), e);
    }
  }

  private boolean isGit(ProjectPath folder) {
    return Files.exists(Paths.get(folder.get()).resolve(".git"));
  }

  @Override
  public void commitAll(ProjectPath folder, GitCommitMessage message) {
    Assert.notNull("folder", folder);
    Assert.notNull("message", message);

    File folderFile = folderFile(folder);

    try (Git gitFolder = Git.open(folderFile)) {
      gitFolder.add().addFilepattern(".").call();

      gitFolder.commit().setSign(false).setMessage(message.get()).call();
    } catch (IOException | GitAPIException | JGitInternalException e) {
      throw new GitCommitException("Can't commit :" + e.getMessage(), e);
    }
  }

  private File folderFile(ProjectPath folder) {
    return new File(folder.get());
  }
}
