package tech.jhipster.lite.module.domain.javadependency;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ProjectJavaDependenciesVersions {

  public static final ProjectJavaDependenciesVersions EMPTY = new ProjectJavaDependenciesVersions(null);

  private final Map<String, JavaDependencyVersion> versions;

  public ProjectJavaDependenciesVersions(Collection<JavaDependencyVersion> versions) {
    this.versions = buildVersions(versions);
  }

  private Map<String, JavaDependencyVersion> buildVersions(Collection<JavaDependencyVersion> versions) {
    if (versions == null) {
      return Map.of();
    }

    return versions.stream().collect(Collectors.toUnmodifiableMap(version -> version.slug().propertyName(), Function.identity()));
  }

  public Optional<JavaDependencyVersion> get(VersionSlug slug) {
    Assert.notNull("slug", slug);

    return Optional.ofNullable(versions.get(slug.propertyName()));
  }
}
