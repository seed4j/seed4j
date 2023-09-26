package tech.jhipster.lite.generator.server.springboot.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootCoreModuleFactoryTest {

  private static final SpringBootCoreModuleFactory factory = new SpringBootCoreModuleFactory();

  @Test
  void shouldBuildModuleOnProjectWithoutDefaultGoal() {
    JHipsterModuleProperties properties = properties();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <scope>import</scope>
                <type>pom</type>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-configuration-processor</artifactId>
              <optional>true</optional>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.apache.commons</groupId>
              <artifactId>commons-lang3</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
                <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                  <version>${spring-boot.version}</version>
                  <executions>
                    <execution>
                      <goals>
                        <goal>repackage</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <mainClass>com.jhipster.test.MyappApp</mainClass>
                  </configuration>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
              </plugin>
        """
      )
      .notContaining(
        """
            <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter-engine</artifactId>
              <version>${junit-jupiter.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .notContaining(
        """
            <dependency>
              <groupId>org.junit.jupiter</groupId>
              <artifactId>junit-jupiter-params</artifactId>
              <version>${junit-jupiter.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .notContaining(
        """
            <dependency>
              <groupId>org.assertj</groupId>
              <artifactId>assertj-core</artifactId>
              <version>${assertj.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .notContaining(
        """
            <dependency>
              <groupId>org.mockito</groupId>
              <artifactId>mockito-junit-jupiter</artifactId>
              <version>${mockito.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing("    <defaultGoal>spring-boot:run</defaultGoal>")
      .and()
      .hasFile("src/main/java/com/jhipster/test/MyappApp.java")
      .containing("class MyappApp")
      .and()
      .hasFiles("src/main/java/com/jhipster/test/ApplicationStartupTraces.java")
      .hasPrefixedFiles("src/test/java/com/jhipster/test", "ApplicationStartupTracesTest.java", "IntegrationTest.java")
      .hasFiles(
        "src/main/resources/config/application.properties",
        "src/main/resources/config/application-local.properties",
        "src/test/resources/config/application-test.properties"
      )
      .hasFiles("src/test/resources/logback.xml", "src/main/resources/logback-spring.xml");
  }

  @Test
  void shouldBuildModuleOnProjectWithDefaultGoal() {
    JHipsterModuleProperties properties = properties();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomWithDefaultGoal())
      .hasFile("pom.xml")
      .containing("<defaultGoal>dummy</defaultGoal>")
      .notContaining("<defaultGoal>spring-boot:run</defaultGoal>");
  }

  private ModuleFile pomWithDefaultGoal() {
    return file("src/test/resources/projects/maven-with-default-goal/pom.xml", "pom.xml");
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
  }
}
