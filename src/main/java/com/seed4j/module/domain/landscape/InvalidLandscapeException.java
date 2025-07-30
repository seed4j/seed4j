package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterSlug;
import com.seed4j.shared.error.domain.GeneratorException;
import java.util.Collection;
import java.util.stream.Collectors;

final class InvalidLandscapeException extends GeneratorException {

  private InvalidLandscapeException(GeneratorExceptionBuilder builder) {
    super(builder);
  }

  static InvalidLandscapeException duplicatedSlug(String slug) {
    return new InvalidLandscapeException(
      internalServerError(LandscapeErrorKey.DUPLICATED_SLUG).message(buildDuplicatedSlugMessage(slug)).addParameter("slug", slug)
    );
  }

  private static String buildDuplicatedSlugMessage(String slug) {
    return new StringBuilder()
      .append("Can't share a slug between a feature and a module, slug \"")
      .append(slug)
      .append("\" is duplicated")
      .toString();
  }

  static InvalidLandscapeException unknownDependency(Collection<JHipsterSlug> knownSlugs, Collection<JHipsterSlug> remainingElements) {
    return new InvalidLandscapeException(
      internalServerError(LandscapeErrorKey.UNKNOWN_DEPENDENCY).message(buildUnknownDependencyMessage(knownSlugs, remainingElements))
    );
  }

  private static String buildUnknownDependencyMessage(Collection<JHipsterSlug> knownSlugs, Collection<JHipsterSlug> remainingElements) {
    return new StringBuilder()
      .append("Can't build landscape this happens if you have an unknown dependency or circular dependencies. Known elements: ")
      .append(displayableSlugs(knownSlugs))
      .append(" and trying to find element with all known dependencies in: ")
      .append(displayableSlugs(remainingElements))
      .toString();
  }

  private static String displayableSlugs(Collection<JHipsterSlug> slugs) {
    return slugs.stream().map(JHipsterSlug::get).collect(Collectors.joining(", "));
  }

  public static InvalidLandscapeException missingRootElement() {
    return new InvalidLandscapeException(
      internalServerError(LandscapeErrorKey.MISSING_ROOT_ELEMENT).message("Can't build landscape, can't find any root element")
    );
  }
}
