package com.seed4j.generator.ci.github.actions.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public class GitHubActionsModuleFactory {

  private static final Seed4JSource SOURCE = from("ci/github/actions/.github");

  private final NodeVersions nodeVersions;

  public GitHubActionsModuleFactory(NodeVersions nodeVersions) {
    this.nodeVersions = nodeVersions;
  }

  public Seed4JModule buildGitHubActionsMavenModule(Seed4JModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.MAVEN);
  }

  public Seed4JModule buildGitHubActionsGradleModule(Seed4JModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.GRADLE);
  }

  private Seed4JModule buildGitHubActionsModule(Seed4JModuleProperties properties, JavaBuildTool javaBuildTool) {
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
