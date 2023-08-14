package tech.jhipster.lite.generator.server.javatool.memoizer.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JavaMemoizersModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/memoizers");

  private static final String COMMON_DOMAIN = "shared/memoizer";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(COMMON_DOMAIN);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(COMMON_DOMAIN);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(SOURCE.template("Memoizers.java"), mainDestination.append("domain").append("Memoizers.java"))
        .add(SOURCE.template("MemoizersTest.java"), testDestination.append("domain").append("MemoizersTest.java"))
        .and()
      .build();
    //@formatter:on
  }
}
