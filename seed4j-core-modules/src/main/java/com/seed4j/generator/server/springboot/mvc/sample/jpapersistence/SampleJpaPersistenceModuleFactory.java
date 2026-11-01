package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notContaining;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;

class SampleJpaPersistenceModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/sample/jpapersistence");

  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "sample/" + SECONDARY;

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText("import org.springframework.stereotype.Service;");

  private static final TextNeedleBeforeReplacer CATALOG_METHOD_NEEDLE = lineBeforeText("public Beers catalog()");

  private static final TextNeedleBeforeReplacer PRE_AUTHORIZE_NEEDLE = new TextNeedleBeforeReplacer(
    notContaining("@Transactional\n"),
    "@PreAuthorize"
  );

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String spaces = properties.indentation().spaces();

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("main").append(SECONDARY), toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerEntity.java")
          .addTemplate("JpaBeersRepository.java")
          .addTemplate("SpringDataBeersRepository.java")
          .and()
        .batch(SOURCE.append("test").append(SECONDARY), toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerEntityTest.java")
          .addTemplate("JpaBeersRepositoryIT.java")
          .and()
        .delete(path("src/main/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersRepository.java"))
        .delete(path("src/test/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersResetter.java"))
        .and()
        .mandatoryReplacements()
          .in(path("src/main/java").append(packagePath).append("sample/application/BeersApplicationService.java"))
            .add(IMPORT_NEEDLE, "import org.springframework.transaction.annotation.Transactional;")
            .add(CATALOG_METHOD_NEEDLE, spaces + "@Transactional(readOnly = true)")
            .add(PRE_AUTHORIZE_NEEDLE, spaces + "@Transactional")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
