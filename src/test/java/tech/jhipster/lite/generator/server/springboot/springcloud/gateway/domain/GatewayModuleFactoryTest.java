package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class GatewayModuleFactoryTest {

  private static final GatewayModuleFactory factory = new GatewayModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), propertiesFile())
      .hasFile("pom.xml")
      .containing("<spring-cloud.version>")
      .containing(
        """
                  <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring-cloud.version}</version>
                    <scope>import</scope>
                    <type>pom</type>
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
      .hasFile("src/main/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("spring.cloud.gateway.discovery.locator.enabled=true")
      .containing("spring.cloud.gateway.discovery.locator.lower-case-service-id=true")
      .containing("spring.cloud.gateway.discovery.locator.predicates[0].name=Path")
      .containing("spring.cloud.gateway.discovery.locator.predicates[0].args[pattern]='/services/'+serviceId.toLowerCase()+'/**'")
      .containing("spring.cloud.gateway.discovery.locator.filters[0].name=RewritePath")
      .containing(
        "spring.cloud.gateway.discovery.locator.filters[0].args[regexp]='/services/' + serviceId.toLowerCase() + '/(?<remaining>.*)'"
      )
      .containing("spring.cloud.gateway.discovery.locator.filters[0].args[replacement]='/${remaining}'")
      .and()
      .hasFile("src/test/resources/config/bootstrap.properties")
      .containing("spring.application.name=myApp")
      .containing("spring.cloud.gateway.discovery.locator.enabled=false")
      .and()
      .hasJavaSources(
        "com/jhipster/test/technical/infrastructure/primary/rest/GatewayResource.java",
        "com/jhipster/test/technical/infrastructure/primary/rest/vm/RouteVM.java"
      )
      .hasJavaTests("com/jhipster/test/technical/infrastructure/primary/rest/GatewayResourceIT.java");
  }
}
