package tech.jhipster.lite.module.domain.landscape;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterLandscapeDependencies(Collection<JHipsterLandscapeDependency> dependencies) {
  public JHipsterLandscapeDependencies {
    Assert.notEmpty("dependencies", dependencies);
  }

  public static Optional<JHipsterLandscapeDependencies> of(Collection<JHipsterLandscapeDependency> dependencies) {
    return Optional.ofNullable(dependencies).filter(dep -> !dep.isEmpty()).map(JHipsterLandscapeDependencies::new);
  }

  public Collection<JHipsterLandscapeDependency> get() {
    return dependencies();
  }

  public long count() {
    return dependencies().size();
  }

  public Stream<JHipsterLandscapeDependency> stream() {
    return dependencies().stream();
  }
}
