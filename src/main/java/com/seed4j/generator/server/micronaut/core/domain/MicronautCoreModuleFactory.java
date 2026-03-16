package com.seed4j.generator.server.micronaut.core.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautCoreModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/core");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId MICRONAUT_GROUP = groupId("io.micronaut");
  private static final VersionSlug MICRONAUT_VERSION_SLUG = versionSlug("micronaut");

  private static final Seed4JDestination MAIN_RESOURCE_DESTINATION = to("src/main/resources");
  private static final Seed4JDestination TEST_RESOURCES_DESTINATION = to("src/test/resources");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    String baseNameUncapitalized = properties.projectBaseName().uncapitalized();
    String packagePath = properties.packagePath();
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath);

    return moduleBuilder(properties)
      .context()
      .put("baseName", baseName)
      .and()
      .javaDependencies()
      .addDependencyManagement(micronautBom())
      .addDependency(MICRONAUT_GROUP, artifactId("micronaut-inject"))
      .addDependency(MICRONAUT_GROUP, artifactId("micronaut-runtime"))
      .addDependency(MICRONAUT_GROUP, artifactId("micronaut-jackson-databind"))
      .addDependency(MICRONAUT_GROUP, artifactId("micronaut-http-server-netty"))
      .addDependency(groupId("jakarta.annotation"), artifactId("jakarta.annotation-api"))
      .addDependency(groupId("ch.qos.logback"), artifactId("logback-classic"))
      .addDependency(micronautTestJUnit5())
      .addDependency(micronautHttpClient())
      .and()
      .mavenPlugins()
      .plugin(micronautMavenPlugin())
      .plugin(micronautCompilerPlugin())
      .and()
      .files()
      .add(MAIN_SOURCE.template("MicronautApp.java"), toSrcMainJava().append(packagePath).append(baseName + "App.java"))
      .add(MAIN_SOURCE.template("HelloController.java"), toSrcMainJava().append(packagePath).append("HelloController.java"))
      .add(TEST_SOURCE.template("HelloControllerTest.java"), testDestination.append("HelloControllerTest.java"))
      .add(MAIN_SOURCE.template("logback.xml"), MAIN_RESOURCE_DESTINATION.append("logback.xml"))
      .and()
      .springMainProperties()
      .set(propertyKey("micronaut.application.name"), propertyValue(baseNameUncapitalized))
      .and()
      .build();
  }

  private MavenPlugin micronautMavenPlugin() {
    return mavenPlugin().groupId("io.micronaut.maven").artifactId("micronaut-maven-plugin").versionSlug(MICRONAUT_VERSION_SLUG).build();
  }

  private MavenPlugin micronautCompilerPlugin() {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-compiler-plugin")
      .configuration(
        """
        <annotationProcessorPaths>
          <path>
            <groupId>io.micronaut</groupId>
            <artifactId>micronaut-inject-java</artifactId>
            <version>${micronaut.version}</version>
          </path>
        </annotationProcessorPaths>
        """
      )
      .build();
  }

  private JavaDependency micronautBom() {
    return JavaDependency.builder()
      .groupId(MICRONAUT_GROUP)
      .artifactId("micronaut-bom")
      .versionSlug(MICRONAUT_VERSION_SLUG)
      .type(JavaDependencyType.POM)
      .scope(JavaDependencyScope.IMPORT)
      .build();
  }

  private JavaDependency micronautTestJUnit5() {
    return JavaDependency.builder()
      .groupId(groupId("io.micronaut.test"))
      .artifactId("micronaut-test-junit5")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency micronautHttpClient() {
    return JavaDependency.builder().groupId(MICRONAUT_GROUP).artifactId("micronaut-http-client").scope(JavaDependencyScope.TEST).build();
  }
}
