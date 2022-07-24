package tech.jhipster.lite.module.infrastructure.secondary;

import static org.joox.JOOX.$;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.joox.Match;
import org.xml.sax.SAXException;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

abstract class JavaDependenciesCommandHandler {

  protected final Indentation indentation;
  protected final Path buildFilePath;
  protected final Match document;

  public JavaDependenciesCommandHandler(Indentation indentation, Path buildFilePath) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("buildFilePath", buildFilePath);

    this.indentation = indentation;
    this.buildFilePath = buildFilePath;
    document = readDocument(buildFilePath);
  }

  private Match readDocument(Path buildFilePath) {
    try (InputStream input = Files.newInputStream(buildFilePath)) {
      return $(input);
    } catch (IOException | SAXException e) {
      throw new GeneratorException("Error reading buildFile content: " + e.getMessage(), e);
    }
  }

  public abstract void handle(SetVersion command);

  public abstract void handle(AddDirectJavaDependency command);

  public abstract void handle(RemoveDirectJavaDependency command);

  public abstract void handle(RemoveJavaDependencyManagement command);

  public abstract void handle(AddJavaDependencyManagement command);

  public abstract void handle(AddDirectJavaBuildPlugin command);

  public abstract void handle(AddBuildPluginManagement command);
}
