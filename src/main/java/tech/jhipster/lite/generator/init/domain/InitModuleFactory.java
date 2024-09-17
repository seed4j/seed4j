package tech.jhipster.lite.generator.init.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.COMMON;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class InitModuleFactory {

  private static final JHipsterSource SOURCE = from("init");
  private static final JHipsterDestination DESTINATION = to(".");
  private static final JHipsterSource SOURCE_COMMON = from("client/common");

  private final NpmVersions npmVersions;

  public InitModuleFactory(NpmVersions npmVersions) {
    Assert.notNull("npmVersions", npmVersions);

    this.npmVersions = npmVersions;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("nodeMajorVersion", npmVersions.nodeVersion().majorVersion())
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
    //@formatter:on
  }

  private String endOfLine(JHipsterModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
