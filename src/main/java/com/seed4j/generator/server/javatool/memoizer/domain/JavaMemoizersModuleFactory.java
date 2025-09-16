package com.seed4j.generator.server.javatool.memoizer.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JavaMemoizersModuleFactory {

  private static final Seed4JSource SOURCE = from("server/javatool/memoizers");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String COMMON_DOMAIN = "shared/memoizer";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(COMMON_DOMAIN);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(COMMON_DOMAIN);

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
