package com.seed4j.generator.server.micronaut.core.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatTwoModulesWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.generator.buildtool.maven.domain.MavenModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautCoreModuleFactoryTest {

  private static final MicronautCoreModuleFactory factory = new MicronautCoreModuleFactory();
  private static final MavenModuleFactory mavenFactory = new MavenModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule mavenModule = mavenFactory.buildMavenModule(properties);
    Seed4JModule module = factory.buildModule(properties);

    assertThatTwoModulesWithFiles(mavenModule, module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <dependency>
                <groupId>io.micronaut</groupId>
                <artifactId>micronaut-bom</artifactId>
                <version>${micronaut.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.micronaut</groupId>
              <artifactId>micronaut-inject</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.micronaut</groupId>
              <artifactId>micronaut-runtime</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.micronaut</groupId>
              <artifactId>micronaut-jackson-databind</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>jakarta.annotation</groupId>
              <artifactId>jakarta.annotation-api</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>ch.qos.logback</groupId>
              <artifactId>logback-classic</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.micronaut.test</groupId>
              <artifactId>micronaut-test-junit5</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing("<artifactId>micronaut-http-server-netty</artifactId>")
      .containing("<artifactId>micronaut-http-client</artifactId>")
      .containing("<artifactId>micronaut-maven-plugin</artifactId>")
      .containing("<artifactId>micronaut-inject-java</artifactId>")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/MyappApp.java")
      .containing("class MyappApp")
      .containing("Micronaut.run(MyappApp.class, args);")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/HelloController.java")
      .containing("@Controller(\"/hello\")")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/HelloControllerTest.java")
      .containing("@MicronautTest")
      .containing("class HelloControllerTest")
      .and()
      .hasFile("src/main/resources/logback.xml")
      .containing("<logger name=\"com.seed4j.growth\" level=\"INFO\" />")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        micronaut:
          application:
            name: myapp
        """
      );
  }
}
