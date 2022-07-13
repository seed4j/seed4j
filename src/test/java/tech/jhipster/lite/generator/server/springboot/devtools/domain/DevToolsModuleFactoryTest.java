package tech.jhipster.lite.generator.server.springboot.devtools.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class DevToolsModuleFactoryTest {

  @InjectMocks
  private DevToolsModuleFactory factory;

  @Test
  void shouldCreateOAuth2Module() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles("documentation", "dev-tools.md")
      .hasFile("pom.xml")
      .containing("spring-boot-devtools")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.devtools.livereload.enabled=false")
      .containing("spring.devtools.restart.enabled=false")
      .and()
      .hasFile("src/main/resources/config/application-local.properties")
      .containing("spring.devtools.livereload.enabled=true")
      .containing("spring.devtools.restart.enabled=true");
  }
}
