package com.seed4j.generator.server.springboot.thymeleaf.init.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SpringBootThymeleafModuleFactoryTest {

  private static final SpringBootThymeleafModuleFactory factory = new SpringBootThymeleafModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-thymeleaf</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>nz.net.ultraq.thymeleaf</groupId>
              <artifactId>thymeleaf-layout-dialect</artifactId>
              <version>${thymeleaf-layout-dialect.version}</version>
            </dependency>
        """
      );
  }
}
