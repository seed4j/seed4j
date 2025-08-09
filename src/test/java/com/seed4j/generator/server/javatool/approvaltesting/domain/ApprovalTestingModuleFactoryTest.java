package com.seed4j.generator.server.javatool.approvaltesting.domain;

import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class ApprovalTestingModuleFactoryTest {

  private final ApprovalTestingModuleFactory factory = new ApprovalTestingModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/approval-testing.md", "src/test/java/com/mycompany/myapp/PackageSettings.java")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.approvaltests</groupId>
              <artifactId>approvaltests</artifactId>
              <version>${approvaltests.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFile(".gitignore")
      .containing("src/test/resources/**/*.received.txt");
  }
}
