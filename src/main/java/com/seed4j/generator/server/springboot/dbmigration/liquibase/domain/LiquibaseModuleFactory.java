package com.seed4j.generator.server.springboot.dbmigration.liquibase.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.mavenPlugin;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.pluginExecution;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestResources;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.PROPERTIES;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.YAML;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPlugin.MavenPluginOptionalBuilder;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LiquibaseModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/dbmigration/liquibase");

  private static final String LIQUIBASE_SECONDARY = "wire/liquibase/infrastructure/secondary";

  private static final String LIQUIBASE = "liquibase";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.liquibase"), artifactId("liquibase-core"), versionSlug(LIQUIBASE))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.liquibase.change-log"), propertyValue("classpath:config/liquibase/master.xml"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.liquibase.contexts"), propertyValue("test"))
        .and()
      .files()
        .add(SOURCE.file("resources/master.xml"), to("src/main/resources/config/liquibase/master.xml"))
        .add(SOURCE.file("resources/0000000000_example.xml"), to("src/main/resources/config/liquibase/changelog/0000000000_example.xml"))
        .and()
      .springMainLogger(LIQUIBASE, LogLevel.WARN)
      .springMainLogger("LiquibaseSchemaResolver", LogLevel.INFO)
      .springMainLogger("com.zaxxer.hikari", LogLevel.WARN)
      .springTestLogger(LIQUIBASE, LogLevel.WARN)
      .springTestLogger("LiquibaseSchemaResolver", LogLevel.INFO)
      .springTestLogger("com.zaxxer.hikari", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  public Seed4JModule buildAsyncModule(Seed4JModuleProperties properties) {
    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("yamlSpringConfigurationFormat", properties.springConfigurationFormat() == YAML)
        .put("propertiesSpringConfigurationFormat", properties.springConfigurationFormat() == PROPERTIES)
        .and()
      .files()
        .batch(SOURCE.append("main"), toSrcMainJava().append(packagePath).append(LIQUIBASE_SECONDARY))
          .addTemplate("AsyncSpringLiquibase.java")
          .addTemplate("LiquibaseConfiguration.java")
          .addTemplate("SpringLiquibaseUtil.java")
          .and()
        .batch(SOURCE.append("test"), toSrcTestJava().append(packagePath).append(LIQUIBASE_SECONDARY))
          .addTemplate("AsyncSpringLiquibaseTest.java")
          .addTemplate("LiquibaseConfigurationIT.java")
          .addTemplate("SpringLiquibaseUtilTest.java")
          .and()
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildLinterModule(Seed4JModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .mavenPlugins()
        .plugin(liquibaseLinterMavenPlugin().build())
        .pluginManagement(liquibaseLinterMavenPluginManagement())
        .and()
      .files()
        .add(SOURCE.append("liquibase-linter.jsonc"), toSrcTestResources().append("liquibase-linter.jsonc"))
        .and()
      .build();
    // @formatter:on
  }

  private MavenPlugin liquibaseLinterMavenPluginManagement() {
    return liquibaseLinterMavenPlugin()
      .versionSlug("liquibase-linter-maven-plugin")
      .configuration(
        """
        <changeLogFile>src/main/resources/config/liquibase/master.xml</changeLogFile>
        <configurationFile>src/test/resources/liquibase-linter.jsonc</configurationFile>
        """
      )
      .addExecution(pluginExecution().goals("lint"))
      .build();
  }

  private static MavenPluginOptionalBuilder liquibaseLinterMavenPlugin() {
    return mavenPlugin().groupId("io.github.liquibase-linter").artifactId("liquibase-linter-maven-plugin");
  }
}
