package com.seed4j.generator.server.javatool.protobuf.infrastructure.primary;

import com.seed4j.generator.server.javatool.protobuf.application.ProtobufApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProtobufModuleConfiguration {

  @Bean
  SeedModuleResource protobufModule(ProtobufApplicationService protobuf) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.PROTOBUF)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf support")
      .organization(SeedModuleOrganization.builder().addDependency(JHLiteModuleSlug.JAVA_BASE).build())
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufModule);
  }

  @Bean
  SeedModuleResource protobufBackwardsCompatibilityCheckModule(ProtobufApplicationService protobuf) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.PROTOBUF_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf backwards compatibility check")
      .organization(
        SeedModuleOrganization.builder().addDependency(JHLiteModuleSlug.PROTOBUF).addDependency(JHLiteModuleSlug.MAVEN_JAVA).build()
      )
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufBackwardsCompatibilityCheckModule);
  }
}
