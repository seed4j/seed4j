package tech.jhipster.lite.generator.client.react.i18n.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ReactI18nModuleFactory {

  private static final JHipsterSource APP_SOURCE = from("client/react/i18n/src/main/webapp/app");
  private static final JHipsterSource ASSETS_FR_SOURCE = from("client/react/i18n/src/main/webapp/assets/locales/fr");
  private static final JHipsterSource ASSETS_EN_SOURCE = from("client/react/i18n/src/main/webapp/assets/locales/en");

  private static final String INDEX = "src/main/webapp/";
  private static final String INDEX_TEST = "src/test/webapp/unit/common/primary/app/";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
      .addDependency(packageName("i18next"), VersionSource.COMMON)
      .addDependency(packageName("i18next-browser-languagedetector"), VersionSource.COMMON)
      .addDependency(packageName("i18next-http-backend"), VersionSource.COMMON)
      .addDependency(packageName("react-i18next"), VersionSource.REACT)
      .and()
      .files()
      .batch(APP_SOURCE, to(INDEX + "/app"))
        .addFile("i18n.ts")
        .and()
      .batch(ASSETS_EN_SOURCE, to(INDEX + "assets/locales/en/"))
        .addFile("translation.json")
        .and()
      .batch(ASSETS_FR_SOURCE, to(INDEX + "assets/locales/fr/"))
        .addFile("translation.json")
        .and()
      .and()
      .mandatoryReplacements()
        .in(path(INDEX + "app/common/primary/app/App.tsx"))
          .add(lineAfterText("import ReactLogo from '@assets/ReactLogo.png';"), "import { useTranslation } from 'react-i18next';")
          .add(lineBeforeText("return ("), properties.indentation().times(1) + "const { t } = useTranslation();" + LINE_BREAK)
          .add(lineAfterText("</h1>"), LINE_BREAK +
            properties.indentation().times(4) + "<p>{t('translationEnabled')}</p>")
          .and()
        .in(path(INDEX + "app/index.tsx"))
          .add(lineAfterText("import './index.css';"), "import './i18n';" + LINE_BREAK)
          .and()
        .in(path(INDEX_TEST + "App.spec.tsx"))
          .add(append(), LINE_BREAK + """
            describe('App I18next', () => {
             it('renders with translation', () => {
               vi.mock('react-i18next', () => ({
                 useTranslation: () => {
                   return {
                     t: vi.fn().mockImplementation((_str: string) => 'Internationalization enabled'),
                     i18n: {
                       changeLanguage: () => new Promise(() => {}),
                     },
                   };
                 },
               }));
               render(<App />);
               const { getAllByText } = render(<App />);
               const title = getAllByText('Internationalization enabled');
               expect(title).toBeTruthy();
             });
           });""" )
        .and()
      .and()
      .build();
    //@formatter:off
  }
}
