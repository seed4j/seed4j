package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract sealed class SeedSlug implements Comparable<SeedSlug> permits SeedModuleSlug, SeedFeatureSlug {

  private static final Pattern SLUG_FORMAT = Pattern.compile("^[a-z0-9-]+$");

  private final String slug;

  protected SeedSlug(String slug) {
    Assert.field("slug", slug)
      .notBlank()
      .matchesPatternOrThrow(SLUG_FORMAT, () -> new InvalidJSeedSlugException(slug));

    this.slug = slug;
  }

  public String get() {
    return slug;
  }

  @Override
  public int compareTo(SeedSlug other) {
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

    if (!(obj instanceof SeedSlug other)) {
      return false;
    }

    return new EqualsBuilder().append(slug, other.slug).isEquals();
  }
}
