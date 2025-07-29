package com.seed4j.generator.server.springboot.database.jpa.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JpaModuleFactory {

  private static final String ORG_HIBERNATE = "org.hibernate.orm";
  private static final PropertyValue FALSE = propertyValue(false);
  private static final PropertyValue TRUE = propertyValue(true);

  public JHipsterModule buildPostgreSQL(JHipsterModuleProperties properties) {
    return sqlCommonModuleBuilder(properties).build();
  }

  public JHipsterModule buildMariaDB(JHipsterModuleProperties properties) {
    return sqlCommonModuleBuilder(properties).build();
  }

  public JHipsterModule buildMySQL(JHipsterModuleProperties properties) {
    return sqlCommonModuleBuilder(properties).build();
  }

  public JHipsterModule buildMsSQL(JHipsterModuleProperties properties) {
    // @formatter:off
    return sqlCommonModuleBuilder(properties)
      .springMainProperties()
        .set(propertyKey("spring.jpa.hibernate.ddl-auto"), propertyValue("update"))
        .set(propertyKey("spring.jpa.properties.hibernate.criteria.literal_handling_mode"), propertyValue("BIND"))
        .set(propertyKey("spring.jpa.properties.hibernate.dialect"), propertyValue("org.hibernate.dialect.SQLServer2012Dialect"))
        .set(propertyKey("spring.jpa.properties.hibernate.format_sql"), propertyValue(true))
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.fetch_size"), propertyValue(150))
        .and()
      .build();
    // @formatter:on
  }

  public static JHipsterModule.JHipsterModuleBuilder sqlCommonModuleBuilder(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterSource jpaSource = from("server/springboot/database/jpa");
    JHipsterDestination mainDestination = toSrcMainJava()
      .append(properties.packagePath())
      .append("wire/database/infrastructure/secondary/");

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(jpaSource.template("DatabaseConfiguration.java"), mainDestination.append("DatabaseConfiguration.java"))
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-jpa"))
        .addDependency(groupId(ORG_HIBERNATE), artifactId("hibernate-core"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.data.jpa.repositories.bootstrap-mode"), propertyValue("deferred"))
        .set(propertyKey("spring.jpa.hibernate.ddl-auto"), propertyValue("none"))
        .set(
          propertyKey("spring.jpa.hibernate.naming.implicit-strategy"),
          propertyValue("org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy")
        )
        .set(
          propertyKey("spring.jpa.hibernate.naming.physical-strategy"),
          propertyValue("org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy")
        )
        .set(propertyKey("spring.jpa.open-in-view"), FALSE)
        .set(propertyKey("spring.jpa.properties.hibernate.connection.provider_disables_autocommit"), TRUE)
        .set(propertyKey("spring.jpa.properties.hibernate.generate_statistics"), FALSE)
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.batch_size"), propertyValue(25))
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.time_zone"), propertyValue("UTC"))
        .set(propertyKey("spring.jpa.properties.hibernate.order_inserts"), TRUE)
        .set(propertyKey("spring.jpa.properties.hibernate.order_updates"), TRUE)
        .set(propertyKey("spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch"), TRUE)
        .set(propertyKey("spring.jpa.properties.hibernate.query.in_clause_parameter_padding"), TRUE)
        .and()
      .springMainLogger(ORG_HIBERNATE, LogLevel.WARN)
      .springMainLogger("org.hibernate.ejb.HibernatePersistence", LogLevel.OFF)
      .springTestLogger("org.hibernate.validator", LogLevel.WARN)
      .springTestLogger(ORG_HIBERNATE, LogLevel.WARN)
      .springTestLogger("org.hibernate.ejb.HibernatePersistence", LogLevel.OFF);
    // @formatter:on
  }
}
