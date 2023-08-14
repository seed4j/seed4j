package tech.jhipster.lite.generator.server.javatool.memoizer.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JavaMemoizersModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/memoizers");

  private static final String COMMON_DOMAIN = "common/domain";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("Memoizers.java"), toSrcMainJava().append(packagePath).append(COMMON_DOMAIN).append("Memoizers.java"))
        .add(SOURCE.template("MemoizersTest.java"), toSrcTestJava().append(packagePath).append(COMMON_DOMAIN).append("MemoizersTest.java"))
        .and()
      .build();
    //@formatter:on
  }
}
