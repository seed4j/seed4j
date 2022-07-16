package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class SpringBootMvcModulesConfiguration {

  @Bean
  JHipsterModuleResource springBootTomcatMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/web-servers/tomcat")
      .slug("springboot-tomcat")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC", "Add Spring Boot MVC with Tomcat"))
      .tags("server", "spring", "spring-boot", "mvc", "web", "tomcat")
      .factory(springBootMvc::buildTomcatModule);
  }

  @Bean
  JHipsterModuleResource springBootUndertowMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/web-servers/undertow")
      .slug("springboot-undertow")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC", "Add Spring Boot MVC with Undertow"))
      .tags("server", "spring", "spring-boot", "mvc", "web", "undertow")
      .factory(springBootMvc::buildUndertowModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addServerPort().build();
  }
}
