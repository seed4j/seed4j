package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.properties.SeedPropertyDefaultValue;
import com.seed4j.module.domain.properties.SeedPropertyDescription;
import com.seed4j.module.domain.properties.SeedPropertyKey;
import com.seed4j.module.domain.properties.SeedPropertyType;
import com.seed4j.module.domain.resource.SeedModuleApiDoc;
import com.seed4j.module.domain.resource.SeedModulePropertyDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.module.domain.resource.SeedModulesResources;
import com.seed4j.shared.enumeration.domain.Enums;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@ImportRuntimeHints(NativeHints.class)
@Configuration
class OpenApiModuleConfiguration {

  private static final String STRING_TYPE = "string";
  private static final String OBJECT_TYPE = "object";
  private static final String DESCRIPTION = "Definitions for properties in this module";
  private static final String MODULE_PROPERTY_DEFINITION_SCHEMA_NAME = "JHipsterModulePropertiesDefinition";
  private static final String MODULE_PROPERTIES_DEFINITION_SCHEMA_NAME = "JHipsterModulePropertyDefinition";

  private static final Schema<?> MODULE_PROPERTY_DEFINITION_SCHEMA = new Schema<>().$ref(
    "#/components/schemas/" + MODULE_PROPERTY_DEFINITION_SCHEMA_NAME
  );
  private static final Schema<?> MODULE_PROPERTIES_DEFINITION_SCHEMA = new Schema<>().$ref(
    "#/components/schemas/" + MODULE_PROPERTIES_DEFINITION_SCHEMA_NAME
  );

  private static final String JSON_MEDIA_TYPE = "application/json";

  @Bean
  OpenApiCustomizer openApiModules(SeedModulesResources modules) {
    return openApi -> {
      openApi
        .schema(MODULE_PROPERTIES_DEFINITION_SCHEMA_NAME, modulePropertyDefinitionSchema())
        .schema(MODULE_PROPERTY_DEFINITION_SCHEMA_NAME, propertiesDefinitionSchema());

      openApi.getComponents().getSchemas().putAll(moduleApplicationSchemas(modules));

      openApi.getPaths().putAll(buildJHipsterModulesPaths(modules));
    };
  }

  @SuppressWarnings("unchecked")
  private Schema<?> propertiesDefinitionSchema() {
    return new Schema<>()
      .addProperty("definitions", new Schema<>().type("array").description(DESCRIPTION).items(MODULE_PROPERTIES_DEFINITION_SCHEMA))
      .description(DESCRIPTION)
      .type(OBJECT_TYPE);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private Schema modulePropertyDefinitionSchema() {
    return new Schema<>()
      .name(MODULE_PROPERTIES_DEFINITION_SCHEMA_NAME)
      .type(OBJECT_TYPE)
      .addProperty(
        "type",
        new Schema<>()
          .type(STRING_TYPE)
          ._enum(Stream.of(SeedPropertyType.values()).map(SeedPropertyType::name).toList())
          .description("Type of this property")
      )
      .addProperty("mandatory", new Schema<>().type("boolean").description("True if the field is mandatory, false otherwise"))
      .addProperty("key", new Schema<>().type(STRING_TYPE).description("Key of this property"))
      .addProperty("description", new Schema<>().type(STRING_TYPE).description("Description of this property"))
      .addProperty("defaultValue", new Schema<>().type(STRING_TYPE).description("Default value for this property"))
      .required(List.of("type", "mandatory", "key"));
  }

  private Map<String, Schema<?>> moduleApplicationSchemas(SeedModulesResources modules) {
    return modules.stream().collect(Collectors.toMap(module -> schemaName(module.slug()), toModuleApplicationSchema()));
  }

  private Function<SeedModuleResource, Schema<?>> toModuleApplicationSchema() {
    return module -> {
      Schema<?> schema = new Schema<>()
        .name(schemaName(module.slug()))
        .type(OBJECT_TYPE)
        .description(DESCRIPTION)
        .addProperty("projectFolder", new Schema<>().type(STRING_TYPE).description("Path to the project"))
        .addProperty(
          "commit",
          new Schema<>().type("boolean").description("True to make a git commit after module application, false otherwise")
        );

      appendPropertiesDefinition(module, schema);

      return schema.required(buildRequirements(module));
    };
  }

  private void appendPropertiesDefinition(SeedModuleResource module, Schema<?> schema) {
    @SuppressWarnings("rawtypes")
    Map<String, Schema> moduleProperties = moduleProperties(module);

    if (moduleProperties.isEmpty()) {
      return;
    }

    Schema<?> modulePropertiesSchema = new Schema<>().type(OBJECT_TYPE).description("Properties for this module");
    modulePropertiesSchema.setProperties(moduleProperties);
    schema.addProperty("properties", modulePropertiesSchema);
  }

  private List<String> buildRequirements(SeedModuleResource module) {
    return Stream.concat(
      Stream.of("projectFolder"),
      module
        .propertiesDefinition()
        .stream()
        .filter(SeedModulePropertyDefinition::isMandatory)
        .map(SeedModulePropertyDefinition::key)
        .map(SeedPropertyKey::get)
    ).toList();
  }

  @SuppressWarnings("rawtypes")
  private Map<String, Schema> moduleProperties(SeedModuleResource module) {
    return module.propertiesDefinition().stream().collect(Collectors.toMap(property -> property.key().get(), toPropertySchema()));
  }

  private Function<SeedModulePropertyDefinition, Schema<?>> toPropertySchema() {
    return property ->
      new Schema<>()
        .type(Enums.map(property.type(), OpenApiFieldType.class).key())
        .description(property.description().map(SeedPropertyDescription::get).orElse(null))
        .example(property.defaultValue().map(SeedPropertyDefaultValue::get).orElse(null));
  }

  private Paths buildJHipsterModulesPaths(SeedModulesResources modules) {
    Paths paths = new Paths();

    paths.putAll(modulesPropertiesDefinitions(modules));
    paths.putAll(modulesApplications(modules));

    return paths;
  }

  private Map<String, PathItem> modulesPropertiesDefinitions(SeedModulesResources modules) {
    return modules
      .stream()
      .collect(Collectors.toMap(SeedModuleResource::moduleUrl, module -> modulePropertiesDefinition(module.apiDoc(), module.slug())));
  }

  private PathItem modulePropertiesDefinition(SeedModuleApiDoc apiDoc, SeedModuleSlug slug) {
    Operation getOperation = new Operation()
      .operationId(slug.get() + "-properties-definition")
      .summary("Get " + slug.get() + " properties definitions")
      .tags(apiDoc.group().list())
      .responses(
        new ApiResponses().addApiResponse(
          "200",
          new ApiResponse()
            .description("Definition for this module properties")
            .content(new Content().addMediaType("*/*", new MediaType().schema(MODULE_PROPERTY_DEFINITION_SCHEMA)))
        )
      );

    return new PathItem().get(getOperation);
  }

  private Map<String, PathItem> modulesApplications(SeedModulesResources modules) {
    return modules
      .stream()
      .collect(
        Collectors.toMap(
          module -> module.moduleUrl() + "/apply-patch",
          module -> moduleApplicationDefinition(module.apiDoc(), module.slug())
        )
      );
  }

  private PathItem moduleApplicationDefinition(SeedModuleApiDoc apiDoc, SeedModuleSlug slug) {
    Operation postOperation = new Operation()
      .operationId(slug.get() + "-application")
      .summary(apiDoc.operation().get())
      .tags(apiDoc.group().list())
      .requestBody(
        new RequestBody()
          .required(true)
          .content(new Content().addMediaType(JSON_MEDIA_TYPE, new MediaType().schema(new Schema<>().$ref(schemaName(slug)))))
      );

    return new PathItem().post(postOperation);
  }

  private String schemaName(SeedModuleSlug slug) {
    return slug.get() + "-schema";
  }
}
