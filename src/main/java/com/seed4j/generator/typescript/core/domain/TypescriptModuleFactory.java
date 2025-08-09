package com.seed4j.generator.typescript.core.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.packageName;
import static com.seed4j.module.domain.JHipsterModule.runScriptCommandWith;
import static com.seed4j.module.domain.JHipsterModule.scriptCommand;
import static com.seed4j.module.domain.JHipsterModule.scriptKey;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.nodejs.JHLiteNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.packagejson.NodeModuleFormat.MODULE;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.nodejs.NodeLazyPackagesInstaller;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class TypescriptModuleFactory {

  private static final SeedSource SOURCE = from("typescript");
  private final NodeLazyPackagesInstaller nodeLazyPackagesInstaller;

  public TypescriptModuleFactory(NodeLazyPackagesInstaller nodeLazyPackagesInstaller) {
    this.nodeLazyPackagesInstaller = nodeLazyPackagesInstaller;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .type(MODULE)
        .addDevDependency(packageName("typescript"), COMMON)
        .addDevDependency(packageName("@tsconfig/recommended"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/parser"), COMMON)
        .addDevDependency(packageName("@vitest/coverage-istanbul"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("eslint-config-prettier"), COMMON)
        .addDevDependency(packageName("@eslint/js"), COMMON)
        .addDevDependency(packageName("globals"), COMMON)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addDevDependency(packageName("typescript-eslint"), COMMON)
        .addDevDependency(packageName("vite-tsconfig-paths"), COMMON)
        .addDevDependency(packageName("vitest"), COMMON)
        .addDevDependency(packageName("vitest-sonar-reporter"), COMMON)
        .addScript(scriptKey("lint"), scriptCommand("eslint ."))
        .addScript(scriptKey("test"), runScriptCommandWith(properties.nodePackageManager(), "watch:test"))
        .addScript(scriptKey("test:coverage"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:tsc"), scriptCommand("tsc --noEmit --watch"))
        .addScript(scriptKey("watch:test"), scriptCommand("vitest --"))
        .and()
      .postActions()
        .add(context -> nodeLazyPackagesInstaller.runInstallIn(context.projectFolder(), properties.nodePackageManager()))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addFile("tsconfig.json")
          .addTemplate("vitest.config.ts")
          .addTemplate("eslint.config.js")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
