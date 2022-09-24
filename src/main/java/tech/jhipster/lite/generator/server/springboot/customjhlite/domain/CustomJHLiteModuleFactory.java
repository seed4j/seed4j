package tech.jhipster.lite.generator.server.springboot.customjhlite.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CustomJHLiteModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/custom-jhlite/");

  private static final PropertyKey EXCEPTION_PACKAGE_KEY = propertyKey("application.exception.package");
  private static final PropertyKey SERVER_PORT_KEY = propertyKey("server.port");
  private static final PropertyKey JACKSON_INCLUSION_KEY = propertyKey("spring.jackson.default-property-inclusion");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Module creation"), SOURCE.file("module-creation.md"))
      .javaDependencies()
        .addDependency(jhipsterLiteDependency())
      .and()
      .mandatoryReplacements()
        .in(mainClassFile(properties))
          .add(text("@SpringBootApplication"), springBootApplicationWithJHLite(properties))
          .add(lineBeforeText("import org.springframework.boot.SpringApplication;"), "import tech.jhipster.lite.JHLiteApp;")
        .and()
      .and()
      .springMainProperties()
        .set(EXCEPTION_PACKAGE_KEY, exceptionPackages(properties))
        .set(SERVER_PORT_KEY, propertyValue(properties.serverPort().stringValue()))
        .set(JACKSON_INCLUSION_KEY, propertyValue("non_null"))
        .and()
      .springTestProperties()
        .set(SERVER_PORT_KEY, propertyValue("0"))
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency jhipsterLiteDependency() {
    return javaDependency().groupId("tech.jhipster.lite").artifactId("jhlite").versionSlug("jhlite").build();
  }

  private String springBootApplicationWithJHLite(JHipsterModuleProperties properties) {
    return "@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, " + mainClassName(properties) + ".class })";
  }

  private String mainClassFile(JHipsterModuleProperties properties) {
    return "src/main/java/" + properties.packagePath() + "/" + mainClassName(properties) + ".java";
  }

  private String mainClassName(JHipsterModuleProperties properties) {
    return properties.projectBaseName().capitalized() + "App";
  }

  private PropertyValue exceptionPackages(JHipsterModuleProperties properties) {
    return propertyValue("org.", "java.", "net.", "javax.", "com.", "io.", "de.", "tech.jhipster.lite", properties.basePackage().get());
  }
}
