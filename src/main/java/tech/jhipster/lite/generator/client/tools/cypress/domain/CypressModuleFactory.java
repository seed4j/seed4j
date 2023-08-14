package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.regex.Pattern;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CypressModuleFactory {

  private static final JHipsterSource SOURCE = from("client/common/cypress");

  private static final String CYPRESS_TEST = "src/test/javascript/integration";

  private static final JHipsterDestination CYPRESS_DESTINATION = to(CYPRESS_TEST);

  private static final String PRIMARY_APP = "common/primary/app";

  private static final String CYPRESS_EXCLUSION = "\"src/test/javascript/integration/**/*.ts\"";
  private static final String EXCLUDE_KEY = "\"exclude\"";
  private static final RegexReplacer NEW_EXCLUSION_REPLACER = new RegexReplacer(
    (currentContent, replacement) -> !currentContent.contains(EXCLUDE_KEY),
    Pattern.compile("\\n.*\\}\\s*$")
  );

  private static final RegexReplacer EXISTING_EXCLUSION_REPLACER = new RegexReplacer(
    (currentContent, replacement) -> currentContent.contains(EXCLUDE_KEY) && !currentContent.contains(CYPRESS_EXCLUSION),
    Pattern.compile("(" + EXCLUDE_KEY + "\\s*:\\s*\\[[^\\]]+)\\]")
  );

  private static final RegexReplacer EMPTY_EXCLUSION_REPLACER = new RegexReplacer(
    (currentContent, replacement) -> currentContent.contains(EXCLUDE_KEY) && !currentContent.contains(CYPRESS_EXCLUSION),
    Pattern.compile("(" + EXCLUDE_KEY + "\\s*:\\s*\\[\\s*)\\]")
  );

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("cypress"), VersionSource.COMMON)
        .addDevDependency(packageName("eslint-plugin-cypress"), VersionSource.COMMON)
        .addScript(scriptKey("e2e"), scriptCommand("npm run test:component"))
        .addScript(scriptKey("e2e:headless"), scriptCommand("npm run test:component:headless"))
        .addScript(scriptKey("test:component"), scriptCommand("cypress open --config-file src/test/javascript/integration/cypress-config.ts"))
        .addScript(
          scriptKey("test:component:headless"),
          scriptCommand("cypress run --headless --config-file src/test/javascript/integration/cypress-config.ts")
        )
        .and()
      .files()
        .batch(SOURCE.append(CYPRESS_TEST), CYPRESS_DESTINATION)
          .addFile("cypress-config.ts")
          .addFile(".eslintrc.js")
          .addFile("tsconfig.json")
          .and()
        .add(
          SOURCE.append(CYPRESS_TEST).append(PRIMARY_APP).file("Home.spec.ts"),
          CYPRESS_DESTINATION.append(PRIMARY_APP).append("Home.spec.ts")
        )
        .and()
      .optionalReplacements()
        .in(path("tsconfig.json"))
        .add(EXISTING_EXCLUSION_REPLACER, "$1, "+CYPRESS_EXCLUSION+"]")
        .add(EMPTY_EXCLUSION_REPLACER, "$1"+CYPRESS_EXCLUSION+"]")
        .add(NEW_EXCLUSION_REPLACER, newExclusionNode(properties))
        .and()
        .and()
      .build();
    //@formatter:on
  }

  private String newExclusionNode(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append(",")
      .append(LINE_BREAK)
      .append(properties.indentation().spaces())
      .append("\"exclude\": [" + CYPRESS_EXCLUSION + "]")
      .append(LINE_BREAK)
      .append("}")
      .toString();
  }
}
