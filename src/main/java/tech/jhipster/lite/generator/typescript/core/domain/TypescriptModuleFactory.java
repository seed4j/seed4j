package tech.jhipster.lite.generator.typescript.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.COMMON;
import static tech.jhipster.lite.module.domain.packagejson.NodeModuleFormat.MODULE;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class TypescriptModuleFactory {

  private static final JHipsterSource SOURCE = from("typescript");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
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
        .addDevDependency(packageName("globals"), COMMON)
        .addDevDependency(packageName("npm-run-all2"), COMMON)
        .addDevDependency(packageName("typescript-eslint"), COMMON)
        .addDevDependency(packageName("vite-tsconfig-paths"), COMMON)
        .addDevDependency(packageName("vitest"), COMMON)
        .addDevDependency(packageName("vitest-sonar-reporter"), COMMON)
        .addScript(scriptKey("lint"), scriptCommand("eslint ."))
        .addScript(scriptKey("test"), scriptCommand("npm run watch:test"))
        .addScript(scriptKey("test:coverage"), scriptCommand("vitest run --coverage"))
        .addScript(scriptKey("watch"), scriptCommand("npm-run-all --parallel watch:*"))
        .addScript(scriptKey("watch:tsc"), scriptCommand("tsc --noEmit --watch"))
        .addScript(scriptKey("watch:test"), scriptCommand("vitest --"))
        .and()
      .files()
        .batch(SOURCE, to("."))
          .addFile("tsconfig.json")
          .addTemplate("vitest.config.ts")
          .addTemplate("eslint.config.js")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
