package tech.jhipster.lite.module.infrastructure.secondary;

import java.util.Collection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleTag;

@ConfigurationProperties("jhlite-hidden-resources")
class JHipsterHiddenResourcesProperties {

  private Collection<String> slugs;
  private Collection<JHipsterModuleTag> tags;

  public void setSlugs(Collection<String> slugs) {
    this.slugs = slugs;
  }

  public void setTags(Collection<String> tags) {
    if (tags == null) {
      this.tags = null;
    } else {
      this.tags = tags.stream().map(JHipsterModuleTag::new).toList();
    }
  }

  public Collection<String> getSlugs() {
    return slugs;
  }

  public Collection<JHipsterModuleTag> getTags() {
    return tags;
  }
}
