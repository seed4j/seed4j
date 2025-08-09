package com.seed4j.generator.server.springboot.database.jooq.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.mavenPlugin;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.pluginExecution;
import static com.seed4j.module.domain.mavenplugin.MavenBuildPhase.GENERATE_RESOURCES;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JooqModuleFactory {

  public SeedModule buildPostgreSQL(SeedModuleProperties properties) {
    // @formatter:off
    return commonModuleBuilder(properties)
      .mavenPlugins()
        .plugin(jooqMavenCodegenPlugin(postgreSQLPluginConfiguration(properties)))
        .and()
      .build();
    // @formatter:on
  }

  private static String postgreSQLPluginConfiguration(SeedModuleProperties properties) {
    return JooqModuleCodegenConfiguration.builder()
      .databaseUrl("jdbc:postgresql://localhost:5432/" + properties.projectBaseName().name())
      .user(properties.projectBaseName().name())
      .inputSchema("public")
      .jooqMetaDatabase("org.jooq.meta.postgres.PostgresDatabase")
      .build()
      .getConfiguration();
  }

  public SeedModule buildMariaDB(SeedModuleProperties properties) {
    // @formatter:off
    return commonModuleBuilder(properties)
      .mavenPlugins()
        .plugin(jooqMavenCodegenPlugin(mariadbPluginConfiguration(properties)))
        .and()
      .build();
    // @formatter:on
  }

  private static String mariadbPluginConfiguration(SeedModuleProperties properties) {
    return JooqModuleCodegenConfiguration.builder()
      .databaseUrl("jdbc:mariadb://localhost:3306/" + properties.projectBaseName().name())
      .user("root")
      .inputSchema(properties.projectBaseName().name())
      .jooqMetaDatabase("org.jooq.meta.mariadb.MariaDBDatabase")
      .build()
      .getConfiguration();
  }

  public SeedModule buildMySQL(SeedModuleProperties properties) {
    // @formatter:off
    return commonModuleBuilder(properties)
      .mavenPlugins()
        .plugin(jooqMavenCodegenPlugin(mysqlPluginConfiguration(properties)))
        .and()
      .build();
    // @formatter:on
  }

  private static String mysqlPluginConfiguration(SeedModuleProperties properties) {
    return JooqModuleCodegenConfiguration.builder()
      .databaseUrl("jdbc:mysql://localhost:3306/" + properties.projectBaseName().name())
      .user("root")
      .inputSchema(properties.projectBaseName().name())
      .jooqMetaDatabase("org.jooq.meta.mysql.MySQLDatabase")
      .build()
      .getConfiguration();
  }

  public SeedModule buildMsSQL(SeedModuleProperties properties) {
    // @formatter:off
    return commonModuleBuilder(properties)
      .mavenPlugins()
        .plugin(jooqMavenCodegenPlugin(mssqlPluginConfiguration(properties)))
        .and()
      .build();
    // @formatter:on
  }

  private static String mssqlPluginConfiguration(SeedModuleProperties properties) {
    return JooqModuleCodegenConfiguration.builder()
      .databaseUrl("jdbc:sqlserver://localhost:1433;database=" + properties.projectBaseName().name() + ";trustServerCertificate=true")
      .user("SA")
      .inputSchema("model")
      .jooqMetaDatabase("org.jooq.meta.sqlserver.SQLServerDatabase")
      .password("yourStrong(!)Password")
      .build()
      .getConfiguration();
  }

  public static SeedModule.SeedModuleBuilder commonModuleBuilder(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-jooq"))
      .and();
  }

  private static MavenPlugin jooqMavenCodegenPlugin(String pluginConfiguration) {
    return mavenPlugin()
      .groupId("org.jooq")
      .artifactId("jooq-codegen-maven")
      .versionSlug("jooq")
      .addExecution(pluginExecution().goals("generate").id("jooq-codegen").phase(GENERATE_RESOURCES))
      .configuration(pluginConfiguration)
      .build();
  }
}
