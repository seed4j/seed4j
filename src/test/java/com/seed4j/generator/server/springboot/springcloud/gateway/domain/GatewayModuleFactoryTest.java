package com.seed4j.generator.server.springboot.springcloud.gateway.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GatewayModuleFactoryTest {

  private static final GatewayModuleFactory factory = new GatewayModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing(
        """
              <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-bootstrap</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-gateway</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/bootstrap.yml")
      .containing(
        // language=yaml
        """
        spring:
          application:
            name: myApp
          cloud:
            gateway:
              discovery:
                locator:
                  enabled: true
                  filters[0]:
                    args[regexp]: '''/services/'' + serviceId.toLowerCase() + ''/(?<remaining>.*)'''
                    args[replacement]: '''/${remaining}'''
                    name: RewritePath
                  lower-case-service-id: true
                  predicates[0]:
                    args[pattern]: '''/services/''+serviceId.toLowerCase()+''/**'''
                    name: Path
        """
      )
      .and()
      .hasFile("src/test/resources/config/bootstrap.yml")
      .containing(
        // language=yaml
        """
        spring:
          application:
            name: myApp
          cloud:
            gateway:
              discovery:
                locator:
                  enabled: false
        """
      )
      .and()
      .hasJavaSources(
        "com/seed4j/growth/wire/gateway/infrastructure/primary/GatewayResource.java",
        "com/seed4j/growth/wire/gateway/infrastructure/primary/vm/RouteVM.java"
      )
      .hasJavaTests("com/seed4j/growth/wire/gateway/infrastructure/primary/GatewayResourceIT.java");
  }
}
