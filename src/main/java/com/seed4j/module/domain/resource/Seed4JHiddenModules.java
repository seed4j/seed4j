package com.seed4j.module.domain.resource;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Seed4JHiddenModules {

  private final Collection<String> slugs;
  private final Collection<Seed4JModuleTag> tags;

  public Seed4JHiddenModules(Collection<String> slugs, Collection<String> tags) {
    this.slugs = Seed4JCollections.immutable(slugs);
    this.tags = buildTags(tags);
  }

  private List<Seed4JModuleTag> buildTags(Collection<String> tags) {
    return Seed4JCollections.immutable(tags).stream().map(Seed4JModuleTag::new).toList();
  }

  public Collection<String> slugs() {
    return slugs;
  }

  public Collection<Seed4JModuleTag> tags() {
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

    if (!(obj instanceof Seed4JHiddenModules other)) {
      return false;
    }

    return new EqualsBuilder().append(slugs, other.slugs).append(tags, other.tags).isEquals();
  }
}
