package tech.jhipster.lite.generator.init.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.npm.domain.NpmVersionSource;
import tech.jhipster.lite.npm.domain.NpmVersions;

public class InitModuleFactory {

  private static final JHipsterSource SOURCE = from("init");
  private static final JHipsterDestination DESTINATION = to(".");

  private final NpmVersions npmVersions;

  public InitModuleFactory(NpmVersions npmVersions) {
    Assert.notNull("npmVersions", npmVersions);

    this.npmVersions = npmVersions;
  }

  public JHipsterModule buildFullModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return minimalModuleBuilder(properties)
      .context()
        .put("prettierEndOfLine", endOfLine(properties))
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("nodeVersion", npmVersions.get("node", NpmVersionSource.COMMON).get())
        .and()
      .files()
        .batch(SOURCE, DESTINATION)
          .addFile(".lintstagedrc.js")
          .addFile(".prettierignore")
          .addTemplate(".prettierrc")
          .addTemplate("package.json")
          .and()
        .addExecutable(SOURCE.append(".husky").file("pre-commit"), DESTINATION.append(".husky/pre-commit"))
        .and()
      .packageJson()
        .addDevDependency(packageName("@prettier/plugin-xml"), COMMON)
        .addDevDependency(packageName("husky"), COMMON)
        .addDevDependency(packageName("lint-staged"), COMMON)
        .addDevDependency(packageName("prettier"), COMMON)
        .addDevDependency(packageName("prettier-plugin-java"), COMMON)
        .addDevDependency(packageName("prettier-plugin-packagejson"), COMMON)
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildMinimalModule(JHipsterModuleProperties properties) {
    return minimalModuleBuilder(properties).build();
  }

  private JHipsterModuleBuilder minimalModuleBuilder(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("editorConfigEndOfLine", endOfLine(properties))
        .and()
      .files()
        .batch(SOURCE, DESTINATION)
          .addTemplate("README.md")
          .addTemplate(".editorconfig")
          .addFile(".eslintignore")
          .and()
        .add(SOURCE.file("gitignore"), to(".gitignore"))
        .add(SOURCE.file("gitattributes"), to(".gitattributes"))
      .and();
    //@formatter:on
  }

  private String endOfLine(JHipsterModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
