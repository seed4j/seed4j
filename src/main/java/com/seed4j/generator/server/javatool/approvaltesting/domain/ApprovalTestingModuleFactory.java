package com.seed4j.generator.server.javatool.approvaltesting.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;
import static com.seed4j.module.domain.SeedModule.versionSlug;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ApprovalTestingModuleFactory {

  private static final SeedSource SOURCE = from("server/javatool/approvaltesting");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    SeedDestination testDestination = toSrcTestJava().append(packagePath);

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
