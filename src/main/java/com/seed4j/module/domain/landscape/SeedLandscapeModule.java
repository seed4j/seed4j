package com.seed4j.module.domain.landscape;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.module.domain.SeedSlug;
import com.seed4j.module.domain.resource.SeedModuleOperation;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleRank;
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
  private final SeedModuleOperation operation;
  private final SeedModulePropertiesDefinition propertiesDefinition;
  private final Optional<SeedLandscapeDependencies> dependencies;
  private final SeedModuleRank rank;

  private SeedLandscapeModule(SeedLandscapeModuleBuilder builder) {
    Assert.notNull("module", builder.module);
    Assert.notNull("operation", builder.operation);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);

    module = builder.module;
    operation = builder.operation;
    propertiesDefinition = builder.propertiesDefinition;
    dependencies = SeedLandscapeDependencies.of(builder.dependencies);
    rank = builder.rank;
  }

  public static SeedLandscapeModuleSlugBuilder builder() {
    return new SeedLandscapeModuleBuilder();
  }

  @Override
  public SeedModuleSlug slug() {
    return module;
  }

  public SeedModuleOperation operation() {
    return operation;
  }

  public SeedModulePropertiesDefinition propertiesDefinition() {
    return propertiesDefinition;
  }

  public SeedModuleRank rank() {
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

  private static final class SeedLandscapeModuleBuilder
    implements
      SeedLandscapeModuleSlugBuilder,
      SeedLandscapeModuleOperationBuilder,
      SeedLandscapeModulePropertiesDefinitionBuilder,
      SeedLandscapeModuleOptionalBuilder {

    private SeedModuleSlug module;
    private SeedModuleOperation operation;
    private Collection<? extends SeedLandscapeDependency> dependencies;
    private SeedModulePropertiesDefinition propertiesDefinition;
    private SeedModuleRank rank;

    @Override
    public SeedLandscapeModuleOperationBuilder module(SeedModuleSlug module) {
      this.module = module;

      return this;
    }

    @Override
    public SeedLandscapeModulePropertiesDefinitionBuilder operation(SeedModuleOperation operation) {
      this.operation = operation;

      return this;
    }

    @Override
    public SeedLandscapeModuleOptionalBuilder propertiesDefinition(SeedModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public SeedLandscapeModuleOptionalBuilder rank(SeedModuleRank rank) {
      this.rank = rank;

      return this;
    }

    @Override
    public SeedLandscapeModule dependencies(Collection<? extends SeedLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return new SeedLandscapeModule(this);
    }
  }

  public interface SeedLandscapeModuleSlugBuilder {
    SeedLandscapeModuleOperationBuilder module(SeedModuleSlug module);

    default SeedLandscapeModuleOperationBuilder module(String module) {
      return module(new SeedModuleSlug(module));
    }
  }

  public interface SeedLandscapeModuleOperationBuilder {
    SeedLandscapeModulePropertiesDefinitionBuilder operation(SeedModuleOperation operation);

    default SeedLandscapeModulePropertiesDefinitionBuilder operation(String operation) {
      return operation(new SeedModuleOperation(operation));
    }
  }

  public interface SeedLandscapeModulePropertiesDefinitionBuilder {
    SeedLandscapeModuleOptionalBuilder propertiesDefinition(SeedModulePropertiesDefinition propertiesDefinition);
  }

  public interface SeedLandscapeModuleOptionalBuilder {
    SeedLandscapeModuleOptionalBuilder rank(SeedModuleRank rank);

    SeedLandscapeModule dependencies(Collection<? extends SeedLandscapeDependency> dependencies);

    default SeedLandscapeModule withoutDependencies() {
      return dependencies(null);
    }
  }
}
