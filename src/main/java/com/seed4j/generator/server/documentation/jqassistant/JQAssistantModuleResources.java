package com.seed4j.generator.server.documentation.jqassistant;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JMOLECULES;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT_JMOLECULES;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.JQASSISTANT_SPRING;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;

public final class JQAssistantModuleResources {

  private static final String DOCUMENTATION = "Documentation";
  private static final String SERVER_TAG = "server";
  private static final String DOCUMENTATION_TAG = "documentation";

  private JQAssistantModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Jqassistant implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JQASSISTANT)
        .withoutProperties()
        .apiDoc(DOCUMENTATION, "Setup jQAssistant for documentation and analysis of the project")
        .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
        .tags(SERVER_TAG, DOCUMENTATION_TAG)
        .factory(new JQAssistantModuleFactory()::buildModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Jmolecules implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JQASSISTANT_JMOLECULES)
        .withoutProperties()
        .apiDoc(DOCUMENTATION, "Add jMolecules support for jQAssistant")
        .organization(Seed4JModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(JMOLECULES).build())
        .tags(SERVER_TAG, DOCUMENTATION_TAG)
        .factory(new JQAssistantModuleFactory()::buildJMoleculesModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Spring implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(JQASSISTANT_SPRING)
        .withoutProperties()
        .apiDoc(DOCUMENTATION, "Add Spring support for jQAssistant")
        .organization(Seed4JModuleOrganization.builder().addDependency(JQASSISTANT).addDependency(SPRING_BOOT).build())
        .tags(SERVER_TAG, DOCUMENTATION_TAG)
        .factory(new JQAssistantModuleFactory()::buildSpringModule);
    }
  }
}
