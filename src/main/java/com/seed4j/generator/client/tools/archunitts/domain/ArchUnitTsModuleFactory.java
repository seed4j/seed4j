package com.seed4j.generator.client.tools.archunitts.domain;

import static com.seed4j.module.domain.Seed4JModule.*;
import static com.seed4j.module.domain.nodejs.Seed4JNodePackagesVersionSource.COMMON;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ArchUnitTsModuleFactory {

  private static final Seed4JSource SOURCE = from("client/tools/archunitts");

  private static final Seed4JDestination APP_DESTINATION = to("src/main/webapp/app/");
  private static final Seed4JDestination TEST_DESTINATION = to("src/test/webapp/unit/");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("arch-unit-ts"), COMMON)
        .and()
      .files()
        .add(SOURCE.template("arch-unit-ts.json"), to("arch-unit-ts.json"))
        .add(SOURCE.template("SharedKernel.ts"), APP_DESTINATION.append("SharedKernel.ts"))
        .add(SOURCE.template("BusinessContext.ts"), APP_DESTINATION.append("BusinessContext.ts"))
        .add(SOURCE.template("BusinessContextPackageInfo.ts"), APP_DESTINATION.append("home").append("package-info.ts"))
        .add(SOURCE.template("SharedKernelPackageInfo.ts"), APP_DESTINATION.append("shared").append("package-info.ts"))
        .add(SOURCE.template("HexagonalArchTest.spec.ts"), TEST_DESTINATION.append("HexagonalArchTest.spec.ts"))
        .and()
      .build();
    // @formatter:on
  }
}
