package tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.path;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class DummyCassandraPersistenceModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/dummy/cassandrapersistence");
  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "dummy/" + SECONDARY;

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String baseName = properties.projectBaseName().get();

    //@formatter:off
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
          .addTemplate("CassandraBeerCatalogRepositoryIntTest.java")
          .addTemplate("CassandraBeerRepositoryIntTest.java")
          .addTemplate("CassandraBeersResetter.java")
          .addTemplate("SpringDataRepositoryIntTest.java")
          .and()
        .batch(SOURCE.append("cql"), toSrcMainResourcesCql().append("changelog"))
          .addTemplate("00000000000000_create-keyspace.cql")
          .addTemplate("00000000000001_create-tables.cql")
          .and()
        .delete(path("src/main/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersRepository.java"))
        .delete(path("src/test/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersReseter.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.cassandra.keyspace-name"), propertyValue(baseName))
        .and()
      .build();
    //@formatter:on
  }

  private JHipsterDestination toSrcMainResourcesCql() {
    return toSrcMainResources().append("config").append("cql");
  }
}
