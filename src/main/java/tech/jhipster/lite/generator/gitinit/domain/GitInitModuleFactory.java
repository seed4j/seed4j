package tech.jhipster.lite.generator.gitinit.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.io.IOException;
import java.nio.file.Files;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GitInitModuleFactory {

  private final GitRepository git;

  public GitInitModuleFactory(GitRepository git) {
    Assert.notNull("git", git);

    this.git = git;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .preActions()
        .add(() -> createProjectDirectory(properties))
        .and()
      .postActions()
        .add(() -> git.init(properties.projectFolder()))
        .and()
      .build();
    //@formatter:on
  }

  @Generated(reason = "Hard to test implementation detail error management")
  private void createProjectDirectory(JHipsterModuleProperties properties) {
    try {
      Files.createDirectories(properties.projectFolder().filePath(""));
    } catch (IOException e) {
      throw new GeneratorException("Can't create project folder: " + e.getMessage(), e);
    }
  }
}
