package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ArchUnitModuleFactory {

  private static final String QUOTE = "\"";
  private static final JHipsterSource SOURCE = from("server/javatool/arch");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("packageWalkPath", packageWalkPath(packagePath))
        .and()
      .files()
        .add(SOURCE.template("archunit.properties"), to("src/test/resources/archunit.properties"))
        .add(SOURCE.template("AnnotationArchTest.java"), testDestination.append("AnnotationArchTest.java"))
        .add(SOURCE.template("HexagonalArchTest.java"), testDestination.append("HexagonalArchTest.java"))
        .and()
      .javaDependencies()
        .addDependency(archUnitDependency())
        .and()
      .springTestLogger("com.tngtech.archunit", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private String packageWalkPath(String packagePath) {
    return Stream.of(packagePath.split("/")).map(folder -> QUOTE + folder + QUOTE).collect(Collectors.joining(", "));
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
