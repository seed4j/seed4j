package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource;
import tech.jhipster.lite.module.domain.npm.NpmPackagesVersions;

@Order
@Repository
public class JHipsterLiteFileSystemNpmVersionReader implements NpmVersionsReader {

  private final FileSystemNpmVersionReader reader;

  public JHipsterLiteFileSystemNpmVersionReader(ProjectFiles projectFiles) {
    reader = new FileSystemNpmVersionReader(projectFiles, List.of(JHLiteNpmVersionSource.values()), "/generator/dependencies/");
  }

  @Override
  public NpmPackagesVersions get() {
    return reader.get();
  }
}
