package tech.jhipster.lite.generator.prettier.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class PrettierModuleFactory {

  private static final JHipsterSource SOURCE = from("prettier");
  private static final JHipsterDestination DESTINATION = to(".");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", properties.projectBaseName().kebabCase())
        .put("endOfLine", endOfLine(properties))
        .and()
      .files()
        .batch(SOURCE, DESTINATION)
          .addFile(".lintstagedrc.js")
          .addFile(".prettierignore")
          .addTemplate(".prettierrc")
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
        .addScript(scriptKey("prepare"), scriptCommand("husky install"))
        .addScript(scriptKey("prettier:check"), scriptCommand("prettier --check '{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}'"))
        .addScript(scriptKey("prettier:format"), scriptCommand("prettier --write '{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}'"))
        .and()
      .build();
    //@formatter:on
  }

  private String endOfLine(JHipsterModuleProperties properties) {
    return properties.getOrDefaultString("endOfLine", "lf");
  }
}
