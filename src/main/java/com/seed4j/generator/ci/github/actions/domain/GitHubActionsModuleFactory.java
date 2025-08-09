package com.seed4j.generator.ci.github.actions.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public class GitHubActionsModuleFactory {

  private static final SeedSource SOURCE = from("ci/github/actions/.github");

  private final NodeVersions nodeVersions;

  public GitHubActionsModuleFactory(NodeVersions nodeVersions) {
    this.nodeVersions = nodeVersions;
  }

  public SeedModule buildGitHubActionsMavenModule(SeedModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.MAVEN);
  }

  public SeedModule buildGitHubActionsGradleModule(SeedModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.GRADLE);
  }

  private SeedModule buildGitHubActionsModule(SeedModuleProperties properties, JavaBuildTool javaBuildTool) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("nodeVersion", nodeVersions.nodeVersion().get())
        .put(javaBuildTool.name().toLowerCase(Locale.ROOT), true)
        .and()
      .files()
        .add(SOURCE.template("workflows/github-actions.yml"), to(".github/workflows/github-actions.yml"))
        .and()
      .build();
    // @formatter:on
  }
}
