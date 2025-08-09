package com.seed4j.module.domain.resource;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

import com.seed4j.module.domain.SeedFeatureSlug;
import com.seed4j.module.domain.landscape.SeedFeatureDependency;
import com.seed4j.module.domain.landscape.SeedLandscapeDependency;
import com.seed4j.module.domain.landscape.SeedModuleDependency;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class JHipsterModuleOrganization {

  public static final JHipsterModuleOrganization STANDALONE = builder().build();
  public static final JHipsterModuleOrganization SPRINGBOOT_DEPENDENCY = builder().addDependency(JHLiteModuleSlug.SPRING_BOOT).build();

  private final Optional<SeedFeatureSlug> feature;
  private final Collection<SeedLandscapeDependency> dependencies;

  private JHipsterModuleOrganization(JHipsterModuleOrganizationBuilder builder) {
    feature = builder.feature;
    dependencies = builder.dependencies;
  }

  public static JHipsterModuleOrganizationBuilder builder() {
    return new JHipsterModuleOrganizationBuilder();
  }

  public Optional<SeedFeatureSlug> feature() {
    return feature;
  }

  public Collection<SeedLandscapeDependency> dependencies() {
    return dependencies;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("feature", feature.map(SeedFeatureSlug::get).orElse(""))
      .append("dependencies", dependencies)
      .build();
  }

  public static class JHipsterModuleOrganizationBuilder {

    private final Collection<SeedLandscapeDependency> dependencies = new ArrayList<>();
    private Optional<SeedFeatureSlug> feature = Optional.empty();

    public JHipsterModuleOrganizationBuilder feature(JHipsterFeatureSlugFactory featureFactory) {
      Assert.notNull("featureFactory", featureFactory);

      this.feature = featureFactory.build();

      return this;
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterModuleSlugFactory module) {
      Assert.notNull("module", module);

      dependencies.add(new SeedModuleDependency(module.build()));

      return this;
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterFeatureSlugFactory featureFactory) {
      Assert.notNull("featureFactory", featureFactory);

      featureFactory.build().map(SeedFeatureDependency::new).ifPresent(dependencies::add);

      return this;
    }

    public JHipsterModuleOrganization build() {
      return new JHipsterModuleOrganization(this);
    }
  }
}
