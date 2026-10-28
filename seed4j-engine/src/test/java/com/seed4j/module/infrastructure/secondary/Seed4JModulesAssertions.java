package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.TestFileUtils.contentNormalizingNewLines;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModuleUpgrade;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.approvaltests.Approvals;
import org.approvaltests.core.Scrubber;
import org.approvaltests.scrubbers.NoOpScrubber;
import org.approvaltests.scrubbers.RegExScrubber;
import org.assertj.core.api.SoftAssertions;

public final class Seed4JModulesAssertions {

  private static final FileTime FILE_APPLICATION_TIME = FileTime.fromMillis(0);

  private Seed4JModulesAssertions() {}

  public static Seed4JModuleAsserter assertThatModule(Seed4JModule module) {
    return new Seed4JModuleAsserter(module);
  }

  public static ModuleFile gradleBuildFile() {
    return fileFromClasspath("generator/buildtool/gradle/build.gradle.kts.mustache", "build.gradle.kts");
  }

  public static ModuleFile gradleLibsVersionFile() {
    return fileFromClasspath("generator/buildtool/gradle/gradle/libs.versions.toml", "gradle/libs.versions.toml");
  }

  public static ModuleFile packageJsonFile() {
    return fileFromClasspath("generator/init/package.json.mustache", "package.json");
  }

  public static ModuleFile file(String sourcePath, String destination) {
    return new ModuleFile(sourcePath, destination);
  }

  public static ModuleFile fileFromClasspath(String source, String destination) {
    URL resource = Thread.currentThread().getContextClassLoader().getResource(source);
    if (resource == null) {
      throw new AssertionError("Can't find " + source + " in classpath");
    }

    return new ModuleFile(toUri(resource), destination);
  }

  public static Seed4JModuleAsserter assertThatModuleWithFiles(Seed4JModule module, ModuleFile... files) {
    addFilesToProject(module.projectFolder(), files);

    return new Seed4JModuleAsserter(module);
  }

  public static Seed4JModuleUpgradeAsserter assertThatModuleUpgrade(Seed4JModule module, Seed4JModuleUpgrade upgrade, ModuleFile... files) {
    addFilesToProject(module.projectFolder(), files);

    return new Seed4JModuleUpgradeAsserter(module, upgrade);
  }

  public static Seed4JModuleAsserter assertThatTwoModulesWithFiles(Seed4JModule module, Seed4JModule moduleSecond, ModuleFile... files) {
    addFilesToProject(module.projectFolder(), files);

    TestSeed4JModules.apply(module);

    return new Seed4JModuleAsserter(moduleSecond);
  }

  private static void addFilesToProject(Seed4JProjectFolder project, ModuleFile... files) {
    Stream.of(files).forEach(file -> {
      Path destination = Path.of(project.folder()).resolve(file.destination);

      try {
        Files.createDirectories(destination.getParent());
      } catch (IOException e) {
        throw new AssertionError(e);
      }

      try (InputStream source = file.source.toURL().openStream()) {
        Files.copy(source, destination);
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    });
  }

  public static final class Seed4JModuleAsserter {

    private static final String SLASH = "/";

    private final Seed4JProjectFolder projectFolder;

    private Seed4JModuleAsserter(Seed4JModule module) {
      assertThat(module).as("Can't make assertions on a module without module").isNotNull();

      TestSeed4JModules.apply(module);
      projectFolder = module.projectFolder();
    }

    public Seed4JModuleAsserter hasPrefixedFiles(String prefix, String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      String[] sourceFiles = Stream.of(files)
        .map(file -> prefix + SLASH + file)
        .toArray(String[]::new);

      return hasFiles(sourceFiles);
    }

    public Seed4JModuleAsserter hasFiles(String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(projectFolder::filePath).forEach(assertFileExist(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileExist(SoftAssertions assertions) {
      return path -> assertions.assertThat(Files.exists(path)).as(fileNotFoundMessage(path, projectFolder)).isTrue();
    }

    public Seed4JModuleAsserter hasExecutableFiles(String... files) {
      assertThat(files).as("Can't check null files for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(projectFolder::filePath).forEach(assertFileIsExecutable(assertions));
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

    public Seed4JModuleAsserter doNotHaveFiles(String... files) {
      assertThat(files).as("Can't check null files as not created for a module").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(projectFolder::filePath).forEach(assertFileNotExist(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
      return path -> assertions.assertThat(Files.notExists(path)).as(fileFoundMessage(path, projectFolder)).isTrue();
    }

    public Seed4JModuleFileAsserter<Seed4JModuleAsserter> hasFile(String file) {
      return new Seed4JModuleFileAsserter<>(this, projectFolder, file);
    }
  }

  private static Supplier<String> fileNotFoundMessage(Path path, Seed4JProjectFolder projectFolder) {
    return () -> "Can't find file " + path + " in project folder, found " + projectFiles(projectFolder);
  }

  private static String projectFiles(Seed4JProjectFolder projectFolder) {
    try (Stream<Path> files = Files.walk(Path.of(projectFolder.folder()))) {
      return files.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.joining(", "));
    } catch (IOException _) {
      return "unreadable folder";
    }
  }

  public static class Seed4JModuleUpgradeAsserter {

    private final Seed4JProjectFolder projectFolder;

    public Seed4JModuleUpgradeAsserter(Seed4JModule module, Seed4JModuleUpgrade upgrade) {
      assertThat(module).as("Can't make assertions on a upgrade without module").isNotNull();
      assertThat(upgrade).as("Can't make assertions on a upgrade without upgrade").isNotNull();

      applyModuleInPast(module);
      TestSeed4JModules.apply(module.withUpgrade(upgrade));
      projectFolder = module.projectFolder();
    }

    private void applyModuleInPast(Seed4JModule module) {
      TestSeed4JModules.apply(module);

      try {
        Files.walkFileTree(module.projectFolder().filePath("."), new FileModifiedTimeUpdater());
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    }

    public Seed4JModuleUpgradeAsserter doNotUpdate(String file) {
      FileTime lastModifiedTime = readFileTime(file);

      assertThat(lastModifiedTime)
        .as(() -> "File " + projectFolder.filePath(file) + " was updated (should not be)")
        .isEqualTo(FILE_APPLICATION_TIME);

      return this;
    }

    public Seed4JModuleUpgradeAsserter update(String file) {
      FileTime lastModifiedTime = readFileTime(file);

      assertThat(lastModifiedTime)
        .as(() -> "File " + projectFolder.filePath(file) + " was not updated (should have been)")
        .isNotEqualTo(FILE_APPLICATION_TIME);

      return this;
    }

    public Seed4JModuleUpgradeAsserter doNotHaveFiles(String... files) {
      assertThat(files).as("Can't check null files as not upgrade").isNotNull();

      SoftAssertions assertions = new SoftAssertions();
      Stream.of(files).map(projectFolder::filePath).forEach(assertFileNotExist(assertions));
      assertions.assertAll();

      return this;
    }

    private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
      return path -> assertions.assertThat(Files.notExists(path)).as(fileFoundMessage(path, projectFolder)).isTrue();
    }

    private FileTime readFileTime(String file) {
      try {
        return Files.getLastModifiedTime(projectFolder.filePath(file));
      } catch (IOException e) {
        throw new AssertionError(e);
      }
    }

    public Seed4JModuleFileAsserter<Seed4JModuleUpgradeAsserter> hasFile(String path) {
      return new Seed4JModuleFileAsserter<>(this, projectFolder, path);
    }
  }

  public static final class Seed4JModuleFileAsserter<T> {

    private final T moduleAsserter;
    private final Seed4JProjectFolder projectFolder;
    private final String file;

    private Seed4JModuleFileAsserter(T moduleAsserter, Seed4JProjectFolder projectFolder, String file) {
      this.projectFolder = projectFolder;
      assertThat(file).as("Can't check file without file path").isNotBlank();

      this.moduleAsserter = moduleAsserter;
      this.file = file;
      assertFileExists();
    }

    private void assertFileExists() {
      Path path = projectFolder.filePath(file);

      assertThat(Files.exists(path)).as(fileNotFoundMessage(path, projectFolder)).isTrue();
    }

    /**
     * Verifies that the file content matches the saved snapshot, using ApprovalTests.
     */
    public Seed4JModuleFileAsserter<T> matchingSavedSnapshot() {
      String shortFileName = Arrays.stream(file.split("/")).toList().getLast();
      Approvals.verify(
        contentNormalizingNewLines(projectFolder.filePath(file)),
        Approvals.NAMES.withParameters(shortFileName).withScrubber(scrubberFor(file))
      );

      return this;
    }

    private Scrubber scrubberFor(String file) {
      return switch (file) {
        case "pom.xml" -> mavenVersionScrubber();
        case "package.json" -> npmVersionScrubber();
        case "gradle/libs.versions.toml" -> gradleCatalogVersionScrubber();
        default -> NoOpScrubber.INSTANCE;
      };
    }

    private Scrubber npmVersionScrubber() {
      return new RegExScrubber("\": \"[^~]?(\\d+\\.?)+\"", "\": \"[version]\"");
    }

    private Scrubber mavenVersionScrubber() {
      return new RegExScrubber(">(\\d+\\.?)+</", ">[version]</");
    }

    private Scrubber gradleCatalogVersionScrubber() {
      return new RegExScrubber(" = \"(\\d+\\.?)+\"", " = \"[version]\"");
    }

    public Seed4JModuleFileAsserter<T> containing(String content) {
      assertThat(content).as("Can't check blank content").isNotBlank();

      Path path = projectFolder.filePath(file);
      assertThat(contentNormalizingNewLines(path))
        .as(() -> "Can't find " + content + " in " + path)
        .contains(content);

      return this;
    }

    public Seed4JModuleFileAsserter<T> notContaining(String content) {
      assertThat(content).as("Can't check blank content").isNotBlank();

      try {
        Path path = projectFolder.filePath(file);

        assertThat(Files.readString(path))
          .as(() -> "Found " + content + " in " + path)
          .doesNotContain(content);
      } catch (IOException e) {
        throw new AssertionError("Can't check file content: " + e.getMessage(), e);
      }

      return this;
    }

    public T and() {
      return moduleAsserter;
    }
  }

  private static Supplier<String> fileFoundMessage(Path path, Seed4JProjectFolder projectFolder) {
    return () -> "Found file " + path + " in project folder, found " + projectFiles(projectFolder);
  }

  private static final class FileModifiedTimeUpdater implements FileVisitor<Path> {

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      Files.setLastModifiedTime(file, FILE_APPLICATION_TIME);

      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
      return FileVisitResult.CONTINUE;
    }
  }

  private static URI toUri(URL resource) {
    try {
      return resource.toURI();
    } catch (URISyntaxException e) {
      throw new AssertionError(e);
    }
  }

  public record ModuleFile(URI source, String destination) {
    public ModuleFile(String sourcePath, String destination) {
      this(Path.of(sourcePath).toUri(), destination);
    }

    public ModuleFile {
      Assert.notNull("source", source);
      Assert.notBlank("destination", destination);
    }
  }
}
