package com.seed4j.module.domain.packagejson;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.nodejs.NodePackagesVersionSourceFactory;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * This class represents the {@code package.json} configurations for a Seed4J module.
 * It includes scripts, dependencies, development dependencies, and configurations
 * for removing unnecessary dependencies.
 */
public final class Seed4JModulePackageJson {

  private final Scripts scripts;
  private final PackageJsonDependencies dependencies;
  private final PackageNames dependenciesToRemove;
  private final PackageJsonDependencies devDependencies;
  private final PackageNames devDependenciesToRemove;
  private final Optional<NodeModuleFormat> nodeModuleFormat;

  private Seed4JModulePackageJson(Seed4JModulePackageJsonBuilder builder) {
    scripts = new Scripts(builder.scripts);
    dependencies = new PackageJsonDependencies(builder.dependencies);
    dependenciesToRemove = new PackageNames(builder.dependenciesToRemove);
    devDependencies = new PackageJsonDependencies(builder.devDependencies);
    devDependenciesToRemove = new PackageNames(builder.devDependenciesToRemove);
    nodeModuleFormat = Optional.ofNullable(builder.nodeModuleFormat);
  }

  public static Seed4JModulePackageJsonBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModulePackageJsonBuilder(module);
  }

  public boolean isEmpty() {
    return (
      nodeModuleFormat.isEmpty()
      && scripts.isEmpty()
      && dependencies.isEmpty()
      && devDependencies.isEmpty()
      && dependenciesToRemove.isEmpty()
      && devDependenciesToRemove.isEmpty()
    );
  }

  public Scripts scripts() {
    return scripts;
  }

  public PackageJsonDependencies devDependencies() {
    return devDependencies;
  }

  public PackageJsonDependencies dependencies() {
    return dependencies;
  }

  public PackageNames devDependenciesToRemove() {
    return devDependenciesToRemove;
  }

  public PackageNames dependenciesToRemove() {
    return dependenciesToRemove;
  }

  public Optional<NodeModuleFormat> nodeModuleFormat() {
    return nodeModuleFormat;
  }

  public static final class Seed4JModulePackageJsonBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<Script> scripts = new ArrayList<>();
    private final Collection<PackageJsonDependency> dependencies = new ArrayList<>();
    private final Collection<PackageJsonDependency> devDependencies = new ArrayList<>();
    private final Collection<PackageName> dependenciesToRemove = new ArrayList<>();
    private final Collection<PackageName> devDependenciesToRemove = new ArrayList<>();
    private @Nullable NodeModuleFormat nodeModuleFormat;

    private Seed4JModulePackageJsonBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    /**
     * Add a script to the {@code package.json} scripts section.
     *
     * @param key the script key
     * @param command the script command
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder addScript(ScriptKey key, ScriptCommand command) {
      scripts.add(new Script(key, command));

      return this;
    }

    /**
     * Add a dependency to the {@code package.json} dependencies section.
     *
     * @param packageName the name of the package
     * @param versionSource the version source
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder addDependency(PackageName packageName, NodePackagesVersionSourceFactory versionSource) {
      dependencies.add(PackageJsonDependency.builder().packageName(packageName).versionSource(versionSource.build()).build());

      return this;
    }

    /**
     * Add a dependency to the {@code package.json} dependencies section with a specific version.
     *
     * @param packageName the name of the package
     * @param versionSource the version source
     * @param versionPackageName the name of the package providing the version
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder addDependency(
      PackageName packageName,
      NodePackagesVersionSourceFactory versionSource,
      PackageName versionPackageName
    ) {
      dependencies.add(
        PackageJsonDependency.builder()
          .packageName(packageName)
          .versionSource(versionSource.build())
          .versionPackageName(versionPackageName)
          .build()
      );

      return this;
    }

    /**
     * Remove a dependency from the {@code package.json} dependencies section.
     *
     * @param packageName the name of the package
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder removeDependency(PackageName packageName) {
      dependenciesToRemove.add(packageName);

      return this;
    }

    /**
     * Add a development dependency to the {@code package.json} devDependencies section.
     *
     * @param packageName the name of the package
     * @param versionSource the version source
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder addDevDependency(PackageName packageName, NodePackagesVersionSourceFactory versionSource) {
      devDependencies.add(PackageJsonDependency.builder().packageName(packageName).versionSource(versionSource.build()).build());

      return this;
    }

    /**
     * Add a development dependency to the {@code package.json} devDependencies section with a specific version.
     *
     * @param packageName the name of the package
     * @param versionSource the version source
     * @param versionPackageName the name of the package providing the version
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder addDevDependency(
      PackageName packageName,
      NodePackagesVersionSourceFactory versionSource,
      PackageName versionPackageName
    ) {
      devDependencies.add(
        PackageJsonDependency.builder()
          .packageName(packageName)
          .versionSource(versionSource.build())
          .versionPackageName(versionPackageName)
          .build()
      );

      return this;
    }

    /**
     * Remove a development dependency from the {@code package.json} devDependencies section.
     *
     * @param packageName the name of the package
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder removeDevDependency(PackageName packageName) {
      devDependenciesToRemove.add(packageName);

      return this;
    }

    /**
     * Defines the module format ("type") in the {@code package.json}.
     *
     * @param moduleFormat the module format
     * @return the builder itself
     */
    public Seed4JModulePackageJsonBuilder type(NodeModuleFormat moduleFormat) {
      nodeModuleFormat = moduleFormat;

      return this;
    }

    /**
     * Finish building the {@code package.json} configuration and return to the parent module builder.
     *
     * @return the parent module builder
     */
    public Seed4JModuleBuilder and() {
      return module;
    }

    /**
     * Build the {@code Seed4JModulePackageJson} instance.
     *
     * @return a new instance of {@code Seed4JModulePackageJson}
     */
    public Seed4JModulePackageJson build() {
      return new Seed4JModulePackageJson(this);
    }
  }
}
