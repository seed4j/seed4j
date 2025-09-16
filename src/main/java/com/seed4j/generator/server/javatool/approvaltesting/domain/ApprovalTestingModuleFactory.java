package com.seed4j.generator.server.javatool.approvaltesting.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ApprovalTestingModuleFactory {

  private static final Seed4JSource SOURCE = from("server/javatool/approvaltesting");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Approval Testing"), SOURCE.append("approval-testing.md"))
      .javaDependencies()
        .addTestDependency(groupId("com.approvaltests"), artifactId("approvaltests"), versionSlug("approvaltests"))
        .and()
      .files()
        .add(SOURCE.append("test").template("PackageSettings.java"), testDestination.append("PackageSettings.java"))
        .and()
      .gitIgnore()
        .comment("Approval Testing")
        .pattern("src/test/resources/**/*.received.txt")
        .and()
      .build();
    // @formatter:on
  }
}
