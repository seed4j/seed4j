package com.seed4j.generator.server.micronaut.database.jpa.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javaproperties.PropertyValue;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautDataJpaModuleFactory {

  private static final GroupId MICRONAUT_DATA_GROUP = groupId("io.micronaut.data");
  private static final GroupId MICRONAUT_SQL_GROUP = groupId("io.micronaut.sql");
  private static final PropertyValue FALSE = propertyValue(false);
  private static final PropertyValue TRUE = propertyValue(true);

  public Seed4JModule buildPostgreSQL(Seed4JModuleProperties properties) {
    return commonJpaModuleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("org.postgresql"), artifactId("postgresql"))
      .and()
      .springMainProperties()
      .set(propertyKey("datasources.default.dialect"), propertyValue("POSTGRES"))
      .and()
      .build();
  }

  public Seed4JModule buildMySQL(Seed4JModuleProperties properties) {
    return commonJpaModuleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("com.mysql"), artifactId("mysql-connector-j"))
      .and()
      .springMainProperties()
      .set(propertyKey("datasources.default.dialect"), propertyValue("MYSQL"))
      .and()
      .build();
  }

  public Seed4JModule buildMariaDB(Seed4JModuleProperties properties) {
    return commonJpaModuleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("org.mariadb.jdbc"), artifactId("mariadb-java-client"))
      .and()
      .springMainProperties()
      .set(propertyKey("datasources.default.dialect"), propertyValue("MYSQL"))
      .and()
      .build();
  }

  public Seed4JModule buildMsSQL(Seed4JModuleProperties properties) {
    return commonJpaModuleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("com.microsoft.sqlserver"), artifactId("mssql-jdbc"))
      .and()
      .springMainProperties()
      .set(propertyKey("datasources.default.dialect"), propertyValue("SQL_SERVER"))
      .and()
      .build();
  }

  public static Seed4JModule.Seed4JModuleBuilder commonJpaModuleBuilder(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Seed4JSource jpaSource = from("server/micronaut/database/jpa");
    Seed4JDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append("wire/database/infrastructure/secondary/");

    return moduleBuilder(properties)
      .files()
      .add(jpaSource.template("DatabaseConfiguration.java"), mainDestination.append("DatabaseConfiguration.java"))
      .and()
      .javaDependencies()
      .addDependency(MICRONAUT_DATA_GROUP, artifactId("micronaut-data-hibernate-jpa"))
      .addDependency(MICRONAUT_SQL_GROUP, artifactId("micronaut-jdbc-hikari"))
      .and()
      .springMainProperties()
      .set(propertyKey("datasources.default.db-type"), propertyValue("postgresql"))
      .set(propertyKey("jpa.default.properties.hibernate.hbm2ddl.auto"), propertyValue("none"))
      .set(propertyKey("jpa.default.properties.hibernate.show_sql"), FALSE)
      .set(propertyKey("jpa.default.properties.hibernate.format_sql"), FALSE)
      .set(
        propertyKey("jpa.default.properties.hibernate.implicit_naming_strategy"),
        propertyValue("org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy")
      )
      .set(
        propertyKey("jpa.default.properties.hibernate.physical_naming_strategy"),
        propertyValue("org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy")
      )
      .set(propertyKey("jpa.default.properties.hibernate.jdbc.batch_size"), propertyValue(25))
      .set(propertyKey("jpa.default.properties.hibernate.jdbc.time_zone"), propertyValue("UTC"))
      .set(propertyKey("jpa.default.properties.hibernate.order_inserts"), TRUE)
      .set(propertyKey("jpa.default.properties.hibernate.order_updates"), TRUE)
      .set(propertyKey("jpa.default.properties.hibernate.query.fail_on_pagination_over_collection_fetch"), TRUE)
      .set(propertyKey("jpa.default.properties.hibernate.query.in_clause_parameter_padding"), TRUE)
      .and()
      .springMainLogger("org.hibernate.orm", LogLevel.WARN)
      .springMainLogger("org.hibernate.ejb.HibernatePersistence", LogLevel.OFF)
      .springTestLogger("org.hibernate.validator", LogLevel.WARN)
      .springTestLogger("org.hibernate.orm", LogLevel.WARN)
      .springTestLogger("org.hibernate.ejb.HibernatePersistence", LogLevel.OFF);
  }
}
