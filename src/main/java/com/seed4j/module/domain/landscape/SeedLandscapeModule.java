package com.seed4j.module.domain.landscape;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.module.domain.resource.JHipsterModuleOperation;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleRank;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SeedLandscapeModule implements SeedLandscapeElement {

  private final SeedModuleSlug module;
  private final JHipsterModuleOperation operation;
  private final JHipsterModulePropertiesDefinition propertiesDefinition;
  private final Optional<SeedLandscapeDependencies> dependencies;
  private final JHipsterModuleRank rank;

  private SeedLandscapeModule(JHipsterLandscapeModuleBuilder builder) {
    Assert.notNull("module", builder.module);
    Assert.notNull("operation", builder.operation);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);

    module = builder.module;
    operation = builder.operation;
    propertiesDefinition = builder.propertiesDefinition;
    dependencies = SeedLandscapeDependencies.of(builder.dependencies);
    rank = builder.rank;
  }

  public static JHipsterLandscapeModuleSlugBuilder builder() {
    return new JHipsterLandscapeModuleBuilder();
  }

  @Override
  public SeedModuleSlug slug() {
    return module;
  }

  public JHipsterModuleOperation operation() {
    return operation;
  }

  public JHipsterModulePropertiesDefinition propertiesDefinition() {
    return propertiesDefinition;
  }

  public JHipsterModuleRank rank() {
    return rank;
  }

  @Override
  public Optional<SeedLandscapeDependencies> dependencies() {
    return dependencies;
  }

  @Override
  public Stream<SeedLandscapeModule> allModules() {
    return Stream.of(this);
  }

  @Override
  public Stream<SeedSlug> slugs() {
    return Stream.of(slug());
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(module).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    SeedLandscapeModule other = (SeedLandscapeModule) obj;

    return new EqualsBuilder().append(module, other.module).isEquals();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("module", module)
      .append("operation", operation)
      .append("propertiesDefinition", propertiesDefinition)
      .append("dependencies", dependencies)
      .append("rank", rank)
      .build();
  }

  private static final class JHipsterLandscapeModuleBuilder
    implements
      JHipsterLandscapeModuleSlugBuilder,
      JHipsterLandscapeModuleOperationBuilder,
      JHipsterLandscapeModulePropertiesDefinitionBuilder,
      JHipsterLandscapeModuleOptionalBuilder {

    private SeedModuleSlug module;
    private JHipsterModuleOperation operation;
    private Collection<? extends SeedLandscapeDependency> dependencies;
    private JHipsterModulePropertiesDefinition propertiesDefinition;
    private JHipsterModuleRank rank;

    @Override
    public JHipsterLandscapeModuleOperationBuilder module(SeedModuleSlug module) {
      this.module = module;

      return this;
    }

    @Override
    public JHipsterLandscapeModulePropertiesDefinitionBuilder operation(JHipsterModuleOperation operation) {
      this.operation = operation;

      return this;
    }

    @Override
    public JHipsterLandscapeModuleOptionalBuilder propertiesDefinition(JHipsterModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public JHipsterLandscapeModuleOptionalBuilder rank(JHipsterModuleRank rank) {
      this.rank = rank;

      return this;
    }

    @Override
    public SeedLandscapeModule dependencies(Collection<? extends SeedLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return new SeedLandscapeModule(this);
    }
  }

  public interface JHipsterLandscapeModuleSlugBuilder {
    JHipsterLandscapeModuleOperationBuilder module(SeedModuleSlug module);

    default JHipsterLandscapeModuleOperationBuilder module(String module) {
      return module(new SeedModuleSlug(module));
    }
  }

  public interface JHipsterLandscapeModuleOperationBuilder {
    JHipsterLandscapeModulePropertiesDefinitionBuilder operation(JHipsterModuleOperation operation);

    default JHipsterLandscapeModulePropertiesDefinitionBuilder operation(String operation) {
      return operation(new JHipsterModuleOperation(operation));
    }
  }

  public interface JHipsterLandscapeModulePropertiesDefinitionBuilder {
    JHipsterLandscapeModuleOptionalBuilder propertiesDefinition(JHipsterModulePropertiesDefinition propertiesDefinition);
  }

  public interface JHipsterLandscapeModuleOptionalBuilder {
    JHipsterLandscapeModuleOptionalBuilder rank(JHipsterModuleRank rank);

    SeedLandscapeModule dependencies(Collection<? extends SeedLandscapeDependency> dependencies);

    default SeedLandscapeModule withoutDependencies() {
      return dependencies(null);
    }
  }
}
