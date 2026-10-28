package com.seed4j.generator.ci.github.actions;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;

class GitHubActionsModuleFactory {

  private static final Seed4JSource SOURCE = from("ci/github/actions/.github");

  private final NodeVersions nodeVersions;

  GitHubActionsModuleFactory(Seed4JModuleServices services) {
    this.nodeVersions = services.nodeVersions();
  }

  Seed4JModule buildGitHubActionsMavenModule(Seed4JModuleProperties properties) {
    return buildGitHubActionsModule(properties, JavaBuildTool.MAVEN);
  }

  Seed4JModule buildGitHubActionsGradleModule(Seed4JModuleProperties properties) {
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
