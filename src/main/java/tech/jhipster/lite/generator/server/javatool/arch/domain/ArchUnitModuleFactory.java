package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterBasePackage;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ArchUnitModuleFactory {

  private static final String QUOTE = "\"";
  private static final JHipsterSource SOURCE = from("server/javatool/arch");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterDestination testDestination = toSrcTestJava().append(properties.basePackage().path());

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("packageWalkPath", packageWalkPath(properties.basePackage()))
        .and()
      .files()
        .add(SOURCE.template("archunit.properties"), to("src/test/resources/archunit.properties"))
        .add(SOURCE.template("HexagonalArchTest.java"), testDestination.append("HexagonalArchTest.java"))
        .and()
      .javaDependencies()
        .addDependency(archUnitDependency())
        .and()
      .springTestLogger("com.tngtech.archunit", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private String packageWalkPath(JHipsterBasePackage basePackage) {
    return Stream.of(basePackage.path().split("/")).map(folder -> QUOTE + folder + QUOTE).collect(Collectors.joining(", "));
  }

  private JavaDependency archUnitDependency() {
    return javaDependency()
      .groupId("com.tngtech.archunit")
      .artifactId("archunit-junit5-api")
      .versionSlug("archunit-junit5.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
