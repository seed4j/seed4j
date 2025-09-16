package com.seed4j.generator.client.react.i18n.domain;

import static com.seed4j.module.domain.Seed4JModule.LINE_BREAK;
import static com.seed4j.module.domain.Seed4JModule.append;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineAfterText;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.packageName;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.text;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.REACT;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ReactI18nModuleFactory {

  private static final Seed4JSource APP_SOURCE = from("client/common/i18n");
  private static final Seed4JSource HOME_CONTEXT_SOURCE = from("client/common/i18n/app");
  private static final Seed4JSource ASSETS_SOURCE = from("client/common/i18n/app/locales");

  private static final String INDEX = "src/main/webapp/app/";
  private static final String INDEX_TEST = "src/test/";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("i18next"), COMMON)
        .addDependency(packageName("i18next-browser-languagedetector"), COMMON)
        .addDependency(packageName("i18next-http-backend"), COMMON)
        .addDependency(packageName("react-i18next"), REACT)
        .and()
      .files()
      .batch(APP_SOURCE, to(INDEX))
        .addFile("i18n.ts")
        .addFile("Translations.ts")
        .and()
      .batch(HOME_CONTEXT_SOURCE, to(INDEX + "home/"))
        .addFile("HomeTranslations.ts")
        .and()
      .batch(ASSETS_SOURCE, to(INDEX + "home/locales/"))
        .addFile("en.ts")
        .addFile("fr.ts")
        .and()
      .batch(APP_SOURCE, to(INDEX_TEST + "webapp/unit"))
        .addFile("i18n.spec.ts")
        .and()
      .and()
      .mandatoryReplacements()
        .in(path(INDEX + "i18n.ts"))
          .add(lineAfterText("import LanguageDetector from 'i18next-browser-languagedetector';"), "import { initReactI18next } from 'react-i18next';")
          .add(text(".use(LanguageDetector)"), ".use(initReactI18next).use(LanguageDetector)")
        .and()
        .in(path(INDEX + "home/infrastructure/primary/HomePage.tsx"))
          .add(lineBeforeText("function HomePage"), "import { useTranslation } from 'react-i18next';" + LINE_BREAK)
          .add(lineBeforeText("return ("), properties.indentation().times(1) + "const { t } = useTranslation();" + LINE_BREAK)
          .add(lineAfterText("</h1>"), LINE_BREAK +
            properties.indentation().times(4) + "<p>{t('home.translationEnabled')}</p>")
          .and()
        .in(path(INDEX + "index.tsx"))
          .add(lineBeforeText("const container = document.getElementById('root');"), "import './i18n';" + LINE_BREAK)
          .and()
        .in(path(INDEX_TEST + "webapp/unit/home/infrastructure/primary/HomePage.spec.tsx"))
          .add(append(), LINE_BREAK + """
           describe('Home I18next', () => {
             it('should render with translation', () => {
               vi.mock('react-i18next', () => ({
                 useTranslation: () => {
                   return {
                     t: vi.fn().mockImplementation(() => 'Internationalization enabled'),
                   };
                 },
               }));
               const { getAllByText } = render(<HomePage />);
               const title = getAllByText('Internationalization enabled');
               expect(title).toBeTruthy();
             });
           });""" )
        .and()
      .and()
      .build();
    // @formatter:on
  }
}
