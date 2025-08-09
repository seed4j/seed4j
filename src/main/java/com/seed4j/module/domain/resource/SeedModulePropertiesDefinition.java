package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.basePackageProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.endOfLineProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.indentationProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.nodePackageManagerProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.projectBaseNameProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.projectNameProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.serverPortProperty;
import static com.seed4j.module.domain.resource.SeedModulePropertyDefinition.springConfigurationFormatProperty;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SeedModulePropertiesDefinition {

  public static final SeedModulePropertiesDefinition EMPTY = builder().build();

  private static final Comparator<SeedModulePropertyDefinition> DEFINITION_COMPARATOR = Comparator.comparing(
    SeedModulePropertyDefinition::order
  ).thenComparing(definition -> definition.key().get());

  private final Set<SeedModulePropertyDefinition> definitions;

  private SeedModulePropertiesDefinition(SeedModulePropertiesDefinitionBuilder builder) {
    definitions = buildDefinitions(builder);
  }

  private Set<SeedModulePropertyDefinition> buildDefinitions(SeedModulePropertiesDefinitionBuilder builder) {
    Set<SeedModulePropertyDefinition> result = new TreeSet<>(DEFINITION_COMPARATOR);
    result.addAll(builder.definitions);

    return Collections.unmodifiableSet(result);
  }

  public static SeedModulePropertiesDefinitionBuilder builder() {
    return new SeedModulePropertiesDefinitionBuilder();
  }

  public Collection<SeedModulePropertyDefinition> get() {
    return definitions;
  }

  public Stream<SeedModulePropertyDefinition> stream() {
    return definitions.stream();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("definitions", definitions).build();
  }

  public static final class SeedModulePropertiesDefinitionBuilder {

    private final Collection<SeedModulePropertyDefinition> definitions = new ArrayList<>();

    private SeedModulePropertiesDefinitionBuilder() {}

    public SeedModulePropertiesDefinitionBuilder addBasePackage() {
      return add(basePackageProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addProjectName() {
      return add(projectNameProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addProjectBaseName() {
      return add(projectBaseNameProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addNodePackageManager() {
      return add(nodePackageManagerProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addServerPort() {
      return add(serverPortProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addIndentation() {
      return add(indentationProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addSpringConfigurationFormat() {
      return add(springConfigurationFormatProperty());
    }

    public SeedModulePropertiesDefinitionBuilder addEndOfLine() {
      return add(endOfLineProperty());
    }

    public SeedModulePropertiesDefinitionBuilder add(SeedModulePropertyDefinition propertyDefinition) {
      Assert.notNull("propertyDefinition", propertyDefinition);

      definitions.add(propertyDefinition);

      return this;
    }

    public SeedModulePropertiesDefinition build() {
      return new SeedModulePropertiesDefinition(this);
    }
  }
}
