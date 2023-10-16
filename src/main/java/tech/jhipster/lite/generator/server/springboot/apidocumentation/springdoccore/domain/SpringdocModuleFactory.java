package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringdocModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdoccore");
  private static final String DESTINATION = "wire/springdoc/infrastructure/primary";

  private static final PropertyValue ALPHA = propertyValue("alpha");
  private static final PropertyValue TRUE = propertyValue(true);

  private static final String SPRINGDOC_CONFIG_JAVA_FILE = "SpringdocConfiguration.java";

  public JHipsterModule buildModuleForMvc(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SpringDocDependencies.MVC);
  }

  public JHipsterModule buildModuleForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SpringDocDependencies.WEBFLUX);
  }

  private JHipsterModule buildModule(JHipsterModuleProperties properties, SpringDocDependencies dependencies) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(DESTINATION);

    //@formatter:off
    return moduleBuilder(properties)
      .localEnvironment(localEnvironment("- [Local API doc](http://localhost:" + properties.serverPort().get() + "/swagger-ui.html)"))
      .context()
        .put("baseNameLowercase", properties.projectBaseName().uncapitalized())
        .put("apiTitle", "Project API")
        .put("apiDescription", "Project description API")
        .put("apiLicenseName", "No license")
        .put("apiLicenseUrl", "")
        .put("apiExternalDocDescription", "Project Documentation")
        .put("apiExternalDocUrl", "")
        .and()
      .javaDependencies()
        .addDependency(dependencies.ui())
        .addDependency(dependencies.api())
        .and()
      .files()
        .add(SOURCE.template(SPRINGDOC_CONFIG_JAVA_FILE), mainDestination.append(SPRINGDOC_CONFIG_JAVA_FILE))
        .and()
      .springMainProperties()
        .set(propertyKey("springdoc.swagger-ui.operationsSorter"), ALPHA)
        .set(propertyKey("springdoc.swagger-ui.tagsSorter"), ALPHA)
        .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), TRUE)
        .set(propertyKey("springdoc.enable-native-support"), TRUE)
        .and()
      .springMainLogger("io.swagger.v3.core.converter.ModelConverterContextImpl", LogLevel.WARN)
      .springTestLogger("io.swagger.v3.core.converter.ModelConverterContextImpl", LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private record SpringDocDependencies(JavaDependency ui, JavaDependency api) {
    private static final String SPRINGDOC_GROUP_ID = "org.springdoc";
    private static final String SPRINGDOC_OPENAPI_WEBMVC_VERSION_KEY = "springdoc-openapi-starter-webmvc";
    private static final String SPRINGDOC_OPENAPI_WEBFLUX_VERSION_KEY = "springdoc-openapi-starter-webflux";

    private static final JavaDependency MVC_UI_DEPENDENCY = JavaDependency
      .builder()
      .groupId(SPRINGDOC_GROUP_ID)
      .artifactId("springdoc-openapi-starter-webmvc-ui")
      .versionSlug(SPRINGDOC_OPENAPI_WEBMVC_VERSION_KEY)
      .build();

    private static final JavaDependency MVC_API_DEPENDENCY = JavaDependency
      .builder()
      .groupId(SPRINGDOC_GROUP_ID)
      .artifactId("springdoc-openapi-starter-webmvc-api")
      .versionSlug(SPRINGDOC_OPENAPI_WEBMVC_VERSION_KEY)
      .build();

    private static final JavaDependency WEBFLUX_UI_DEPENDENCY = JavaDependency
      .builder()
      .groupId(SPRINGDOC_GROUP_ID)
      .artifactId("springdoc-openapi-starter-webflux-ui")
      .versionSlug(SPRINGDOC_OPENAPI_WEBFLUX_VERSION_KEY)
      .build();

    private static final JavaDependency WEBFLUX_API_DEPENDENCY = JavaDependency
      .builder()
      .groupId(SPRINGDOC_GROUP_ID)
      .artifactId("springdoc-openapi-starter-webflux-api")
      .versionSlug(SPRINGDOC_OPENAPI_WEBFLUX_VERSION_KEY)
      .build();

    private static final SpringDocDependencies MVC = new SpringDocDependencies(MVC_UI_DEPENDENCY, MVC_API_DEPENDENCY);
    private static final SpringDocDependencies WEBFLUX = new SpringDocDependencies(WEBFLUX_UI_DEPENDENCY, WEBFLUX_API_DEPENDENCY);
  }
}
