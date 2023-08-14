package tech.jhipster.lite.module.domain;

import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.Generated;

public abstract sealed class JHipsterSlug implements Comparable<JHipsterSlug> permits JHipsterModuleSlug, JHipsterFeatureSlug {

  private static final Pattern SLUG_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  private final String slug;

  protected JHipsterSlug(String slug) {
    Assert.notBlank("slug", slug);

    assertFormat(slug);

    this.slug = slug;
  }

  private static void assertFormat(String slug) {
    if (invalidFormat(slug)) {
      throw new InvalidJHipsterSlugException(slug);
    }
  }

  private static boolean invalidFormat(String slug) {
    return !SLUG_FORMAT.matcher(slug).matches();
  }

  public String get() {
    return slug;
  }

  @Override
  public int compareTo(JHipsterSlug other) {
    if (isInit()) {
      return -1;
    }

    if (other.isInit()) {
      return 1;
    }

    return get().compareTo(other.get());
  }

  private boolean isInit() {
    return get().equals("init");
  }

  @Override
  @Generated
  public int hashCode() {
    return new HashCodeBuilder().append(slug).hashCode();
  }

  @Override
  @Generated
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JHipsterSlug other = (JHipsterSlug) obj;
    return new EqualsBuilder().append(slug, other.slug).isEquals();
  }
}
