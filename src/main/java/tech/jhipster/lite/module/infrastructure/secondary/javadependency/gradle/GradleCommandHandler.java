package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.NotImplementedException;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ParsingException;

import tech.jhipster.lite.common.domain.ExcludeFromGeneratedCodeCoverage;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.javabuild.DependencySlug;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.module.domain.replacement.MandatoryFileReplacer;
import tech.jhipster.lite.module.domain.replacement.MandatoryReplacer;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;
import tech.jhipster.lite.module.infrastructure.secondary.FileSystemReplacer;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesCommandHandler;

public class GradleCommandHandler implements JavaDependenciesCommandHandler {

  private static final String COMMAND = "command";
  private static final String NOT_YET_IMPLEMENTED = "Not yet implemented";
  private static final String VERSIONS_TOML_KEY = "versions";
  private static final String LIBRARIES_TOML_KEY = "libraries";
  private static final String BUILD_GRADLE_FILE = "build.gradle.kts";

  private static final String GRADLE_DEPENDENCY_NEEDLE = "// jhipster-needle-gradle-add-dependency";
  private static final String GRADLE_TEST_DEPENDENCY_NEEDLE = "// jhipster-needle-gradle-add-dependency-test";

  private final JHipsterProjectFolder projectFolder;
  private final FileConfig versionsCatalog;
  private final FileSystemReplacer fileReplacer = new FileSystemReplacer();

  public GradleCommandHandler(Indentation indentation, JHipsterProjectFolder projectFolder) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);

    this.projectFolder = projectFolder;
    Path tomlVersionCatalogFile = tomlVersionCatalogPath();
    versionsCatalog = FileConfig.builder(tomlVersionCatalogFile)
      .sync()
      .build();

    // Missing TOML file will be automatically created, but its parent folder should exist
    if (!Files.exists(tomlVersionCatalogFile.getParent())) {
      tomlVersionCatalogFile.toFile().getParentFile().mkdirs();
    }

    try {
      versionsCatalog.load();
    } catch (ParsingException exception) {
      throw new InvalidTomlVersionCatalogException(exception);
    }
  }

  private Path tomlVersionCatalogPath() {
    return projectFolder.filePath("gradle").resolve("libs.versions.toml");
  }

  @Override
  public void handle(SetVersion command) {
    Assert.notNull(COMMAND, command);

    JavaDependencyVersion javaDependencyVersion = command.version();
    versionsCatalog.set(VERSIONS_TOML_KEY + "." + javaDependencyVersion.slug().slug(), javaDependencyVersion.version().get());

    versionsCatalog.save();
  }

  @Override
  public void handle(AddDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    addJavaDependencyToVersionCatalog(command.dependency());
    addJavaDependencyToBuildGradle(command.dependency());
  }

  private void addJavaDependencyToBuildGradle(JavaDependency dependency) {
    GradleDependencyScope gradleScope = switch(dependency.scope()) {
      case TEST -> GradleDependencyScope.TEST_IMPLEMENTATION;
      case PROVIDED -> GradleDependencyScope.COMPILE_ONLY;
      case RUNTIME -> GradleDependencyScope.RUNTIME_ONLY;
      default -> GradleDependencyScope.IMPLEMENTATION;
    };

    MandatoryReplacer replacer = new MandatoryReplacer(
      new TextNeedleBeforeReplacer(
        (contentBeforeReplacement, newText) -> !contentBeforeReplacement.contains(newText),
        gradleScope == GradleDependencyScope.TEST_IMPLEMENTATION ? GRADLE_TEST_DEPENDENCY_NEEDLE : GRADLE_DEPENDENCY_NEEDLE
      ),
      "%s(libs.%s)".formatted(gradleScope.command(), dependencySlug(dependency).replace("-", "."))
    );
    fileReplacer.handle(projectFolder, ContentReplacers.of(new MandatoryFileReplacer(new JHipsterProjectFilePath(BUILD_GRADLE_FILE), replacer)));
  }

  private void addJavaDependencyToVersionCatalog(JavaDependency dependency) {
    Config libraryConfig = Config.inMemory();
    libraryConfig.set("group", dependency.id().groupId().get());
    libraryConfig.set("name", dependency.id().artifactId().get());
    dependency.version().ifPresent(versionSlug -> libraryConfig.set("version.ref", versionSlug.slug()));
    String libraryEntryKey = dependencySlug(dependency);
    versionsCatalog.set(LIBRARIES_TOML_KEY + "." + libraryEntryKey, libraryConfig);
    versionsCatalog.save();
  }

  private static String dependencySlug(JavaDependency dependency) {
    return dependency.slug().map(DependencySlug::slug).orElse(dependency.id().artifactId().get());
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(RemoveDirectJavaDependency command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(RemoveJavaDependencyManagement command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(AddJavaDependencyManagement command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(AddDirectJavaBuildPlugin command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(AddBuildPluginManagement command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
  }

}
