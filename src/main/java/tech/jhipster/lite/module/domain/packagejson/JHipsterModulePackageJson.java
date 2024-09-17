package tech.jhipster.lite.module.domain.packagejson;

import java.util.*;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.npm.NpmVersionSourceFactory;
import tech.jhipster.lite.shared.error.domain.Assert;

/**
 * This class represents the {@code package.json} configurations for a JHipster module.
 * It includes scripts, dependencies, development dependencies, and configurations
 * for removing unnecessary dependencies.
 */
public final class JHipsterModulePackageJson {

  private final Scripts scripts;
  private final PackageJsonDependencies dependencies;
  private final PackageNames dependenciesToRemove;
  private final PackageJsonDependencies devDependencies;
  private final PackageNames devDependenciesToRemove;
  private final Optional<NodeModuleFormat> nodeModuleFormat;

  private JHipsterModulePackageJson(JHipsterModulePackageJsonBuilder builder) {
    scripts = new Scripts(builder.scripts);
    dependencies = new PackageJsonDependencies(builder.dependencies);
    dependenciesToRemove = new PackageNames(builder.dependenciesToRemove);
    devDependencies = new PackageJsonDependencies(builder.devDependencies);
    devDependenciesToRemove = new PackageNames(builder.devDependenciesToRemove);
    nodeModuleFormat = Optional.ofNullable(builder.nodeModuleFormat);
  }

  public static JHipsterModulePackageJsonBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModulePackageJsonBuilder(module);
  }

  public boolean isEmpty() {
    return (
      nodeModuleFormat.isEmpty() &&
      scripts.isEmpty() &&
      dependencies.isEmpty() &&
      devDependencies.isEmpty() &&
      dependenciesToRemove.isEmpty() &&
      devDependenciesToRemove.isEmpty()
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

  public static final class JHipsterModulePackageJsonBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<Script> scripts = new ArrayList<>();
    private final Collection<PackageJsonDependency> dependencies = new ArrayList<>();
    private final Collection<PackageJsonDependency> devDependencies = new ArrayList<>();
    private final Collection<PackageName> dependenciesToRemove = new ArrayList<>();
    private final Collection<PackageName> devDependenciesToRemove = new ArrayList<>();
    private NodeModuleFormat nodeModuleFormat;

    private JHipsterModulePackageJsonBuilder(JHipsterModuleBuilder module) {
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
    public JHipsterModulePackageJsonBuilder addScript(ScriptKey key, ScriptCommand command) {
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
    public JHipsterModulePackageJsonBuilder addDependency(PackageName packageName, NpmVersionSourceFactory versionSource) {
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
    public JHipsterModulePackageJsonBuilder addDependency(
      PackageName packageName,
      NpmVersionSourceFactory versionSource,
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
    public JHipsterModulePackageJsonBuilder removeDependency(PackageName packageName) {
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
    public JHipsterModulePackageJsonBuilder addDevDependency(PackageName packageName, NpmVersionSourceFactory versionSource) {
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
    public JHipsterModulePackageJsonBuilder addDevDependency(
      PackageName packageName,
      NpmVersionSourceFactory versionSource,
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
    public JHipsterModulePackageJsonBuilder removeDevDependency(PackageName packageName) {
      devDependenciesToRemove.add(packageName);

      return this;
    }

    /**
     * Add a type to the {@code package.json}.
     *
     * @param moduleFormat the moduleFormat
     * @return the builder itself
     * @deprecated Use {@link #type(NodeModuleFormat)} instead
     */
    @Deprecated(forRemoval = true, since = "1.17.0")
    public JHipsterModulePackageJsonBuilder addType(String moduleFormat) {
      nodeModuleFormat = Stream.of(NodeModuleFormat.values())
        .filter(enumValue -> enumValue.name().equalsIgnoreCase(moduleFormat))
        .findFirst()
        .orElseThrow();

      return this;
    }

    /**
     * Defines the module format ("type") in the {@code package.json}.
     *
     * @param moduleFormat the module format
     * @return the builder itself
     */
    public JHipsterModulePackageJsonBuilder type(NodeModuleFormat moduleFormat) {
      nodeModuleFormat = moduleFormat;

      return this;
    }

    /**
     * Finish building the {@code package.json} configuration and return to the parent module builder.
     *
     * @return the parent module builder
     */
    public JHipsterModuleBuilder and() {
      return module;
    }

    /**
     * Build the {@code JHipsterModulePackageJson} instance.
     *
     * @return a new instance of {@code JHipsterModulePackageJson}
     */
    public JHipsterModulePackageJson build() {
      return new JHipsterModulePackageJson(this);
    }
  }
}
