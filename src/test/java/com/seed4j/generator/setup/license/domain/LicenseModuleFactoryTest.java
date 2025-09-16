package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import java.time.Year;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

@UnitTest
class LicenseModuleFactoryTest {

  private static final LicenseModuleFactory factory = new LicenseModuleFactory();

  @Test
  void shouldBuildMitModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    int year = Year.now(ZoneId.systemDefault()).getValue();

    Seed4JModule module = factory.buildMitModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Copyright %d".formatted(year));
  }

  @Test
  void shouldBuildApacheModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildApacheModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Apache License");
  }
}
