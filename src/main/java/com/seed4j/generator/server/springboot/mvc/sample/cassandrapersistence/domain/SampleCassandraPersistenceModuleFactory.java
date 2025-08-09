package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.path;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcMainResources;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleCassandraPersistenceModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/cassandrapersistence");
  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "sample/" + SECONDARY;

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String packageName = properties.projectBaseName().get();

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("main").append(SECONDARY), toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerCatalogTable.java")
          .addTemplate("BeerTable.java")
          .addTemplate("CassandraBeerCatalogRepository.java")
          .addTemplate("CassandraBeerRepository.java")
          .addTemplate("SpringDataBeersRepository.java")
          .and()
        .batch(SOURCE.append("test").append(SECONDARY), toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerCatalogTableTest.java")
          .addTemplate("BeerTableTest.java")
          .addTemplate("CassandraBeerCatalogRepositoryIT.java")
          .addTemplate("CassandraBeerRepositoryIT.java")
          .addTemplate("CassandraBeersResetter.java")
          .addTemplate("SpringDataRepositoryIT.java")
          .and()
        .batch(SOURCE.append("cql"), toSrcMainResourcesCql().append("changelog"))
          .addTemplate("00000000000000_create-keyspace.cql")
          .addTemplate("00000000000001_create-tables.cql")
          .and()
        .delete(path("src/main/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersRepository.java"))
        .delete(path("src/test/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersResetter.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.cassandra.keyspace-name"), propertyValue(packageName))
        .and()
      .build();
    // @formatter:on
  }

  private SeedDestination toSrcMainResourcesCql() {
    return toSrcMainResources().append("config").append("cql");
  }
}
