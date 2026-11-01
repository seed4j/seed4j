package com.seed4j.generator.client.angular.i18n;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.ANGULAR;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notContaining;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.EndOfFileReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

class AngularI18nModuleFactory {

  private static final Pattern ENABLE_PROD_MODE_PATTERN = Pattern.compile(
    "(import \\{\\s*enableProdMode\\s*)(\\}\\s*from\\s*'@angular/core';)"
  );

  private static final String I18N_MAIN_IMPORTS = """
    import { TranslocoHttpLoader } from './transloco-loader';
    import { provideTransloco } from '@jsverse/transloco';
  """;

  private static final String I18N_MAIN_CONFIG = """
    provideTransloco({
      config: {
        availableLangs: ['en', 'fr'],
        defaultLang: 'en',
          // Remove this option if your application doesn't support changing language in runtime.
          reRenderOnLangChange: true,
          prodMode: !isDevMode(),
      },
      loader: TranslocoHttpLoader
    })
  """;

  private static final Seed4JSource APP_SOURCE = from("client/angular/i18n");
  private static final Seed4JSource HOME_CONTEXT_SOURCE = from("client/angular/i18n/src/main/webapp");
  private static final Seed4JSource ASSETS_SOURCE = from("client/angular/i18n/src/main/webapp/content");

  private static final Seed4JDestination INDEX_CLIENT = to("src/main/webapp/");
  private static final Seed4JDestination INDEX_DEST = to(".");
  private static final Seed4JDestination ASSETS_DEST = INDEX_CLIENT.append("content/i18n");

  private static final String PROVIDER_NEEDLE = "// seed4j-needle-main-ts-provider";

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("@jsverse/transloco"), ANGULAR)
        .and()
      .files()

      .batch(APP_SOURCE, INDEX_DEST)
        .addFile("transloco.config.ts")
        .and()
      .batch(HOME_CONTEXT_SOURCE, INDEX_CLIENT)
        .addFile("transloco-loader.ts")
        .and()
      .batch(HOME_CONTEXT_SOURCE.append("app"), INDEX_CLIENT.append("app"))
        .addFile("transloco-testing-module.ts")
        .and()
      .batch(ASSETS_SOURCE, ASSETS_DEST)
        .addFile("en.json")
        .addFile("fr.json")
        .and()
      .and()
      .mandatoryReplacements()
        .in(path(INDEX_CLIENT + "main.ts"))
          .add(fileStart(), I18N_MAIN_IMPORTS)
          .add(lineAfterText(PROVIDER_NEEDLE), I18N_MAIN_CONFIG)
          .add(regex(notContaining(", isDevMode"), ENABLE_PROD_MODE_PATTERN), "$1, isDevMode $2")
          .and()
        .and()
      .optionalReplacements()
        .in(path(INDEX_CLIENT + "/app/app.html"))
          .add(new EndOfFileReplacer(notContaining("<ng-container *transloco=\"let t\">")), "</ng-container>")
          .add(fileStart(), "<ng-container *transloco=\"let t\">")
          .add(lineAfterRegex("Angular \\+ TypeScript"), properties.indentation().times(2) + "<h2>{{ t('home.translationEnabled') }}</h2>")
          .and()
        .in(path(INDEX_CLIENT + "app/" + "app.ts"))
          .add(lineAfterText("@angular/core';"), "import { TranslocoDirective } from '@jsverse/transloco';")
          .add(regex(notContaining(", TranslocoDirective"), "(imports: \\[.*)(\\],)"), "$1, TranslocoDirective$2")
          .and()
        .in(path(INDEX_CLIENT + "app/app.spec.ts"))
          .add(fileStart(), "import { getTranslocoModule } from './transloco-testing-module';\n")
          .add(regex(notContaining(", getTranslocoModule()"), "(imports: \\[.*)(\\],)"), "$1, getTranslocoModule()$2")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
