package com.seed4j.generator.server.javatool.memoizer.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JavaMemoizersModuleFactory {

  private static final SeedSource SOURCE = from("server/javatool/memoizers");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String COMMON_DOMAIN = "shared/memoizer";

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(COMMON_DOMAIN);
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(COMMON_DOMAIN);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(MAIN_SOURCE.template("Memoizers.java"), mainDestination.append("domain").append("Memoizers.java"))
        .add(TEST_SOURCE.template("MemoizersTest.java"), testDestination.append("domain").append("MemoizersTest.java"))
        .and()
      .build();
    // @formatter:on
  }
}
