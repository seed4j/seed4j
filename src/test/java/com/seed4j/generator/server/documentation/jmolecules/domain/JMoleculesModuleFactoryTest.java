package com.seed4j.generator.server.documentation.jmolecules.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JMoleculesModuleFactoryTest {

  private static final JMoleculesModuleFactory factory = new JMoleculesModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<jmolecules-bom.version>")
      .containing(
        """
              <dependency>
                <groupId>org.jmolecules</groupId>
                <artifactId>jmolecules-bom</artifactId>
                <version>${jmolecules-bom.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.jmolecules</groupId>
              <artifactId>jmolecules-ddd</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.jmolecules</groupId>
              <artifactId>jmolecules-hexagonal-architecture</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.jmolecules</groupId>
              <artifactId>jmolecules-events</artifactId>
            </dependency>
        """
      );
  }
}
