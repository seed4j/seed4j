package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static tech.jhipster.lite.common.domain.FileUtils.exists;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.REMOTE_URL;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Cloud;
import tech.jhipster.lite.generator.project.domain.Project;

@Repository
public class CloudRepository implements Cloud {

  private final Logger log = LoggerFactory.getLogger(CloudRepository.class);

  @Override
  public void clone(Project project) {
    Assert.notNull(REMOTE_URL, project.getRemoteUrl());
    String githubToken = "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"; //GET IT FROM DB
    String remoteUrl = project.getRemoteUrl().orElseThrow();
    String path = project.getFolder();
    if (!exists(path)) {
      try {
        FileUtils.createFolder(path);
      } catch (IOException e) {
        throw new GeneratorException("Impossible to clone project locally", e);
      }
      File localPath = new File(path);

      CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(githubToken, "");

      try {
        Git git = Git.cloneRepository().setURI(remoteUrl).setDirectory(localPath).setCredentialsProvider(credentialsProvider).call();
        git
          .checkout()
          .setCreateBranch(true)
          .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
          .setName("Jhipster-bot")
          .call();
      } catch (GitAPIException e) {
        throw new GeneratorException("Impossible to clone project locally", e);
      }
    }
    log.info("Cloned repository: " + "https://github.com/jhipster/jhipster-lite");
  }
}
