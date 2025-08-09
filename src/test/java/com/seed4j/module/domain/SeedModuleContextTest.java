package com.seed4j.module.domain;

import static com.seed4j.module.domain.JHipsterModulesFixture.emptyModuleBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModuleContextTest {

  @Test
  void shouldGetDefaultContext() {
    Map<String, Object> context = SeedModuleContext.builder(emptyModuleBuilder()).build().get();

    assertThat(context)
      .hasSize(8)
      .containsEntry("baseName", "jhipster")
      .containsEntry("projectName", "Seed4J Project")
      .containsEntry("packageName", "com.mycompany.myapp")
      .containsEntry("serverPort", 8080)
      .containsEntry("indentSize", 2)
      .containsEntry("javaVersion", "21")
      .containsEntry("springConfigurationFormat", "yaml")
      .containsEntry("projectBuildDirectory", "target");
  }

  @Test
  void shouldEnrichContextWithJavaBuildTool() {
    SeedModuleContext context = SeedModuleContext.builder(emptyModuleBuilder()).build();

    Map<String, Object> newContext = context.withJavaBuildTool(JavaBuildTool.GRADLE).get();

    assertThat(newContext)
      .hasSize(9)
      .containsKey("baseName")
      .containsKey("projectName")
      .containsKey("packageName")
      .containsKey("serverPort")
      .containsKey("indentSize")
      .containsKey("javaVersion")
      .containsKey("springConfigurationFormat")
      .containsEntry("javaBuildTool", "gradle")
      .containsEntry("projectBuildDirectory", "build");

    assertThat(context.get()).containsEntry("projectBuildDirectory", "target");
  }
}
