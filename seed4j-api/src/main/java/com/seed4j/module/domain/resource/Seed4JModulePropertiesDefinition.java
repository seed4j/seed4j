package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.basePackageProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.endOfLineProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.indentationProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.nodePackageManagerProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.projectBaseNameProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.projectNameProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.serverPortProperty;
import static com.seed4j.module.domain.resource.Seed4JModulePropertyDefinition.springConfigurationFormatProperty;
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

public final class Seed4JModulePropertiesDefinition {

  public static final Seed4JModulePropertiesDefinition EMPTY = builder().build();

  private static final Comparator<Seed4JModulePropertyDefinition> DEFINITION_COMPARATOR = Comparator.comparing(
    Seed4JModulePropertyDefinition::order
  ).thenComparing(definition -> definition.key().get());

  private final Set<Seed4JModulePropertyDefinition> definitions;

  private Seed4JModulePropertiesDefinition(Seed4JModulePropertiesDefinitionBuilder builder) {
    definitions = buildDefinitions(builder);
  }

  private Set<Seed4JModulePropertyDefinition> buildDefinitions(Seed4JModulePropertiesDefinitionBuilder builder) {
    Set<Seed4JModulePropertyDefinition> result = new TreeSet<>(DEFINITION_COMPARATOR);
    result.addAll(builder.definitions);

    return Collections.unmodifiableSet(result);
  }

  public static Seed4JModulePropertiesDefinitionBuilder builder() {
    return new Seed4JModulePropertiesDefinitionBuilder();
  }

  public Collection<Seed4JModulePropertyDefinition> get() {
    return definitions;
  }

  public Stream<Seed4JModulePropertyDefinition> stream() {
    return definitions.stream();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("definitions", definitions).build();
  }

  public static final class Seed4JModulePropertiesDefinitionBuilder {

    private final Collection<Seed4JModulePropertyDefinition> definitions = new ArrayList<>();

    private Seed4JModulePropertiesDefinitionBuilder() {}

    public Seed4JModulePropertiesDefinitionBuilder addBasePackage() {
      return add(basePackageProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addProjectName() {
      return add(projectNameProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addProjectBaseName() {
      return add(projectBaseNameProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addNodePackageManager() {
      return add(nodePackageManagerProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addServerPort() {
      return add(serverPortProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addIndentation() {
      return add(indentationProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addSpringConfigurationFormat() {
      return add(springConfigurationFormatProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder addEndOfLine() {
      return add(endOfLineProperty());
    }

    public Seed4JModulePropertiesDefinitionBuilder add(Seed4JModulePropertyDefinition propertyDefinition) {
      Assert.notNull("propertyDefinition", propertyDefinition);

      definitions.add(propertyDefinition);

      return this;
    }

    public Seed4JModulePropertiesDefinition build() {
      return new Seed4JModulePropertiesDefinition(this);
    }
  }
}
