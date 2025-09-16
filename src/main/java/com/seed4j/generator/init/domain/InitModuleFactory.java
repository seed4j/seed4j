package com.seed4j.generator.init.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.scriptCommand;
import static com.seed4j.module.domain.Seed4JModule.scriptKey;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class InitModuleFactory {

  private static final Seed4JSource SOURCE = from("init");
  private static final Seed4JDestination DESTINATION = to(".");
  private static final Seed4JSource SOURCE_COMMON = from("client/common");

  private final NodeVersions nodeVersions;

  public InitModuleFactory(NodeVersions nodeVersions) {
    Assert.notNull("nodeVersions", nodeVersions);

    this.nodeVersions = nodeVersions;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("nodeMajorVersion", nodeVersions.nodeVersion().majorVersion())
        .put("nodePackageManagerCommand", properties.nodePackageManager().command())
        .put("nodePackageManagerPackage", properties.nodePackageManager().packageName())
        .put("nodePackageManagerVersion", nodeVersions.packageManagerVersion(properties.nodePackageManager()))
        .put("endOfLine", endOfLine(properties))
        .and()
      .files()
        .batch(SOURCE, DESTINATION)
          .addTemplate("README.md")
          .addTemplate("package.json")
          .addTemplate(".editorconfig")
          .addFile(".lintstagedrc.cjs")
          .and()
        .batch(SOURCE_COMMON, DESTINATION)
          .addFile(".npmrc")
          .and()
        .addExecutable(SOURCE.append(".husky").file("pre-commit"), DESTINATION.append(".husky/pre-commit"))
        .add(SOURCE.file("gitignore"), to(".gitignore"))
        .add(SOURCE.file("gitattributes"), to(".gitattributes"))
      .and()
      .packageJson()
        .addDevDependency(packageName("husky"), COMMON)
        .addDevDependency(packageName("lint-staged"), COMMON)
        .addScript(scriptKey("prepare"), scriptCommand("husky"))
        .and()
      .build();
    // @formatter:on
  }

  private String endOfLine(Seed4JModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
