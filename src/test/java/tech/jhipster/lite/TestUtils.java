package tech.jhipster.lite;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;

public class TestUtils {

  public static void assertFileExist(Project project, String... paths) {
    assertFileExist(getPath(project.getFolder(), getPath(paths)));
  }

  public static void assertFileExist(String... paths) {
    assertTrue(exists(getPath(paths)), "The file '" + getPath(paths) + "' does not " + "exist.");
  }

  public static void assertFileNotExist(Project project, String... paths) {
    assertFileNotExist(getPath(project.getFolder(), getPath(paths)));
  }

  public static void assertFileNotExist(String... paths) {
    assertFalse(exists(getPath(paths)), "The file '" + getPath(paths) + "' should not exist.");
  }

  public static void assertFileContent(Project project, String filename, String value) {
    assertTrue(FileUtils.containsInLine(getPath(project.getFolder(), filename), value), "The value '" + value + "' was not found");
  }

  public static void assertFileContent(String path, String filename, String value) {
    assertTrue(FileUtils.containsInLine(getPath(path, filename), value), "The value '" + value + "' was not found");
  }

  public static void assertFileNoContent(Project project, String filename, String value) {
    assertFalse(FileUtils.containsInLine(getPath(project.getFolder(), filename), value), "The value '" + value + "' was found");
  }

  public static void assertFileContent(Project project, String filename, List<String> lines) {
    assertTrue(FileUtils.containsLines(getPath(project.getFolder(), filename), lines), "The lines '" + lines + "' were not found");
  }

  public static void assertFileContentManyTimes(Project project, String filename, String regexp, int times) throws Exception {
    assertEquals(
      times,
      FileUtils.countsRegexp(getPath(project.getFolder(), filename), FileUtils.REGEXP_PREFIX_MULTILINE + regexp),
      "The regexp '" + regexp + "' were found in the incorrect count"
    );
  }

  public static void assertFileContent(String path, String filename, List<String> lines) {
    assertTrue(FileUtils.containsLines(getPath(path, filename), lines), "The lines '" + lines + "' were not found");
  }

  public static void assertFileNoContent(Project project, String filename, List<String> lines) {
    assertFalse(FileUtils.containsLines(getPath(project.getFolder(), filename), lines), "The lines '" + lines + "' were found");
  }

  public static Project.ProjectBuilder tmpProjectBuilder() {
    return Project.builder().folder(FileUtils.tmpDirForTest());
  }

  public static Project tmpProject() {
    return tmpProjectBuilder().build();
  }

  public static Project tmpProjectWithPomXml() {
    Project project = tmpProject();
    copyPomXml(project);
    return project;
  }

  public static Project tmpProjectWithPackageJson() {
    Project project = tmpProject();
    copyPackageJson(project);
    return project;
  }

  public static Project tmpProjectWithPackageJsonNothing() {
    Project project = tmpProject();
    copyPackageJsonByName(project, "package-nothing.json");
    return project;
  }

  public static Project tmpProjectWithPackageJsonEmpty() {
    Project project = tmpProject();
    copyPackageJsonByName(project, "package-empty.json");
    return project;
  }

  public static Project tmpProjectWithPackageJsonComplete() {
    Project project = tmpProject();
    copyPackageJsonByName(project, "package-complete.json");
    return project;
  }

  public static Project tmpProjectWithBuildGradle() {
    Project project = tmpProject();
    copyBuildGradle(project);
    return project;
  }

  public static Project tmpProjectWithSpringBootProperties() {
    Project project = tmpProject();
    copySpringBootProperties(project);
    return project;
  }

  public static Project tmpProjectWithSpringBootLoggingConfiguration() {
    Project project = tmpProject();
    copySpringBootLogConfigurationFiles(project);
    return project;
  }

  public static void copyPomXml(Project project) {
    try {
      FileUtils.createFolder(project.getFolder());
      Files.copy(getPathOf("src/test/resources/generator/buildtool/maven/pom.test.xml"), getPathOf(project.getFolder(), POM_XML));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static void copyBuildGradle(Project project) {
    try {
      FileUtils.createFolder(project.getFolder());
      Files.copy(
        getPathOf("src/test/resources/generator/buildtool/gradle/build.test.gradle"),
        getPathOf(project.getFolder(), "build.gradle")
      );
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static void copyPackageJson(Project project) {
    try {
      FileUtils.createFolder(project.getFolder());
      Files.copy(getPathOf("src/test/resources/generator/command/package-test.json"), getPathOf(project.getFolder(), PACKAGE_JSON));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static void copyPackageJsonByName(Project project, String packageJsonFilename) {
    try {
      FileUtils.createFolder(project.getFolder());
      Files.copy(getPathOf("src/test/resources/generator/command/", packageJsonFilename), getPathOf(project.getFolder(), PACKAGE_JSON));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static void copySpringBootProperties(Project project) {
    try {
      FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
      FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES, "config"));
      Files.copy(
        getPathOf("src/test/resources/generator/server/springboot/core/application.src.properties"),
        getPathOf(project.getFolder(), MAIN_RESOURCES, "config/application.properties")
      );
      Files.copy(
        getPathOf("src/test/resources/generator/server/springboot/core/application.src.fast.properties"),
        getPathOf(project.getFolder(), MAIN_RESOURCES, "config/application-fast.properties")
      );
      Files.copy(
        getPathOf("src/test/resources/generator/server/springboot/core/application.test.properties"),
        getPathOf(project.getFolder(), TEST_RESOURCES, "config/application.properties")
      );
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  public static void copySpringBootLogConfigurationFiles(Project project) {
    try {
      FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES));
      FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES));
      Files.copy(
        getPathOf("src/test/resources/generator/server/springboot/core/logback-spring.xml"),
        getPathOf(project.getFolder(), MAIN_RESOURCES, "logback-spring.xml")
      );
      Files.copy(
        getPathOf("src/test/resources/generator/server/springboot/core/logback.xml"),
        getPathOf(project.getFolder(), TEST_RESOURCES, "logback.xml")
      );
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static final ObjectMapper mapper = createObjectMapper();

  private static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
    return mapper.writeValueAsBytes(object);
  }

  public static String asString(Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static String readFileToString(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  public static <T> T readFileToObject(String path, Class<T> valueType) {
    String stringObject = readFileToString(path);

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    try {
      return mapper.readValue(stringObject, valueType);
    } catch (JsonProcessingException e) {
      throw new AssertionError("Can't read value");
    }
  }
}
