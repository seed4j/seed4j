package com.seed4j.generator.prettier.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.preCommitCommands;
import static com.seed4j.module.domain.JHipsterModule.scriptCommand;
import static com.seed4j.module.domain.JHipsterModule.scriptKey;
import static com.seed4j.module.domain.JHipsterModule.stagedFilesFilter;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.nodejs.SeedNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PrettierModuleFactory {

  private static final SeedSource SOURCE = from("prettier");
  private static final SeedDestination DESTINATION = to(".");

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public PrettierModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("endOfLine", endOfLine(properties))
        .and()
      .files()
        .batch(SOURCE, DESTINATION)
          .addFile(".prettierignore")
          .addTemplate(".prettierrc")
          .and()
        .and()
      .packageJson()
        .addDevDependency(packageName("@prettier/plugin-xml"), COMMON)
        .addDevDependency(packageName("prettier"), COMMON)
        .addDevDependency(packageName("prettier-plugin-gherkin"), COMMON)
        .addDevDependency(packageName("prettier-plugin-java"), COMMON)
        .addDevDependency(packageName("prettier-plugin-organize-imports"), COMMON)
        .addDevDependency(packageName("prettier-plugin-packagejson"), COMMON)
        .addScript(scriptKey("prettier:check"), scriptCommand("prettier --check ."))
        .addScript(scriptKey("prettier:format"), scriptCommand("prettier --write ."))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .preCommitActions(stagedFilesFilter("*.{md,json*,yml,html,css,scss,java,xml,feature}"), preCommitCommands("['prettier --write']"))
      .build();
    // @formatter:on
  }

  private String endOfLine(SeedModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
