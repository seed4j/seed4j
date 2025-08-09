package com.seed4j.generator.server.javatool.protobuf.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.gradleCommunityPlugin;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.mavenPlugin;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.pluginExecution;
import static com.seed4j.module.domain.SeedModule.stagedFilesFilter;
import static com.seed4j.module.domain.SeedModule.to;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;
import static com.seed4j.module.domain.SeedModule.versionSlug;

import com.seed4j.module.domain.PreCommitCommands;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.gradleplugin.GradleCommunityPlugin;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin.MavenPluginOptionalBuilder;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ProtobufModuleFactory {

  private static final SeedSource SOURCE = from("server/javatool/protobuf");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String PROTOBUF_PACKAGE = "shared/protobuf";
  private static final VersionSlug PROTOBUF_VERSION_SLUG = versionSlug("protobuf");
  private static final GroupId PROTOBUF_GROUP_ID = groupId("com.google.protobuf");

  public SeedModule buildProtobufModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    SeedDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(PROTOBUF_PACKAGE);
    SeedDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(PROTOBUF_PACKAGE);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(MAIN_SOURCE.template("ProtobufDatesReader.java"), mainDestination.append("infrastructure/primary/ProtobufDatesReader.java"))
        .add(MAIN_SOURCE.template("ProtobufDatesWriter.java"), mainDestination.append("infrastructure/secondary/ProtobufDatesWriter.java"))
        .add(MAIN_SOURCE.append(".gitkeep"), to("src/main/proto/.gitkeep"))
        .add(
          TEST_SOURCE.template("ProtobufDatesReaderTest.java"),
          testDestination.append("infrastructure/primary/ProtobufDatesReaderTest.java")
        )
        .add(
          TEST_SOURCE.template("ProtobufDatesWriterTest.java"),
          testDestination.append("infrastructure/secondary/ProtobufDatesWriterTest.java")
        )
        .and()
      .javaDependencies()
        .addDependency(PROTOBUF_GROUP_ID, artifactId("protobuf-java"), PROTOBUF_VERSION_SLUG)
        .addTestDependency(PROTOBUF_GROUP_ID, artifactId("protobuf-java-util"), PROTOBUF_VERSION_SLUG)
        .and()
      .mavenPlugins()
        .pluginManagement(protobufMavenPluginManagement())
        .plugin(protobufMavenPluginBuilder().build())
        .and()
      .gradlePlugins()
        .plugin(protobufGradlePlugin())
        .and()
      .build();
    // @formatter:on
  }

  private static GradleCommunityPlugin protobufGradlePlugin() {
    return gradleCommunityPlugin()
      .id("com.google.protobuf")
      .pluginSlug("protobuf")
      .versionSlug("protobuf-plugin")
      .configuration(
        """
        protobuf {
          protoc {
            artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.asProvider().get()}"
          }
        }
        """
      )
      .build();
  }

  private MavenPlugin protobufMavenPluginManagement() {
    return protobufMavenPluginBuilder()
      .versionSlug("protobuf-maven-plugin")
      .configuration(
        """
        <protocVersion>${protobuf.version}</protocVersion>
        <sourceDirectories>
          <sourceDirectory>src/main/proto</sourceDirectory>
        </sourceDirectories>
        <failOnMissingSources>false</failOnMissingSources>
        """
      )
      .addExecution(pluginExecution().goals("generate"))
      .build();
  }

  private MavenPluginOptionalBuilder protobufMavenPluginBuilder() {
    return mavenPlugin().groupId("io.github.ascopes").artifactId("protobuf-maven-plugin");
  }

  public SeedModule buildProtobufBackwardsCompatibilityCheckModule(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(MAIN_SOURCE.append("proto.lock"), to("src/main/proto/proto.lock"))
        .and()
      .mavenBuildExtensions()
        .addExtension(groupId("kr.motd.maven"), artifactId("os-maven-plugin"), versionSlug("os-maven-plugin"))
        .and()
      .mavenPlugins()
        .pluginManagement(protoBackwardsCompatibilityMavenPluginManagement())
        .plugin(protoBackwardsCompatibilityMavenPluginBuilder().build())
        .and()
      .preCommitActions(stagedFilesFilter("*.proto"), new PreCommitCommands("() => ['mvn proto-backwards-compatibility:backwards-compatibility-check', 'git add *proto.lock']"))
      .build();
    // @formatter:on
  }

  private MavenPlugin protoBackwardsCompatibilityMavenPluginManagement() {
    return protoBackwardsCompatibilityMavenPluginBuilder()
      .versionSlug("proto-backwards-compatibility")
      .addExecution(pluginExecution().goals("backwards-compatibility-check"))
      .build();
  }

  private MavenPluginOptionalBuilder protoBackwardsCompatibilityMavenPluginBuilder() {
    return mavenPlugin().groupId("com.salesforce.servicelibs").artifactId("proto-backwards-compatibility");
  }
}
