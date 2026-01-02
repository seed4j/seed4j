package com.seed4j.module.domain.packagejson;

import com.seed4j.module.domain.nodejs.NodePackagesVersionSource;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class PackageJsonDependency {

  private final PackageName packageName;
  private final NodePackagesVersionSource versionSource;
  private final Optional<PackageName> versionPackageName;

  private PackageJsonDependency(PackageJsonDependencyBuilder builder) {
    Assert.notNull("packageName", builder.packageName);
    Assert.notNull("versionSource", builder.versionSource);
    this.packageName = builder.packageName;
    this.versionSource = builder.versionSource;
    this.versionPackageName = Optional.ofNullable(builder.versionPackageName);
  }

  public PackageName packageName() {
    return packageName;
  }

  public NodePackagesVersionSource versionSource() {
    return versionSource;
  }

  public Optional<PackageName> versionPackageName() {
    return versionPackageName;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (PackageJsonDependency) obj;
    return Objects.equals(this.packageName, that.packageName) && Objects.equals(this.versionSource, that.versionSource);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return Objects.hash(packageName, versionSource);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    return "PackageJsonDependency[" + "packageName=" + packageName + ", " + "versionSource=" + versionSource + ']';
  }

  public static PackageJsonDependencyPackageNameBuilder builder() {
    return new PackageJsonDependencyBuilder();
  }

  private static final class PackageJsonDependencyBuilder
    implements PackageJsonDependencyPackageNameBuilder, PackageJsonDependencyVersionSourceBuilder, PackageJsonDependencyOptionalBuilder {

    private @Nullable PackageName packageName;
    private @Nullable NodePackagesVersionSource versionSource;
    private @Nullable PackageName versionPackageName;

    @Override
    public PackageJsonDependencyVersionSourceBuilder packageName(PackageName packageName) {
      this.packageName = packageName;
      return this;
    }

    @Override
    public PackageJsonDependencyOptionalBuilder versionSource(NodePackagesVersionSource versionSource) {
      this.versionSource = versionSource;
      return this;
    }

    @Override
    public PackageJsonDependencyOptionalBuilder versionPackageName(PackageName versionPackageName) {
      this.versionPackageName = versionPackageName;
      return this;
    }

    @Override
    public PackageJsonDependency build() {
      return new PackageJsonDependency(this);
    }
  }

  public interface PackageJsonDependencyPackageNameBuilder {
    PackageJsonDependencyVersionSourceBuilder packageName(PackageName packageName);
  }

  public interface PackageJsonDependencyVersionSourceBuilder {
    PackageJsonDependencyOptionalBuilder versionSource(NodePackagesVersionSource versionSource);
  }

  public interface PackageJsonDependencyOptionalBuilder {
    PackageJsonDependencyOptionalBuilder versionPackageName(PackageName versionPackageName);

    PackageJsonDependency build();
  }
}
