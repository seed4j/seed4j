package tech.jhipster.lite.generator.enums.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.JHLiteModuleSlug;
import tech.jhipster.lite.generator.enums.application.EnumApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class EnumModuleConfiguration {

  @Bean
  JHipsterModuleResource enumModule(EnumApplicationService enumModule) {
    return JHipsterModuleResource
      .builder()
      .slug(JHLiteModuleSlug.JAVA_ENUMS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Java", "Add Enums module")
      .standalone()
      .tags("server")
      .factory(enumModule::buildModule);
  }
}
