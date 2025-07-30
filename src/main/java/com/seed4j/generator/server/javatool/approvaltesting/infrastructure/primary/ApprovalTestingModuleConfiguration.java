package com.seed4j.generator.server.javatool.approvaltesting.infrastructure.primary;

import com.seed4j.generator.server.javatool.approvaltesting.application.ApprovalTestingApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteFeatureSlug;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApprovalTestingModuleConfiguration {

  @Bean
  JHipsterModuleResource approvalTestingModule(ApprovalTestingApplicationService approvalTesting) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.APPROVAL_TESTS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().build())
      .apiDoc("Advanced testing", "Add ApprovalTests library for Approval testing")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteFeatureSlug.JAVA_BUILD_TOOL).build())
      .tags("server", "testing")
      .factory(approvalTesting::buildModule);
  }
}
