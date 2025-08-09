package com.seed4j.generator.server.springboot.seed4jextension.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JExtensionModuleFactoryTest {

  private static final Seed4JExtensionModuleFactory factory = new Seed4JExtensionModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .put("serverPort", 9000)
      .build();

    SeedModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, pomFile(), mainAppFile())
      .hasExecutableFiles("tests-ci/generate.sh", "tests-ci/start.sh", "tests-ci/stop.sh")
      .hasFile("tests-ci/generate.sh")
        .containing("http://localhost:9000")
        .and()
      .hasFile("tests-ci/start.sh")
        .containing("9000")
        .and()
      .hasFile("tests-ci/modulePayload.json")
        .containing("""
          "packageName": "com.seed4j.growth.APP_NAME",
        """)
        .and()
      .hasFile("pom.xml")
        .containing("<artifactId>cucumber-junit-platform-engine</artifactId>")
        .containing("<artifactId>cucumber-java</artifactId>")
        .containing("<artifactId>cucumber-spring</artifactId>")
        .containing("<artifactId>junit-platform-suite</artifactId>")
        .containing(
          """
              <dependency>
                <groupId>com.seed4j</groupId>
                <artifactId>seed4j</artifactId>
                <version>${seed4j.version}</version>
              </dependency>
          """
        )
      .containing(
          """
              <dependency>
                <groupId>com.approvaltests</groupId>
                <artifactId>approvaltests</artifactId>
                <version>${approvaltests.version}</version>
                <scope>test</scope>
              </dependency>
          """
        )
        .containing(
          """
              <dependency>
                <groupId>com.seed4j</groupId>
                <artifactId>seed4j</artifactId>
                <version>${seed4j.version}</version>
                <type>test-jar</type>
                <classifier>tests</classifier>
                <scope>test</scope>
              </dependency>
          """
        )
        .and()
      .hasFile("src/main/resources/config/application.yml")
        .containing(
          """
          seed4j:
            hidden-resources:
              # Disable the modules and its dependencies by slugs
              slugs: seed4j-extension
          server:
            port: 9000
          spring:
            jackson:
              default-property-inclusion: non_null
          """
        )
        .and()
      .hasFile("src/test/resources/config/application-test.yml")
        .containing(
          """
          server:
            port: 0
          spring:
            main:
              allow-bean-definition-overriding: true
          """)
        .and()
      .hasFile("src/main/java/com/seed4j/growth/MyAppApp.java")
        .containing("import com.seed4j.Seed4jApp;")
        .containing("@SpringBootApplication(scanBasePackageClasses = { Seed4jApp.class, MyAppApp.class })")
        .and()
      .hasPrefixedFiles("documentation", "module-creation.md", "cucumber.md")
      .doNotHaveFiles(
        "src/main/java/com/seed4j/growth/security/infrastructure/primary/CorsFilterConfiguration.java",
        "src/main/java/com/seed4j/growth/security/infrastructure/primary/CorsProperties.java",
        "src/test/java/com/seed4j/growth/security/infrastructure/primary/CorsFilterConfigurationIT.java"
      )
      .hasFile("src/test/java/com/seed4j/growth/cucumber/CucumberTest.java")
        .containing("key = GLUE_PROPERTY_NAME, value = \"com.seed4j.growth, com.seed4j.module.infrastructure.primary, com.seed4j.project.infrastructure.primary\"")
        .and()
      .hasFile("src/test/java/com/seed4j/growth/cucumber/CucumberConfiguration.java")
        .containing("import com.seed4j.growth.MyAppApp;")
        .and()
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/shared/slug", "package-info.java", "domain/MyAppFeatureSlug.java", "domain/MyAppModuleSlug.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/dependencies",
        "package-info.java",
        "domain/MyAppNodePackagesVersionSource.java",
        "infrastructure/secondary/MyAppNodePackagesVersionsReader.java",
        "infrastructure/secondary/MyAppMavenDependenciesReader.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/dependencies/infrastructure/secondary",
        "MyAppNodePackagesVersionsReaderTest.java",
        "MyAppMavenDependenciesReaderTest.java"
      )
      .hasFile("src/main/java/com/seed4j/growth/shared/dependencies/domain/MyAppNodePackagesVersionSource.java")
        .containing(
        """
        MY_APP("my-app");
        """
        )
        .and()
      .hasPrefixedFiles(
        "src/main/resources/generator/my-app-dependencies",
        "my-app/package.json",
        "pom.xml"
      )
      .hasFiles("src/test/java/com/seed4j/growth/cucumber/rest/CucumberRestTemplate.java")
      .hasFiles("src/test/features/.gitkeep");
    // @formatter:on
  }

  private ModuleFile mainAppFile() {
    return file("src/test/resources/projects/files/MainApp.java", "src/main/java/com/seed4j/growth/MyAppApp.java");
  }
}
