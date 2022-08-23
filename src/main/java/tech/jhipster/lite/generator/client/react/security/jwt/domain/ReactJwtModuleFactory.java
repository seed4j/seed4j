package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.RegexNeedleBeforeReplacer;

public class ReactJwtModuleFactory {

  private static final JHipsterSource SOURCE = from("client/react/src");

  private static final JHipsterSource APP_SOURCE = SOURCE.append("main/webapp/app");
  private static final JHipsterSource TEST_JAVASCRIPT_SOURCE = SOURCE.append("test/javascript/spec");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");
  private static final JHipsterDestination COMMON_DESTINATION = APP_DESTINATION.append("common");

  private static final JHipsterDestination TEST_DESTINATION = to("src/test/javascript/spec/");

  private static final RegexNeedleBeforeReplacer LOGIN_FORM_MATCHER = lineBeforeRegex(
    "[  ]{0,10}[<\\/div>]{0,1}\n{0,5}[  ]{0,10}<\\/div>\n{0,5}[  ]{0,10}[);]{0,2}\n{0,5}\\}\n{0,5}[  ]{0,10}export default App;"
  );

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("react-hook-form"), REACT)
        .addDependency(packageName("axios"), COMMON)
        .addDependency(packageName("@nextui-org/react"), REACT)
        .addDevDependency(packageName("sass"), REACT)
        .and()
      .files()
        .add(APP_SOURCE.template("common/services/storage.ts"), COMMON_DESTINATION.append("services/storage.ts"))
        .add(APP_SOURCE.append("login/primary/loginForm").template("index.tsx"), APP_DESTINATION.append("login/primary/loginForm/index.tsx"))
        .batch(APP_SOURCE.append("login/primary/loginModal"), APP_DESTINATION.append("login/primary/loginModal/"))
          .addTemplate("index.tsx")
          .addTemplate("interface.d.ts")
          .addTemplate("LoginModal.scss")
          .and()
        .add(APP_SOURCE.template("login/services/login.ts"), APP_DESTINATION.append("login/services/login.ts"))
        .add(TEST_JAVASCRIPT_SOURCE.template("login/services/login.test.ts"), TEST_DESTINATION.append("login/services/login.test.ts"))
        .add(
          TEST_JAVASCRIPT_SOURCE.template("login/primary/loginForm/index.test.tsx"),
          TEST_DESTINATION.append("login/primary/loginForm/index.test.tsx")
        )
        .add(
          TEST_JAVASCRIPT_SOURCE.template("login/primary/loginModal/index.test.tsx"),
          TEST_DESTINATION.append("login/primary/loginModal/index.test.tsx")
        )
        .add(TEST_JAVASCRIPT_SOURCE.template("common/services/storage.test.ts"), TEST_DESTINATION.append("common/services/storage.test.ts"))
        .and()
      .mandatoryReplacements()
        .in("src/main/webapp/app/common/primary/app/App.tsx")
          .add(lineBeforeText("function App() {"), "import LoginForm from '@/login/primary/loginForm';" + LINE_BREAK)
          .add(LOGIN_FORM_MATCHER, properties.indentation().times(4) + "<LoginForm />")
          .and()
        .and()
      .optionalReplacements()
        .in("src/main/webapp/app/common/primary/app/App.css")
          .add(text("  -moz-osx-font-smoothing: grayscale;"), authenticationStyle())
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String authenticationStyle() {
    return """
           -moz-osx-font-smoothing: grayscale;
           display: flex;
           flex-direction: column;
           justify-content:center;
           align-items: center;
        """;
  }
}
