package com.seed4j.generator.prettier.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.preCommitCommands;
import static com.seed4j.module.domain.Seed4JModule.scriptCommand;
import static com.seed4j.module.domain.Seed4JModule.scriptKey;
import static com.seed4j.module.domain.Seed4JModule.stagedFilesFilter;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PrettierModuleFactory {

  private static final Seed4JSource SOURCE = from("prettier");
  private static final Seed4JDestination DESTINATION = to(".");

  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public PrettierModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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

  private String endOfLine(Seed4JModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
