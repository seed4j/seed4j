package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MongoDbModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private MongoDbModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("mongo")).thenReturn(new DockerImage("mongo", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), lockbackFile(), testLockbackFile(), integrationTestAnnotation(), readmeFile())
      .createFiles("documentation/mongo-db.md")
      .createFile("README.md")
      .containing("""
            ```bash
            docker-compose -f src/main/docker/mongodb.yml up -d
            ```
            """)
      .and()
      .createFile("pom.xml")
      .containing(
        """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-mongodb</artifactId>
                </dependency>
            """
      )
      .containing(
        """
                <dependency>
                  <groupId>org.mongodb</groupId>
                  <artifactId>mongodb-driver-sync</artifactId>
                </dependency>
            """
      )
      .containing(
        """
                <dependency>
                  <groupId>org.testcontainers</groupId>
                  <artifactId>mongodb</artifactId>
                  <version>${testcontainers.version}</version>
                  <scope>test</scope>
                </dependency>
            """
      )
      .and()
      .createFile("src/main/docker/mongodb.yml")
      .containing("mongo:1.1.1")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/secondary/mongodb",
        "MongodbDatabaseConfiguration.java",
        "JSR310DateConverters.java"
      )
      .createFiles("src/test/java/com/jhipster/test/technical/infrastructure/secondary/mongodb/JSR310DateConvertersTest.java")
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test",
        "MongodbTestContainerExtension.java",
        "TestContainersSpringContextCustomizerFactory.java"
      )
      .createFile("src/test/resources/META-INF/spring.factories")
      .containing("org.springframework.test.context.ContextCustomizerFactory=com.jhipster.test")
      .and()
      .createFile("src/main/resources/config/application.properties")
      .containing("spring.data.mongodb.database=com.jhipster.test")
      .containing("spring.data.mongodb.uri=mongodb://localhost:27017")
      .and()
      .createFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"org.mongodb.driver\" level=\"WARN\" />")
      .and()
      .createFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.reflections\" level=\"WARN\" />")
      .containing("<logger name=\"org.mongodb.driver\" level=\"WARN\" />")
      .containing("<logger name=\"com.github.dockerjava\" level=\"WARN\" />")
      .containing("<logger name=\"org.testcontainers\" level=\"WARN\" />")
      .and()
      .createFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("import org.junit.jupiter.api.extension.ExtendWith;")
      .containing("@ExtendWith(MongodbTestContainerExtension.class)");
  }

  private ModuleFile integrationTestAnnotation() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/jhipster/test/IntegrationTest.java");
  }
}
