package com.seed4j.generator.ci.github.actions.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

public class GitHubActionsModuleFactory {

  private static final JHipsterSource SOURCE = from("ci/github/actions/.github");

  private final NodeVersions nodeVersions;

  public GitHubActionsModuleFactory(NodeVersions nodeVersions) {
    this.nodeVersions = nodeVersions;
  }

  public JHipsterModule buildGitHubActionsMavenModule(JHipsterModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.MAVEN);
  }

  public JHipsterModule buildGitHubActionsGradleModule(JHipsterModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.GRADLE);
  }

  private JHipsterModule buildGitHubActionsModule(JHipsterModuleProperties properties, JavaBuildTool javaBuildTool) {
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
