package com.seed4j.module.domain.landscape;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.module.domain.resource.Seed4JModuleOperation;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleRank;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jspecify.annotations.Nullable;

public final class Seed4JLandscapeModule implements Seed4JLandscapeElement {

  private final Seed4JModuleSlug module;
  private final Seed4JModuleOperation operation;
  private final Seed4JModulePropertiesDefinition propertiesDefinition;
  private final Seed4JModuleRank rank;
  private final Optional<Seed4JLandscapeDependencies> dependencies;

  private Seed4JLandscapeModule(Seed4JLandscapeModuleBuilder builder) {
    Assert.notNull("module", builder.module);
    Assert.notNull("operation", builder.operation);
    Assert.notNull("propertiesDefinition", builder.propertiesDefinition);
    Assert.notNull("rank", builder.rank);

    module = builder.module;
    operation = builder.operation;
    propertiesDefinition = builder.propertiesDefinition;
    dependencies = Seed4JLandscapeDependencies.of(builder.dependencies);
    rank = builder.rank;
  }

  public static Seed4JLandscapeModuleSlugBuilder builder() {
    return new Seed4JLandscapeModuleBuilder();
  }

  @Override
  public Seed4JModuleSlug slug() {
    return module;
  }

  public Seed4JModuleOperation operation() {
    return operation;
  }

  public Seed4JModulePropertiesDefinition propertiesDefinition() {
    return propertiesDefinition;
  }

  public Seed4JModuleRank rank() {
    return rank;
  }

  @Override
  public Optional<Seed4JLandscapeDependencies> dependencies() {
    return dependencies;
  }

  @Override
  public Stream<Seed4JLandscapeModule> allModules() {
    return Stream.of(this);
  }

  @Override
  public Stream<Seed4JSlug> slugs() {
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

    Seed4JLandscapeModule other = (Seed4JLandscapeModule) obj;

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

  private static final class Seed4JLandscapeModuleBuilder
    implements
      Seed4JLandscapeModuleSlugBuilder,
      Seed4JLandscapeModuleOperationBuilder,
      Seed4JLandscapeModulePropertiesDefinitionBuilder,
      Seed4JLandscapeModuleOptionalBuilder
  {

    private @Nullable Seed4JModuleSlug module;
    private @Nullable Seed4JModuleOperation operation;
    private @Nullable Collection<? extends Seed4JLandscapeDependency> dependencies;
    private @Nullable Seed4JModulePropertiesDefinition propertiesDefinition;
    private @Nullable Seed4JModuleRank rank;

    @Override
    public Seed4JLandscapeModuleOperationBuilder module(Seed4JModuleSlug module) {
      this.module = module;

      return this;
    }

    @Override
    public Seed4JLandscapeModulePropertiesDefinitionBuilder operation(Seed4JModuleOperation operation) {
      this.operation = operation;

      return this;
    }

    @Override
    public Seed4JLandscapeModuleOptionalBuilder propertiesDefinition(Seed4JModulePropertiesDefinition propertiesDefinition) {
      this.propertiesDefinition = propertiesDefinition;

      return this;
    }

    @Override
    public Seed4JLandscapeModuleOptionalBuilder rank(Seed4JModuleRank rank) {
      this.rank = rank;

      return this;
    }

    @Override
    public Seed4JLandscapeModule dependencies(Collection<? extends Seed4JLandscapeDependency> dependencies) {
      this.dependencies = dependencies;

      return new Seed4JLandscapeModule(this);
    }
  }

  public interface Seed4JLandscapeModuleSlugBuilder {
    Seed4JLandscapeModuleOperationBuilder module(Seed4JModuleSlug module);

    default Seed4JLandscapeModuleOperationBuilder module(String module) {
      return module(new Seed4JModuleSlug(module));
    }
  }

  public interface Seed4JLandscapeModuleOperationBuilder {
    Seed4JLandscapeModulePropertiesDefinitionBuilder operation(Seed4JModuleOperation operation);

    default Seed4JLandscapeModulePropertiesDefinitionBuilder operation(String operation) {
      return operation(new Seed4JModuleOperation(operation));
    }
  }

  public interface Seed4JLandscapeModulePropertiesDefinitionBuilder {
    Seed4JLandscapeModuleOptionalBuilder propertiesDefinition(Seed4JModulePropertiesDefinition propertiesDefinition);
  }

  public interface Seed4JLandscapeModuleOptionalBuilder {
    Seed4JLandscapeModuleOptionalBuilder rank(Seed4JModuleRank rank);

    Seed4JLandscapeModule dependencies(Collection<? extends Seed4JLandscapeDependency> dependencies);

    default Seed4JLandscapeModule withoutDependencies() {
      return dependencies(List.of());
    }
  }
}
