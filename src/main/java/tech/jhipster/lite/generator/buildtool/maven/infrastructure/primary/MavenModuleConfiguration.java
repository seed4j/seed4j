package tech.jhipster.lite.generator.buildtool.maven.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class MavenModuleConfiguration {

  @Bean
  JHipsterModuleResource mavenModule(MavenApplicationService maven) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/build-tools/maven")
      .slug("maven-java")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc(new JHipsterModuleApiDoc("Build Tool", "Init Maven project with pom.xml and wrapper"))
      .tags("buildtool", "test")
      .factory(maven::buildInitModule);
  }
}
