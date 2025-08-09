package com.seed4j.generator.server.javatool.approvaltesting.infrastructure.primary;

import com.seed4j.generator.server.javatool.approvaltesting.application.ApprovalTestingApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.Seed4JFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApprovalTestingModuleConfiguration {

  @Bean
  SeedModuleResource approvalTestingModule(ApprovalTestingApplicationService approvalTesting) {
    return SeedModuleResource.builder()
      .slug(Seed4JModuleSlug.APPROVAL_TESTS)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add ApprovalTests library for Approval testing")
      .organization(SeedModuleOrganization.builder().addDependency(Seed4JFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(approvalTesting::buildModule);
  }
}
