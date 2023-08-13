package tech.jhipster.lite.generator.server.javatool.memoizer.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class JavaMemoizersModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/memoizers");

  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");

  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String COMMON_DOMAIN = "common/domain";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(MAIN_SOURCE.template("Memoizers.java"), toSrcMainJava().append(packagePath).append(COMMON_DOMAIN).append("Memoizers.java"))
        .add(TEST_SOURCE.template("MemoizersTest.java"), toSrcTestJava().append(packagePath).append(COMMON_DOMAIN).append("MemoizersTest.java"))
        .and()
      .build();
    //@formatter:on
  }
}
