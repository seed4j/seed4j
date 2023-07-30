package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringdocModuleFactoryTest {

  private static final SpringdocModuleFactory springdocModuleFactory = new SpringdocModuleFactory();

  @Test
  void shouldBuildModuleForMvc() {
    JHipsterModule module = springdocModuleFactory.buildModuleForMvc(properties());

    assertThatSpringDocModule(module)
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>")
      .containing("<artifactId>springdoc-openapi-starter-webmvc-api</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>")
      .and();
  }

  @Test
  void shouldBuildModuleForWebflux() {
    JHipsterModule module = springdocModuleFactory.buildModuleForWebflux(properties());

    assertThatSpringDocModule(module)
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .hasFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-starter-webflux-ui</artifactId>")
      .containing("<artifactId>springdoc-openapi-starter-webflux-api</artifactId>")
      .and();
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
  }

  private static JHipsterModuleAsserter assertThatSpringDocModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile(), readmeFile(), logbackFile(), testLogbackFile())
      .hasFile("src/main/resources/config/application.properties")
      .containing("springdoc.swagger-ui.operationsSorter=alpha")
      .containing("springdoc.swagger-ui.tagsSorter=alpha")
      .containing("springdoc.swagger-ui.tryItOutEnabled=true")
      .containing("springdoc.enable-native-support=true")
      .and()
      .hasFile("README.md")
      .containing("- [Local API doc](http://localhost:8080/swagger-ui.html)")
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"io.swagger.v3.core.converter.ModelConverterContextImpl\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"io.swagger.v3.core.converter.ModelConverterContextImpl\" level=\"WARN\" />")
      .and();
  }
}
