package tech.jhipster.lite.generator.server.springboot.langchain4j.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

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
class LangChain4jModuleFactoryTest {

  @InjectMocks
  private LangChain4jModuleFactory factory;

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/langchain4j.md")
      .hasFile("pom.xml")
      .containing("<langchain4j.version>")
      .containing(
        """
            <dependency>
              <groupId>dev.langchain4j</groupId>
              <artifactId>langchain4j-spring-boot-starter</artifactId>
              <version>${langchain4j.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>dev.langchain4j</groupId>
              <artifactId>langchain4j-open-ai-spring-boot-starter</artifactId>
              <version>${langchain4j.version}</version>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        langchain4j:
          open-ai:
            chat-model:
              # You can temporarily use 'demo' key, which is provided for free for demonstration purposes
              api-key: demo
              log-requests: 'true'
              log-responses: 'true'
              model-name: gpt-4o-mini
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        langchain4j:
          open-ai:
            chat-model:
              api-key: jhipster
        """
      );
  }
}
