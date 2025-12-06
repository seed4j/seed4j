package com.seed4j.generator.server.springboot.springcloud.gateway.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GatewayModuleFactoryTest {

  private static final GatewayModuleFactory factory = new GatewayModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

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
              <version>${spring-cloud-starter-gateway.version}</version>
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
              server:
                webflux:
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
              server:
                webflux:
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
