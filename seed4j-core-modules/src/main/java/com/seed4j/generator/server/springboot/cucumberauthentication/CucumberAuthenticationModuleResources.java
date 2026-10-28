package com.seed4j.generator.server.springboot.cucumberauthentication;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.CUCUMBER_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.SPRING_BOOT_CUCUMBER;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_JWT_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CUCUMBER_OAUTH_2_AUTHENTICATION;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_JWT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_OAUTH_2;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class CucumberAuthenticationModuleResources {

  private CucumberAuthenticationModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Oauth2Authentication implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_CUCUMBER_OAUTH_2_AUTHENTICATION)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
        .apiDoc("Spring Boot - Component Tests", "Add OAuth2 authentication steps for cucumber")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(CUCUMBER_AUTHENTICATION)
            .addDependency(SPRING_BOOT_CUCUMBER)
            .addDependency(SPRING_BOOT_OAUTH_2)
            .build()
        )
        .tags("server", "spring", "spring-boot", "test", "oauth2")
        .factory(new CucumberAuthenticationModuleFactory()::buildOauth2Module);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class JwtAuthentication implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_CUCUMBER_JWT_AUTHENTICATION)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().build())
        .apiDoc("Spring Boot - Component Tests", "Add JWT authentication steps for cucumber")
        .organization(
          Seed4JModuleOrganization.builder()
            .feature(CUCUMBER_AUTHENTICATION)
            .addDependency(SPRING_BOOT_CUCUMBER)
            .addDependency(SPRING_BOOT_JWT)
            .build()
        )
        .tags("server", "spring", "spring-boot", "test", "jwt")
        .factory(new CucumberAuthenticationModuleFactory()::buildJWTModule);
    }
  }
}
