package com.seed4j.generator.server.springboot.langchain4j.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class LangChain4jModuleFactoryTest {

  private static final LangChain4jModuleFactory factory = new LangChain4jModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).projectBaseName("myApp").build();

    SeedModule module = factory.buildModule(properties);

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
