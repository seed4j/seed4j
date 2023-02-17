package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.NotImplementedException;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ParsingException;

import tech.jhipster.lite.common.domain.ExcludeFromGeneratedCodeCoverage;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesCommandHandler;

public class GradleCommandHandler implements JavaDependenciesCommandHandler {

  private static final String COMMAND = "command";
  private static final String VERSIONS_TOML_KEY = "versions";
  private static final String NOT_YET_IMPLEMENTED = "Not yet implemented";

  private final JHipsterProjectFolder projectFolder;
  private final FileConfig versionsCatalog;

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
  @ExcludeFromGeneratedCodeCoverage(reason = "Not yet implemented")
  public void handle(AddDirectJavaDependency command) {
    throw new NotImplementedException(NOT_YET_IMPLEMENTED);
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
