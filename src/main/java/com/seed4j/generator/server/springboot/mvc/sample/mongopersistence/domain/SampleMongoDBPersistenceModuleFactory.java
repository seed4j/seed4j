package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleMongoDBPersistenceModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/mongodbpersistence");

  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "sample/" + SECONDARY;

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("main").append(SECONDARY), toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerDocument.java")
          .addTemplate("BeersCollectionChangeUnit.java")
          .addTemplate("MongoDBBeersRepository.java")
          .addTemplate("SpringDataBeersRepository.java")
          .and()
        .batch(SOURCE.append("test").append(SECONDARY), toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerDocumentTest.java")
          .addTemplate("MongoDBBeersRepositoryIT.java")
          .addTemplate("MongoDBBeersResetter.java")
          .and()
        .delete(path("src/main/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersRepository.java"))
        .delete(path("src/test/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersResetter.java"))
        .and()
      .build();
    // @formatter:on
  }
}
