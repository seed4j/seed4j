package com.seed4j.generator.server.javatool.protobuf;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;

public final class ProtobufModuleResources {

  private ProtobufModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Protobuf implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(Seed4JCoreModuleSlug.PROTOBUF)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
        .apiDoc("Java", "Add protobuf support")
        .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.JAVA_BASE).build())
        .tags("server", "protobuf")
        .factory(new ProtobufModuleFactory()::buildProtobufModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class BackwardsCompatibilityCheck implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
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
        .factory(new ProtobufModuleFactory()::buildProtobufBackwardsCompatibilityCheckModule);
    }
  }
}
