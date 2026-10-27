package com.seed4j.generator.server.micronaut.core;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautProperties.MICRONAUT_MAIN;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaAnnotationProcessor;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.mavenplugin.MavenBuildPhase;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.properties.SpringConfigurationFormat;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;

class MicronautCoreModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/core");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId MICRONAUT_GROUP = groupId("io.micronaut");
  private static final VersionSlug MICRONAUT_VERSION_SLUG = versionSlug("micronaut");

  private static final Seed4JDestination MAIN_RESOURCE_DESTINATION = to("src/main/resources");
  private static final Seed4JDestination TEST_RESOURCES_DESTINATION = to("src/test/resources");

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    String baseNameUncapitalized = properties.projectBaseName().uncapitalized();
    String packagePath = properties.packagePath();
    String fullyQualifiedMainClass = properties.basePackage().get() + "." + baseName + "App";

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
      .javaCompiler()
      .addAnnotationProcessor(micronautInjectJava())
      .and()
      .mavenPlugins()
      .plugin(micronautMavenPlugin())
      .plugin(mavenShadePlugin(fullyQualifiedMainClass))
      .and()
      .files()
      .add(
        MAIN_SOURCE.template("MicronautApp.java"),
        toSrcMainJava()
          .append(packagePath)
          .append(baseName + "App.java")
      )
      .add(MAIN_SOURCE.template("logback.xml"), MAIN_RESOURCE_DESTINATION.append("logback.xml"))
      .add(TEST_SOURCE.template("logback-test.xml"), TEST_RESOURCES_DESTINATION.append("logback-test.xml"))
      .and()
      .applicationProperties(MICRONAUT_MAIN)
      .set(propertyKey("micronaut.application.name"), propertyValue(baseNameUncapitalized))
      .set(propertyKey("micronaut.server.port"), propertyValue(properties.serverPort().get()))
      .and()
      .with(yamlConfigurationSupport(properties))
      .build();
  }

  private static Consumer<Seed4JModuleBuilder> yamlConfigurationSupport(Seed4JModuleProperties properties) {
    return builder -> {
      if (properties.springConfigurationFormat() == SpringConfigurationFormat.YAML) {
        // Micronaut doesn't bring a YAML parser anymore: it is needed to read application.yml
        builder.javaDependencies().addDependency(snakeYaml());
      }
    };
  }

  private static JavaDependency snakeYaml() {
    return JavaDependency.builder().groupId("org.yaml").artifactId("snakeyaml").scope(JavaDependencyScope.RUNTIME).build();
  }

  private MavenPlugin micronautMavenPlugin() {
    return mavenPlugin().groupId("io.micronaut.maven").artifactId("micronaut-maven-plugin").versionSlug("micronaut-maven-plugin").build();
  }

  private MavenPlugin mavenShadePlugin(String fullyQualifiedMainClass) {
    return mavenPlugin()
      .groupId("org.apache.maven.plugins")
      .artifactId("maven-shade-plugin")
      .versionSlug("maven-shade-plugin")
      .addExecution(pluginExecution().goals("shade").phase(MavenBuildPhase.PACKAGE))
      .configuration(
        """
        <createDependencyReducedPom>false</createDependencyReducedPom>
        <shadedArtifactAttached>true</shadedArtifactAttached>
        <shadedClassifierName>exec</shadedClassifierName>
        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <mainClass>%s</mainClass>
          </transformer>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" />
        </transformers>
        <filters>
          <filter>
            <artifact>*:*</artifact>
            <excludes>
              <exclude>META-INF/*.SF</exclude>
              <exclude>META-INF/*.DSA</exclude>
              <exclude>META-INF/*.RSA</exclude>
              <exclude>module-info.class</exclude>
            </excludes>
          </filter>
        </filters>
        """.formatted(fullyQualifiedMainClass)
      )
      .build();
  }

  private JavaAnnotationProcessor micronautInjectJava() {
    return javaAnnotationProcessor().groupId(MICRONAUT_GROUP).artifactId("micronaut-inject-java").build();
  }

  private JavaDependency micronautBom() {
    return JavaDependency.builder()
      .groupId("io.micronaut.platform")
      .artifactId("micronaut-platform")
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
