package com.seed4j.generator.server.javatool.approvaltesting.infrastructure.primary;

import com.seed4j.generator.server.javatool.approvaltesting.application.ApprovalTestingApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApprovalTestingModuleConfiguration {

  @Bean
  Seed4JModuleResource approvalTestingModule(ApprovalTestingApplicationService approvalTesting) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.APPROVAL_TESTS)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add ApprovalTests library for Approval testing")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(approvalTesting::buildModule);
  }
}
