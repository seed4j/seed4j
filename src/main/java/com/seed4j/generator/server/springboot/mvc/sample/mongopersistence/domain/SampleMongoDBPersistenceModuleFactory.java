package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleMongoDBPersistenceModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/sample/mongodbpersistence");

  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "sample/" + SECONDARY;

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
