package com.seed4j.module.infrastructure.secondary;

import java.util.Collection;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("seed4j.hidden-resources")
class Seed4JHiddenResourcesProperties {

  private Collection<String> slugs;
  private Collection<String> tags;

  public void setSlugs(Collection<String> slugs) {
    this.slugs = slugs;
  }

  public void setTags(Collection<String> tags) {
    this.tags = tags;
  }

  public Collection<String> getSlugs() {
    return slugs;
  }

  public Collection<String> getTags() {
    return tags;
  }
}
