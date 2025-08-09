package com.seed4j.generator.server.springboot.mvc.security.oauth2.account.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_OAUTH_2;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_OAUTH_2_ACCOUNT;

import com.seed4j.generator.server.springboot.mvc.security.oauth2.account.application.OAuth2AccountApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OAuth2AccountModuleConfiguration {

  private static final String AUTHENTICATION = "authentication";

  @Bean
  SeedModuleResource oAuth2AccountModule(OAuth2AccountApplicationService oAuth2Account) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_OAUTH_2_ACCOUNT)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - MVC - Security", "Add a account context for OAuth 2.0 / OIDC Authentication")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "account", "user")
      .factory(oAuth2Account::buildModule);
  }
}
