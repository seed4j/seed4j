package com.seed4j.generator.server.pagination.domainmodel.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PaginationDomainModuleFactoryTest {

  private static final PaginationDomainModuleFactory factory = new PaginationDomainModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.apache.commons</groupId>
              <artifactId>commons-lang3</artifactId>
              <version>${commons-lang3.version}</version>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/java/com/seed4j/growth/shared/pagination/package-info.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/shared/pagination/domain", "MyAppPage.java", "MyAppPageable.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/pagination/domain",
        "MyAppPageTest.java",
        "MyAppPageableTest.java",
        "MyAppPagesFixture.java"
      );
  }
}
