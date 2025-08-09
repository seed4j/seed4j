package com.seed4j.module.domain.resource;

import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeedHiddenModules {

  private final Collection<String> slugs;
  private final Collection<SeedModuleTag> tags;

  public SeedHiddenModules(Collection<String> slugs, Collection<String> tags) {
    this.slugs = SeedCollections.immutable(slugs);
    this.tags = buildTags(tags);
  }

  private List<SeedModuleTag> buildTags(Collection<String> tags) {
    return SeedCollections.immutable(tags).stream().map(SeedModuleTag::new).toList();
  }

  public Collection<String> slugs() {
    return slugs;
  }

  public Collection<SeedModuleTag> tags() {
    return tags;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(slugs).append(tags).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof SeedHiddenModules other)) {
      return false;
    }

    return new EqualsBuilder().append(slugs, other.slugs).append(tags, other.tags).isEquals();
  }
}
