package tech.jhipster.lite.generator.server.springboot.devtools.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class DevToolsModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final JHipsterSource SOURCE = from("server/springboot/devtools");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Dev tools"), SOURCE.template("devtools.md"))
      .javaDependencies()
        .addDependency(springBootDevtoolsDependency())
        .and()
      .springMainProperties()
        .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue("false"))
        .set(propertyKey("spring.devtools.restart.enabled"), propertyValue("false"))
        .and()
      .springMainProperties(springProfile("local"))
        .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue("true"))
        .set(propertyKey("spring.devtools.restart.enabled"), propertyValue("true"))
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency springBootDevtoolsDependency() {
    return JavaDependency
      .builder()
      .groupId(SPRING_GROUP)
      .artifactId(artifactId("spring-boot-devtools"))
      .scope(JavaDependencyScope.RUNTIME)
      .optional()
      .build();
  }
}
