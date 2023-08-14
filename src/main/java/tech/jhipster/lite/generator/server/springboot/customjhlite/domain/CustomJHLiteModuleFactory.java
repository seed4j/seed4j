package tech.jhipster.lite.generator.server.springboot.customjhlite.domain;

import static tech.jhipster.lite.generator.server.springboot.cucumbercommon.domain.CucumbersModules.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyOptionalValueBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CustomJHLiteModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/custom-jhlite");
  private static final JHipsterSource CUCUMBER_SOURCE = from("server/springboot/cucumber");

  private static final String SRC_MAIN_JAVA = "src/main/java";

  private static final PropertyKey EXCEPTION_PACKAGE_KEY = propertyKey("application.exception.package");
  private static final PropertyKey SERVER_PORT_KEY = propertyKey("server.port");
  private static final PropertyKey JACKSON_INCLUSION_KEY = propertyKey("spring.jackson.default-property-inclusion");

  private static final PropertyKey HIDDEN_TAGS_PROPERTY_KEY = propertyKey("jhlite-hidden-resources.tags");
  private static final PropertyKey HIDDEN_SLUGS_PROPERTY_KEY = propertyKey("jhlite-hidden-resources.slugs");
  private static final PropertyKey BEAN_DEFINITION_OVERRIDING_PROPERTY_KEY = propertyKey("spring.main.allow-bean-definition-overriding");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    PropertyValue exceptionPackages = exceptionPackages(properties);
    JHipsterDestination cucumberDestination = toSrcTestJava().append(packagePath).append("cucumber");

    //@formatter:off
    return cucumberModuleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .documentation(documentationTitle("Module creation"), SOURCE.template("module-creation.md"))
      .documentation(documentationTitle("Cucumber"), CUCUMBER_SOURCE.template("cucumber.md"))
      .javaDependencies()
        .addDependency(jhipsterLiteDependency())
        .addDependency(jhipsterLiteTestDependency())
      .and()
      .mandatoryReplacements()
        .in(mainClassFile(properties))
          .add(text("@SpringBootApplication"), springBootApplicationWithJHLite(properties))
          .add(lineBeforeText("import org.springframework.boot.SpringApplication;"), "import tech.jhipster.lite.JHLiteApp;")
        .and()
      .and()
      .springMainProperties()
        .set(EXCEPTION_PACKAGE_KEY, exceptionPackages)
        .set(SERVER_PORT_KEY, propertyValue(properties.serverPort().stringValue()))
        .set(JACKSON_INCLUSION_KEY, propertyValue("non_null"))
        .set(HIDDEN_TAGS_PROPERTY_KEY, propertyValue("banner"))
        .comment(HIDDEN_SLUGS_PROPERTY_KEY, comment("Disable the modules and its dependencies by slugs"))
        .set(HIDDEN_SLUGS_PROPERTY_KEY, propertyValue("custom-jhlite"))
        .and()
      .springTestProperties()
        .set(EXCEPTION_PACKAGE_KEY, exceptionPackages)
        .set(SERVER_PORT_KEY, propertyValue("0"))
        .set(BEAN_DEFINITION_OVERRIDING_PROPERTY_KEY, propertyValue("true"))
        .and()
      .files()
        .add(SOURCE.template("CucumberTest.java"), cucumberDestination.append("CucumberTest.java"))
        .add(SOURCE.template("CucumberConfiguration.java"), cucumberDestination.append("CucumberConfiguration.java"))
        .add(CUCUMBER_SOURCE.template("CucumberRestTemplate.java"), cucumberDestination.append("CucumberRestTemplate.java"))
        .add(CUCUMBER_SOURCE.file("gitkeep"), to("src/test/features/.gitkeep"))
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency jhipsterLiteDependency() {
    return jhLiteDependencyBuilder().build();
  }

  private JavaDependency jhipsterLiteTestDependency() {
    return jhLiteDependencyBuilder().classifier("tests").scope(JavaDependencyScope.TEST).type(JavaDependencyType.TEST_JAR).build();
  }

  private JavaDependencyOptionalValueBuilder jhLiteDependencyBuilder() {
    return javaDependency().groupId("tech.jhipster.lite").artifactId("jhlite").versionSlug("jhlite");
  }

  private String springBootApplicationWithJHLite(JHipsterModuleProperties properties) {
    return "@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, " + mainClassName(properties) + ".class })";
  }

  private JHipsterProjectFilePath mainClassFile(JHipsterModuleProperties properties) {
    return path(SRC_MAIN_JAVA).append(properties.packagePath()).append(mainClassName(properties) + ".java");
  }

  private String mainClassName(JHipsterModuleProperties properties) {
    return properties.projectBaseName().capitalized() + "App";
  }

  private PropertyValue exceptionPackages(JHipsterModuleProperties properties) {
    return propertyValue("org.", "java.", "net.", "jakarta.", "com.", "io.", "de.", "tech.jhipster.lite", properties.basePackage().get());
  }
}
