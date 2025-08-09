package com.seed4j.wire.springdoc.infrastructure.primary;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringdocConfiguration {

  @Value("${application.version:undefined}")
  private String version;

  @Bean
  public OpenAPI seed4jOpenAPI() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Seed4J API")
          .description("Seed4J API")
          .version(version)
          .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
      )
      .externalDocs(new ExternalDocumentation().description("Seed4J Documentation").url("https://seed4j.com"));
  }

  @Bean
  public GroupedOpenApi seed4jAllOpenAPI(OpenApiCustomizer openApiModules) {
    return GroupedOpenApi.builder().group("all").pathsToMatch("/api/**").addOpenApiCustomizer(openApiModules).build();
  }
}
