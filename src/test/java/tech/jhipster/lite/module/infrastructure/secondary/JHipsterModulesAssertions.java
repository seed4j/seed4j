package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public final class JHipsterModulesAssertions {

  private JHipsterModulesAssertions() {}

  public static ModuleAsserter assertThatModule(JHipsterModule module) {
    return new ModuleAsserter(module);
  }

  public static ModuleFile pomFile() {
    return file("src/test/resources/projects/init-maven/pom.xml", "pom.xml");
  }

  public static ModuleFile propertiesFile() {
    return file("src/test/resources/projects/files/application.properties", "src/main/resources/application.properties");
  }

  public static ModuleFile testLockbackFile() {
    return file("src/test/resources/projects/logback/logback.xml", "src/test/resources/logback.xml");
  }

  public static ModuleFile packageJsonFile() {
    return file("src/test/resources/projects/empty-node/package.json", "package.json");
  }

  public static ModuleFile readmeFile() {
    return file("src/test/resources/projects/README.md", "README.md");
  }

  public static ModuleFile file(String source, String destination) {
    return new ModuleFile(source, destination);
  }

  public static ModuleAsserter assertThatModuleWithFiles(JHipsterModule module, ModuleFile... files) {
    addFilesToproject(module.projectFolder(), files);

    return new ModuleAsserter(module);
  }

  private static void addFilesToproject(JHipsterProjectFolder project, ModuleFile... files) {
    Stream
      .of(files)
      .forEach(file -> {
        Path destination = Paths.get(project.folder()).resolve(file.destination);

        try {
          Files.createDirectories(destination.getParent());
        } catch (IOException e) {
          throw new AssertionError(e);
        }

        try {
          Files.copy(Paths.get(file.source), destination);
        } catch (IOException e) {
          throw new AssertionError(e);
        }
      });
  }

  public static class ModuleAsserter {

    private static final String SLASH = "/";

    private final JHipsterProjectFolder projectFolder;

    private ModuleAsserter(JHipsterModule module) {
      assertThat(module).as("Can't make assertions on a module without module").isNotNull();

      TestJHipsterModules.apply(module);
      projectFolder = module.projectFolder();
    }

    public ModuleAsserter hasJavaSources(String... files) {
      return hasPrefixedFiles("src/main/java", files);
    }

    public ModuleAsserter hasJavaTests(String... files) {
      return hasPrefixedFiles("src/test/java", files);
    }

    public ModuleAsserter hasPrefixedFiles(String prefix, String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      String[] sourceFiles = Stream.of(files).map(file -> prefix + SLASH + file).toArray(String[]::new);

      return hasFiles(sourceFiles);
    }

    public ModuleAsserter hasFiles(String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(file -> projectFolder.filePath(file)).forEach(assertFileExist(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileExist(SoftAssertions assertions) {
      return path -> assertions.assertThat(Files.exists(path)).as(fileNotFoundMessage(path, projectFolder)).isTrue();
    }

    public ModuleAsserter hasExecutableFiles(String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(file -> projectFolder.filePath(file)).forEach(assertFileIsExecutable(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileIsExecutable(SoftAssertions assertions) {
      return path ->
        assertions
          .assertThat(Files.exists(path) && Files.isExecutable(path))
          .as(() -> "File " + path + " is not executable (or doesn't exist) in project folder, found " + projectFiles(projectFolder))
          .isTrue();
    }

    public ModuleAsserter doNotHaveFiles(String... files) {
      assertThat(files).as("Can't check null files as not created for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(file -> projectFolder.filePath(file)).forEach(assertFileNotExist(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
      return path -> assertions.assertThat(Files.notExists(path)).as(fileFoundMessage(path, projectFolder)).isTrue();
    }

    public ModuleFileAsserter hasFile(String file) {
      return new ModuleFileAsserter(this, file);
    }
  }

  public static class ModuleFileAsserter {

    private final ModuleAsserter moduleAsserter;
    private final String file;

    private ModuleFileAsserter(ModuleAsserter moduleAsserter, String file) {
      assertThat(file).as("Can't check file without file path").isNotBlank();

      this.moduleAsserter = moduleAsserter;
      this.file = file;
      assertFileExists();
    }

    private void assertFileExists() {
      Path path = moduleAsserter.projectFolder.filePath(file);

      assertThat(Files.exists(path)).as(fileNotFoundMessage(path, moduleAsserter.projectFolder)).isTrue();
    }

    public ModuleFileAsserter containing(String content) {
      assertThat(content).as("Can't check blank content").isNotBlank();

      try {
        Path path = moduleAsserter.projectFolder.filePath(file);

        assertThat(Files.readString(path)).as(() -> "Can't find " + content + " in " + path.toString()).contains(content);
      } catch (IOException e) {
        throw new AssertionError("Can't check file content: " + e.getMessage(), e);
      }

      return this;
    }

    public ModuleFileAsserter notContaining(String content) {
      assertThat(content).as("Can't check blank content").isNotBlank();

      try {
        Path path = moduleAsserter.projectFolder.filePath(file);

        assertThat(Files.readString(path)).as(() -> "Found " + content + " in " + path.toString()).doesNotContain(content);
      } catch (IOException e) {
        throw new AssertionError("Can't check file content: " + e.getMessage(), e);
      }

      return this;
    }

    public ModuleAsserter and() {
      return moduleAsserter;
    }
  }

  private static Supplier<String> fileNotFoundMessage(Path path, JHipsterProjectFolder projectFolder) {
    return () -> "Can't find file " + path + " in project folder, found " + projectFiles(projectFolder);
  }

  private static Supplier<String> fileFoundMessage(Path path, JHipsterProjectFolder projectFolder) {
    return () -> "Found file " + path + " in project folder, found " + projectFiles(projectFolder);
  }

  private static String projectFiles(JHipsterProjectFolder projectFolder) {
    try {
      return Files
        .walk(Paths.get(projectFolder.folder()))
        .filter(Files::isRegularFile)
        .map(Path::toString)
        .collect(Collectors.joining(", "));
    } catch (IOException e) {
      return "unreadable folder";
    }
  }

  public static record ModuleFile(String source, String destination) {
    public ModuleFile {
      Assert.notBlank("source", source);
      Assert.notBlank("destination", destination);
    }
  }
}
