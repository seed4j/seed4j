package com.seed4j.generator.server.javatool.protobuf.infrastructure.primary;

import com.seed4j.generator.server.javatool.protobuf.application.ProtobufApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProtobufModuleConfiguration {

  @Bean
  JHipsterModuleResource protobufModule(ProtobufApplicationService protobuf) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.PROTOBUF)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf support")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteModuleSlug.JAVA_BASE).build())
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufModule);
  }

  @Bean
  JHipsterModuleResource protobufBackwardsCompatibilityCheckModule(ProtobufApplicationService protobuf) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.PROTOBUF_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf backwards compatibility check")
      .organization(
        JHipsterModuleOrganization.builder().addDependency(JHLiteModuleSlug.PROTOBUF).addDependency(JHLiteModuleSlug.MAVEN_JAVA).build()
      )
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufBackwardsCompatibilityCheckModule);
  }
}
