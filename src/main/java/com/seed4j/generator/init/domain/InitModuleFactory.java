package com.seed4j.generator.init.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.packageName;
import static com.seed4j.module.domain.SeedModule.scriptCommand;
import static com.seed4j.module.domain.SeedModule.scriptKey;
import static com.seed4j.module.domain.SeedModule.to;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class InitModuleFactory {

  private static final SeedSource SOURCE = from("init");
  private static final SeedDestination DESTINATION = to(".");
  private static final SeedSource SOURCE_COMMON = from("client/common");

  private final NodeVersions nodeVersions;

  public InitModuleFactory(NodeVersions nodeVersions) {
    Assert.notNull("nodeVersions", nodeVersions);

    this.nodeVersions = nodeVersions;
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
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

  private String endOfLine(SeedModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
