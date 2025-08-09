package com.seed4j.generator.server.springboot.apidocumentation.openapicontract.domain;

import static com.seed4j.module.domain.JHipsterModule.mavenPlugin;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.pluginExecution;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin.MavenPluginOptionalBuilder;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class OpenApiContractModuleFactory {

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .pluginManagement(openApiPluginManagement(properties))
        .plugin(openApiPlugin().build())
        .and()
      .build();
    // @formatter:on
  }

  private MavenPluginOptionalBuilder openApiPlugin() {
    return mavenPlugin().groupId("io.github.kbuntrock").artifactId("openapi-maven-plugin");
  }

  private MavenPlugin openApiPluginManagement(SeedModuleProperties properties) {
    return openApiPlugin()
      .versionSlug("openapi-maven-plugin")
      .addExecution(pluginExecution().goals("documentation").id("generate-openapi-contract"))
      .configuration(
        """
        <apiConfiguration>
          <library>SPRING_MVC</library>
        </apiConfiguration>
        <apis>
          <api>
            <filename>openapi-contract.yml</filename>
            <locations>
              <location>%s</location>
            </locations>
            <tag>
              <substitutions>
                <sub>
                  <regex>Resource$</regex>
                  <substitute />
                </sub>
              </substitutions>
            </tag>
          </api>
        </apis>\
        """.formatted(properties.basePackage())
      )
      .build();
  }

  public JHipsterModule buildBackwardsCompatibilityCheckModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .pluginManagement(openApiBackwardsCompatPluginManagement())
        .plugin(openApiBackwardsCompatPlugin().build())
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin openApiBackwardsCompatPluginManagement() {
    return openApiBackwardsCompatPlugin()
      .versionSlug("openapi-backwards-compat-maven-plugin")
      .configuration("<openApiSourceDir>${project.build.directory}</openApiSourceDir>")
      .addExecution(pluginExecution().goals("backwards-compatibility-check"))
      .build();
  }

  private MavenPluginOptionalBuilder openApiBackwardsCompatPlugin() {
    return mavenPlugin().groupId("io.kemtoa.openapi").artifactId("openapi-backwards-compat-maven-plugin");
  }
}
