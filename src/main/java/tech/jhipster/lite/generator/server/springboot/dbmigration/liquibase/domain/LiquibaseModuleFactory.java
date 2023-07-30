package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class LiquibaseModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/liquibase");

  private static final String LIQUIBASE_SECONDARY = "technical/infrastructure/secondary/liquibase";

  private static final String LIQUIBASE = "liquibase";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.liquibase"), artifactId("liquibase-core"), versionSlug(LIQUIBASE))
        .addDependency(h2Dependency())
        .and()
      .springMainProperties()
        .set(propertyKey("spring.liquibase.change-log"), propertyValue("classpath:config/liquibase/master.xml"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.liquibase.change-log"), propertyValue("classpath:config/liquibase/master.xml"))
        .and()
      .files()
        .add(SOURCE.file("resources/master.xml"), to("src/main/resources/config/liquibase/master.xml"))
        .add(SOURCE.file("resources/0000000000_example.xml"), to("src/main/resources/config/liquibase/changelog/0000000000_example.xml"))
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
      .springMainLogger(LIQUIBASE, LogLevel.WARN)
      .springMainLogger("LiquibaseSchemaResolver", LogLevel.INFO)
      .springMainLogger("com.zaxxer.hikari", LogLevel.WARN)
      .springTestLogger(LIQUIBASE, LogLevel.WARN)
      .springTestLogger("LiquibaseSchemaResolver", LogLevel.INFO)
      .springTestLogger("com.zaxxer.hikari", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private JavaDependency h2Dependency() {
    return javaDependency().groupId("com.h2database").artifactId("h2").scope(JavaDependencyScope.TEST).build();
  }
}
