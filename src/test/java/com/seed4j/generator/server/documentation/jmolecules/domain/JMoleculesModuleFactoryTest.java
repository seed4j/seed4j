package com.seed4j.generator.server.documentation.jmolecules.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JMoleculesModuleFactoryTest {

  private static final JMoleculesModuleFactory factory = new JMoleculesModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

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
