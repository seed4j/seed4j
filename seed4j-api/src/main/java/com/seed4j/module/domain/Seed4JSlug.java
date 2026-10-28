package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract sealed class Seed4JSlug implements Comparable<Seed4JSlug> permits Seed4JModuleSlug, Seed4JFeatureSlug {

  private static final Pattern SLUG_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  private final String slug;

  protected Seed4JSlug(String slug) {
    Assert.field("slug", slug)
      .notBlank()
      .matchesPatternOrThrow(SLUG_FORMAT, () -> new InvalidSeed4JSlugException(slug));

    this.slug = slug;
  }

  public String get() {
    return slug;
  }

  @Override
  public int compareTo(Seed4JSlug other) {
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
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(slug).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Seed4JSlug other)) {
      return false;
    }

    return new EqualsBuilder().append(slug, other.slug).isEquals();
  }
}
