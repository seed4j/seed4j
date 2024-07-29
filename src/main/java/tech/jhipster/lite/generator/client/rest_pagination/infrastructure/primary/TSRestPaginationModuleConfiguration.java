package tech.jhipster.lite.generator.client.rest_pagination.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.rest_pagination.application.TSRestPaginationApplicationService;
import tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class TSRestPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource tsRestPaginationModule(TSRestPaginationApplicationService tsRestPagination) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.TS_REST_PAGINATION)
      .withoutProperties()
      .apiDoc("client", "Add rest pagination to the client")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteModuleSlug.TS_PAGINATION_DOMAIN).build())
      .tags("client")
      .factory(tsRestPagination::buildModule);
  }
}
