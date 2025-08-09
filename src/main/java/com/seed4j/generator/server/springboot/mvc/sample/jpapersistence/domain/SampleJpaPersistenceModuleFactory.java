package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.domain.replacement.RegexNeedleBeforeReplacer;
import com.seed4j.module.domain.replacement.ReplacementCondition;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;
import java.util.regex.Pattern;

public class SampleJpaPersistenceModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/jpapersistence");

  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "sample/" + SECONDARY;

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText("import org.springframework.stereotype.Service;");

  private static final TextNeedleBeforeReplacer CATALOG_METHOD_NEEDLE = new TextNeedleBeforeReplacer(
    ReplacementCondition.always(),
    "Beers catalog()"
  );

  private static final RegexNeedleBeforeReplacer REMOVE_METHOD_NEEDLE = new RegexNeedleBeforeReplacer(
    ReplacementCondition.always(),
    Pattern.compile("^ +@PreAuthorize\\(\"can\\('remove', #beer\\)\"\\)\\n+ +public void remove\\(BeerId beer\\)", Pattern.MULTILINE)
  );

  private static final RegexNeedleBeforeReplacer CREATE_METHOD_NEEDLE = new RegexNeedleBeforeReplacer(
    ReplacementCondition.always(),
    Pattern.compile(
      "^ +@PreAuthorize\\(\"can\\('create', #beerToCreate\\)\"\\)\\n+ +public Beer create\\(BeerToCreate beerToCreate\\)",
      Pattern.MULTILINE
    )
  );

  public JHipsterModule buildModule(SeedModuleProperties properties) {
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
            .add(REMOVE_METHOD_NEEDLE, spaces + "@Transactional")
            .add(CREATE_METHOD_NEEDLE, spaces + "@Transactional")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
