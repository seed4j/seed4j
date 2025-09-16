package com.seed4j.generator.server.javatool.protobuf.infrastructure.primary;

import com.seed4j.generator.server.javatool.protobuf.application.ProtobufApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProtobufModuleConfiguration {

  @Bean
  Seed4JModuleResource protobufModule(ProtobufApplicationService protobuf) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.PROTOBUF)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf support")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.JAVA_BASE).build())
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufModule);
  }

  @Bean
  Seed4JModuleResource protobufBackwardsCompatibilityCheckModule(ProtobufApplicationService protobuf) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.PROTOBUF_BACKWARDS_COMPATIBILITY_CHECK)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add protobuf backwards compatibility check")
      .organization(
        Seed4JModuleOrganization.builder()
          .addDependency(Seed4JCoreModuleSlug.PROTOBUF)
          .addDependency(Seed4JCoreModuleSlug.MAVEN_JAVA)
          .build()
      )
      .tags("server", "protobuf")
      .factory(protobuf::buildProtobufBackwardsCompatibilityCheckModule);
  }
}
