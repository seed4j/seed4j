package com.seed4j.module.domain.resource;

import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.basePackageProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.endOfLineProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.indentationProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.nodePackageManagerProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.projectBaseNameProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.projectNameProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.serverPortProperty;
import static com.seed4j.module.domain.resource.JHipsterModulePropertyDefinition.springConfigurationFormatProperty;
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

public final class JHipsterModulePropertiesDefinition {

  public static final JHipsterModulePropertiesDefinition EMPTY = builder().build();

  private static final Comparator<JHipsterModulePropertyDefinition> DEFINITION_COMPARATOR = Comparator.comparing(
    JHipsterModulePropertyDefinition::order
  ).thenComparing(definition -> definition.key().get());

  private final Set<JHipsterModulePropertyDefinition> definitions;

  private JHipsterModulePropertiesDefinition(JHipsterModulePropertiesDefinitionBuilder builder) {
    definitions = buildDefinitions(builder);
  }

  private Set<JHipsterModulePropertyDefinition> buildDefinitions(JHipsterModulePropertiesDefinitionBuilder builder) {
    Set<JHipsterModulePropertyDefinition> result = new TreeSet<>(DEFINITION_COMPARATOR);
    result.addAll(builder.definitions);

    return Collections.unmodifiableSet(result);
  }

  public static JHipsterModulePropertiesDefinitionBuilder builder() {
    return new JHipsterModulePropertiesDefinitionBuilder();
  }

  public Collection<JHipsterModulePropertyDefinition> get() {
    return definitions;
  }

  public Stream<JHipsterModulePropertyDefinition> stream() {
    return definitions.stream();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE).append("definitions", definitions).build();
  }

  public static final class JHipsterModulePropertiesDefinitionBuilder {

    private final Collection<JHipsterModulePropertyDefinition> definitions = new ArrayList<>();

    private JHipsterModulePropertiesDefinitionBuilder() {}

    public JHipsterModulePropertiesDefinitionBuilder addBasePackage() {
      return add(basePackageProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addProjectName() {
      return add(projectNameProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addProjectBaseName() {
      return add(projectBaseNameProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addNodePackageManager() {
      return add(nodePackageManagerProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addServerPort() {
      return add(serverPortProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addIndentation() {
      return add(indentationProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addSpringConfigurationFormat() {
      return add(springConfigurationFormatProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addEndOfLine() {
      return add(endOfLineProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder add(JHipsterModulePropertyDefinition propertyDefinition) {
      Assert.notNull("propertyDefinition", propertyDefinition);

      definitions.add(propertyDefinition);

      return this;
    }

    public JHipsterModulePropertiesDefinition build() {
      return new JHipsterModulePropertiesDefinition(this);
    }
  }
}
