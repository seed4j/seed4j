package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import java.time.Year;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

@UnitTest
class LicenseModuleFactoryTest {

  private static final LicenseModuleFactory factory = new LicenseModuleFactory();

  @Test
  void shouldBuildMitModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
    int year = Year.now(ZoneId.systemDefault()).getValue();

    JHipsterModule module = factory.buildMitModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Copyright %d".formatted(year));
  }

  @Test
  void shouldBuildApacheModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildApacheModule(properties);

    assertThatModuleWithFiles(module).hasFile("LICENSE.txt").containing("Apache License");
  }
}
