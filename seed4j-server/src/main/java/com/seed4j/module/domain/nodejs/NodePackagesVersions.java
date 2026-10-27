package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class NodePackagesVersions {

  public static final NodePackagesVersions EMPTY = builder().build();

  private final Map<NodePackagesVersionSource, NodePackagesSourceVersions> versions;

  private NodePackagesVersions(NpmPackagesVersionsBuilder builder) {
    versions = Collections.unmodifiableMap(builder.versions);
  }

  private NodePackagesVersions(Map<NodePackagesVersionSource, NodePackagesSourceVersions> versions) {
    this.versions = Collections.unmodifiableMap(versions);
  }

  public static NpmPackagesVersionsBuilder builder() {
    return new NpmPackagesVersionsBuilder();
  }

  public NodePackageVersion get(NodePackageName packageName, NodePackagesVersionSource source) {
    Assert.notNull("packageName", packageName);
    Assert.notNull("source", source);

    return Optional.ofNullable(versions.get(source))
      .orElseThrow(() -> new UnknownNodePackageException(packageName, source))
      .get(packageName);
  }

  public NodePackagesVersions merge(NodePackagesVersions other) {
    Assert.notNull("other", other);

    Map<NodePackagesVersionSource, NodePackagesSourceVersions> mergedVersions = new HashMap<>();
    other.versions.forEach((source, packages) -> mergedVersions.computeIfAbsent(source, NodePackagesSourceVersions::new).add(packages));
    versions.forEach((source, packages) -> mergedVersions.computeIfAbsent(source, NodePackagesSourceVersions::new).add(packages));

    return new NodePackagesVersions(mergedVersions);
  }

  public static class NpmPackagesVersionsBuilder {

    private final Map<NodePackagesVersionSource, NodePackagesSourceVersions> versions = new ConcurrentHashMap<>();

    public NpmPackagesVersionsBuilder put(NodePackagesVersionSource source, Collection<NodePackage> packages) {
      versions.computeIfAbsent(source, key -> new NodePackagesSourceVersions(source)).add(packages);

      return this;
    }

    public NodePackagesVersions build() {
      return new NodePackagesVersions(this);
    }
  }

  private static final class NodePackagesSourceVersions {

    private final NodePackagesVersionSource source;
    private final Map<NodePackageName, NodePackageVersion> versions = new ConcurrentHashMap<>();

    private NodePackagesSourceVersions(NodePackagesVersionSource source) {
      Assert.notNull("source", source);

      this.source = source;
    }

    private void add(NodePackagesSourceVersions packages) {
      versions.putAll(packages.versions);
    }

    private void add(Collection<NodePackage> packages) {
      Assert.field("packages", packages).notNull().noNullElement();

      versions.putAll(packages.stream().collect(Collectors.toMap(NodePackage::name, NodePackage::version)));
    }

    private NodePackageVersion get(NodePackageName packageName) {
      return Optional.ofNullable(versions.get(packageName)).orElseThrow(() -> new UnknownNodePackageException(packageName, source));
    }
  }
}
