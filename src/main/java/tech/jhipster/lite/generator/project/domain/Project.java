package tech.jhipster.lite.generator.project.domain;

import static tech.jhipster.lite.common.domain.FileUtils.detectEndOfLine;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.UnauthorizedValueException;

public class Project {

  private final String folder;
  private final String endOfLine;
  private final Map<String, Object> config;
  private final Optional<String> remoteUrl;
  private final Optional<LanguageType> language;
  private final Optional<BuildToolType> buildTool;
  private final Optional<ServerFrameworkType> server;
  private final Optional<ClientFrameworkType> client;
  private final Optional<DatabaseType> database;
  private final Optional<DatabaseMigrationType> databaseMigration;
  private final Optional<CacheType> cache;
  private final Optional<SecurityType> security;

  private Project(ProjectBuilder builder) {
    Assert.notBlank("folder", builder.folder);
    Assert.notNull("config", builder.config);

    this.folder = builder.folder;
    this.endOfLine = Optional.ofNullable(builder.endOfLine).orElseGet(this::detectEndOfLineOrDefault);
    this.config = builder.config;
    this.remoteUrl = Optional.ofNullable(builder.remoteUrl);
    this.language = Optional.ofNullable(builder.language);
    this.buildTool = Optional.ofNullable(builder.buildTool);
    this.server = Optional.ofNullable(builder.server);
    this.client = Optional.ofNullable(builder.client);
    this.database = Optional.ofNullable(builder.database);
    this.databaseMigration = Optional.ofNullable(builder.databaseMigration);
    this.cache = Optional.ofNullable(builder.cache);
    this.security = Optional.ofNullable(builder.security);

    validateProject();
  }

  public static ProjectBuilder builder() {
    return new ProjectBuilder();
  }

  public String getFolder() {
    return folder;
  }

  public String getEndOfLine() {
    return endOfLine;
  }

  public Map<String, Object> getConfig() {
    return config;
  }

  public Optional<String> getRemoteUrl() {
    return remoteUrl;
  }

  public Optional<LanguageType> getLanguage() {
    return language;
  }

  public Optional<BuildToolType> getBuildTool() {
    return buildTool;
  }

  public Optional<ServerFrameworkType> getServer() {
    return server;
  }

  public Optional<ClientFrameworkType> getClient() {
    return client;
  }

  public Optional<DatabaseType> getDatabase() {
    return database;
  }

  public Optional<DatabaseMigrationType> getDatabaseMigration() {
    return databaseMigration;
  }

  public Optional<CacheType> getCache() {
    return cache;
  }

  public Optional<SecurityType> getSecurity() {
    return security;
  }

  public Optional<Object> getConfig(String key) {
    return Optional.ofNullable(config.get(key));
  }

  public void addConfig(String key, Object value) {
    config.putIfAbsent(key, value);
    validateConfig();
  }

  public void addDefaultConfig(String key) {
    DefaultConfig.get(key).ifPresent(value -> addConfig(key, value));
  }

  public Optional<String> getBaseName() {
    return getStringConfig(BASE_NAME);
  }

  public Optional<String> getPackageName() {
    return getStringConfig(PACKAGE_NAME);
  }

  public Optional<String> getPackageNamePath() {
    return getPackageName().map(packageName -> packageName.replace(".", "/"));
  }

  public Optional<String> getStringConfig(String key) {
    Object value = config.get(key);
    if (value == null) {
      return Optional.empty();
    } else if (value instanceof String stringValue) {
      return Optional.of(stringValue);
    }
    throw new UnauthorizedValueException("The " + key + " is not valid");
  }

  public Optional<Integer> getIntegerConfig(String key) {
    Object value = config.get(key);
    if (value == null) {
      return Optional.empty();
    } else if (value instanceof Integer integerValue) {
      return Optional.of(integerValue);
    }
    throw new UnauthorizedValueException("The " + key + " is not valid");
  }

  private void validateConfig() {
    getBaseName().ifPresent(CheckConfig::validBaseName);
    getPackageName().ifPresent(CheckConfig::validPackageName);
  }

  public boolean isMavenProject() {
    return FileUtils.exists(getPath(getFolder(), POM_XML));
  }

  public boolean isGradleProject() {
    return FileUtils.exists(getPath(getFolder(), "build.gradle.kts"));
  }

  public void checkBuildTool() {
    if (!isMavenProject() && !isGradleProject()) {
      throw new GeneratorException("No build tool");
    }
  }

  public void validateProject() {
    checkFolder();
  }

  public void checkFolder() {
    if ("/".equals(folder)) {
      throw new UnauthorizedValueException("The generation folder shall not be set to '/'");
    }
  }

  public int getServerPort() {
    int serverPort;
    try {
      serverPort = this.getIntegerConfig("serverPort").orElse(8080);
    } catch (UnauthorizedValueException e) {
      serverPort = 8080;
    }
    return serverPort;
  }

  /**
   * Attempts detection of end-of-line characters by reading files at the root of the project folder or defaults to \n.
   * Non-UTF-8 files can lead to unexpected results.
   *
   * @return "\r\n" if found or "\n" otherwise
   * @see FileUtils#detectEndOfLine(String)
   */
  public String detectEndOfLineOrDefault() {
    Optional<String> eol = Optional.empty();
    try (Stream<Path> paths = Files.list(Path.of(this.folder)).filter(Files::isRegularFile)) {
      List<String> filenames = paths.map(Path::toString).toList();
      for (String filename : filenames) {
        eol = detectEndOfLine(filename);
        if (eol.isPresent()) {
          break;
        }
      }
    } catch (IOException ignored) {
      // defaults to LF
    }
    return eol.orElse(WordUtils.LF);
  }

  public static class ProjectBuilder {

    private String folder;
    private String endOfLine;
    private Map<String, Object> config = new HashMap<>();
    private String remoteUrl;
    private LanguageType language;
    private BuildToolType buildTool;
    private ServerFrameworkType server;
    private ClientFrameworkType client;
    private DatabaseType database;
    private DatabaseMigrationType databaseMigration;
    private CacheType cache;
    private SecurityType security;

    public Project build() {
      return new Project(this);
    }

    public ProjectBuilder folder(String folder) {
      this.folder = folder;
      return this;
    }

    public ProjectBuilder endOfLine(String endOfLine) {
      this.endOfLine = endOfLine;
      return this;
    }

    public ProjectBuilder config(Map<String, Object> config) {
      if (config != null) {
        this.config = config;
      }
      return this;
    }

    public ProjectBuilder remoteUrl(String remoteUrl) {
      this.remoteUrl = remoteUrl;
      return this;
    }

    public ProjectBuilder language(LanguageType language) {
      this.language = language;
      return this;
    }

    public ProjectBuilder buildTool(BuildToolType buildTool) {
      this.buildTool = buildTool;
      return this;
    }

    public ProjectBuilder server(ServerFrameworkType server) {
      this.server = server;
      return this;
    }

    public ProjectBuilder client(ClientFrameworkType client) {
      this.client = client;
      return this;
    }

    public ProjectBuilder database(DatabaseType database) {
      this.database = database;
      return this;
    }

    public ProjectBuilder databaseMigration(DatabaseMigrationType databaseMigration) {
      this.databaseMigration = databaseMigration;
      return this;
    }

    public ProjectBuilder cache(CacheType cache) {
      this.cache = cache;
      return this;
    }

    public ProjectBuilder security(SecurityType security) {
      this.security = security;
      return this;
    }
  }
}
