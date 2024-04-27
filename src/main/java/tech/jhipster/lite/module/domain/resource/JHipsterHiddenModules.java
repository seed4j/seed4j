package tech.jhipster.lite.module.domain.resource;

import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

public class JHipsterHiddenModules {

  private final Collection<String> slugs;
  private final Collection<JHipsterModuleTag> tags;

  public JHipsterHiddenModules(Collection<String> slugs, Collection<String> tags) {
    this.slugs = JHipsterCollections.immutable(slugs);
    this.tags = buildTags(tags);
  }

  private List<JHipsterModuleTag> buildTags(Collection<String> tags) {
    return JHipsterCollections.immutable(tags).stream().map(JHipsterModuleTag::new).toList();
  }

  public Collection<String> slugs() {
    return slugs;
  }

  public Collection<JHipsterModuleTag> tags() {
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

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JHipsterHiddenModules other = (JHipsterHiddenModules) obj;
    return new EqualsBuilder().append(slugs, other.slugs).append(tags, other.tags).isEquals();
  }
}
