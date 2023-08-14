package tech.jhipster.lite.generator.server.springboot.mvc.dummy.jpapersistence.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.regex.Pattern;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.RegexNeedleBeforeReplacer;
import tech.jhipster.lite.module.domain.replacement.ReplacementCondition;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;
import tech.jhipster.lite.shared.error.domain.Assert;

public class DummyJpaPersistenceModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/dummy/jpapersistence");

  private static final String SECONDARY = "infrastructure/secondary";
  private static final String SECONDARY_DESTINATION = "dummy/" + SECONDARY;

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

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    String spaces = properties.indentation().spaces();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("main").append(SECONDARY), toSrcMainJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerEntity.java")
          .addTemplate("JpaBeersRepository.java")
          .addTemplate("SpringDataBeersRepository.java")
          .and()
        .batch(SOURCE.append("test").append(SECONDARY), toSrcTestJava().append(packagePath).append(SECONDARY_DESTINATION))
          .addTemplate("BeerEntityTest.java")
          .addTemplate("JpaBeersRepositoryIntTest.java")
          .and()
        .delete(path("src/main/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersRepository.java"))
        .delete(path("src/test/java").append(packagePath).append(SECONDARY_DESTINATION).append("InMemoryBeersReseter.java"))
        .and()
        .mandatoryReplacements()
          .in(path("src/main/java").append(packagePath).append("dummy/application/BeersApplicationService.java"))
            .add(IMPORT_NEEDLE, "import org.springframework.transaction.annotation.Transactional;")
            .add(CATALOG_METHOD_NEEDLE, spaces + "@Transactional(readOnly = true)")
            .add(REMOVE_METHOD_NEEDLE, spaces + "@Transactional")
            .add(CREATE_METHOD_NEEDLE, spaces + "@Transactional")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
