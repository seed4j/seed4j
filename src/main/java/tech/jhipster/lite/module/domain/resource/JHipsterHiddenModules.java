package tech.jhipster.lite.module.domain.resource;

import java.util.Collection;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;

public record JHipsterHiddenModules(Collection<String> slugs, Collection<JHipsterModuleTag> tags) {
  public JHipsterHiddenModules(Collection<String> slugs, Collection<JHipsterModuleTag> tags) {
    this.slugs = JHipsterCollections.immutable(slugs);
    this.tags = JHipsterCollections.immutable(tags);
  }
}
