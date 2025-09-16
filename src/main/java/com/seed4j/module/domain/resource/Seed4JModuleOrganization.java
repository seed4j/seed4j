package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.Seed4JFeatureSlug;
import com.seed4j.module.domain.landscape.Seed4JFeatureDependency;
import com.seed4j.module.domain.landscape.Seed4JLandscapeDependency;
import com.seed4j.module.domain.landscape.Seed4JModuleDependency;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Seed4JModuleOrganization {

  public static final Seed4JModuleOrganization STANDALONE = builder().build();
  public static final Seed4JModuleOrganization SPRINGBOOT_DEPENDENCY = builder().addDependency(Seed4JCoreModuleSlug.SPRING_BOOT).build();

  private final Optional<Seed4JFeatureSlug> feature;
  private final Collection<Seed4JLandscapeDependency> dependencies;

  private Seed4JModuleOrganization(Seed4JModuleOrganizationBuilder builder) {
    feature = builder.feature;
    dependencies = builder.dependencies;
  }

  public static Seed4JModuleOrganizationBuilder builder() {
    return new Seed4JModuleOrganizationBuilder();
  }

  public Optional<Seed4JFeatureSlug> feature() {
    return feature;
  }

  public Collection<Seed4JLandscapeDependency> dependencies() {
    return dependencies;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("feature", feature.map(Seed4JFeatureSlug::get).orElse(""))
      .append("dependencies", dependencies)
      .build();
  }

  public static class Seed4JModuleOrganizationBuilder {

    private final Collection<Seed4JLandscapeDependency> dependencies = new ArrayList<>();
    private Optional<Seed4JFeatureSlug> feature = Optional.empty();

    public Seed4JModuleOrganizationBuilder feature(Seed4JFeatureSlugFactory featureFactory) {
      Assert.notNull("featureFactory", featureFactory);

      this.feature = featureFactory.build();

      return this;
    }

    public Seed4JModuleOrganizationBuilder addDependency(Seed4JModuleSlugFactory module) {
      Assert.notNull("module", module);

      dependencies.add(new Seed4JModuleDependency(module.build()));

      return this;
    }

    public Seed4JModuleOrganizationBuilder addDependency(Seed4JFeatureSlugFactory featureFactory) {
      Assert.notNull("featureFactory", featureFactory);

      featureFactory.build().map(Seed4JFeatureDependency::new).ifPresent(dependencies::add);

      return this;
    }

    public Seed4JModuleOrganization build() {
      return new Seed4JModuleOrganization(this);
    }
  }
}
