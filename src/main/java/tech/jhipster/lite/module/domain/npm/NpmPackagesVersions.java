package tech.jhipster.lite.module.domain.npm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import tech.jhipster.lite.shared.error.domain.Assert;

public class NpmPackagesVersions {

  public static final NpmPackagesVersions EMPTY = builder().build();

  private final Map<NpmVersionSource, NpmSourceVersions> versions;

  private NpmPackagesVersions(NpmPackagesVersionsBuilder builder) {
    versions = Collections.unmodifiableMap(builder.versions);
  }

  private NpmPackagesVersions(Map<NpmVersionSource, NpmSourceVersions> versions) {
    this.versions = Collections.unmodifiableMap(versions);
  }

  public static NpmPackagesVersionsBuilder builder() {
    return new NpmPackagesVersionsBuilder();
  }

  public NpmPackageVersion get(NpmPackageName packageName, NpmVersionSource source) {
    Assert.notNull("packageName", packageName);
    Assert.notNull("source", source);

    return Optional
      .ofNullable(versions.get(source))
      .orElseThrow(() -> new UnknownNpmPackageException(packageName, source))
      .get(packageName);
  }

  public NpmPackagesVersions merge(NpmPackagesVersions other) {
    Assert.notNull("other", other);

    Map<NpmVersionSource, NpmSourceVersions> mergedVersions = new HashMap<>(other.versions);
    versions.forEach((source, packages) -> mergedVersions.computeIfAbsent(source, NpmSourceVersions::new).add(packages));

    return new NpmPackagesVersions(mergedVersions);
  }

  public static class NpmPackagesVersionsBuilder {

    public final Map<NpmVersionSource, NpmSourceVersions> versions = new ConcurrentHashMap<>();

    public NpmPackagesVersionsBuilder put(NpmVersionSource source, Collection<NpmPackage> packages) {
      versions.computeIfAbsent(source, key -> new NpmSourceVersions(source)).add(packages);

      return this;
    }

    public NpmPackagesVersions build() {
      return new NpmPackagesVersions(this);
    }
  }

  private static class NpmSourceVersions {

    private final NpmVersionSource source;
    private final Map<NpmPackageName, NpmPackageVersion> versions = new ConcurrentHashMap<>();

    private NpmSourceVersions(NpmVersionSource source) {
      Assert.notNull("source", source);

      this.source = source;
    }

    private void add(NpmSourceVersions packages) {
      versions.putAll(packages.versions);
    }

    private void add(Collection<NpmPackage> packages) {
      Assert.field("packages", packages).notNull().noNullElement();

      versions.putAll(packages.stream().collect(Collectors.toMap(NpmPackage::name, NpmPackage::version)));
    }

    private NpmPackageVersion get(NpmPackageName packageName) {
      return Optional.ofNullable(versions.get(packageName)).orElseThrow(() -> new UnknownNpmPackageException(packageName, source));
    }
  }
}
