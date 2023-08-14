package tech.jhipster.lite.generator.typescript.optional.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class OptionalTypescriptModuleFactory {

  private static final JHipsterSource SOURCE = from("typescript");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("jest"), VersionSource.COMMON)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("ts-jest"), COMMON)
        .addScript(scriptKey("jest"), scriptCommand("jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache"))
        .addScript(scriptKey("test"), scriptCommand("npm run jest --"))
        .addScript(scriptKey("test:watch"), scriptCommand("jest --watch"))
        .addScript(scriptKey("test:watch:all"), scriptCommand("jest --watchAll"))
      .and()
      .files()
        .add(SOURCE.file("jest.config.js"), to("jest.config.js"))
        .add(from("typescript/webapp/common/domain/optional/").file("Optional.ts"), to("src/main/webapp/app/common/domain/Optional.ts"))
        .add(from("typescript/test/javascript/spec/common/domain/optional/").file("Optional.spec.ts"), to("src/test/javascript/spec/common/domain/Optional.spec.ts"))
      .and()
      .build();
    //@formatter:on
  }
}
