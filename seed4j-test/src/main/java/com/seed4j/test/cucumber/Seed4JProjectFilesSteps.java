package com.seed4j.test.cucumber;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;

/**
 * Steps checking the files of the project the modules were applied to.
 */
public class Seed4JProjectFilesSteps {

  @Then("I should have files in {string}")
  public void shouldHaveFiles(String folder, List<String> files) {
    SoftAssertions assertions = new SoftAssertions();

    files
      .stream()
      .map(file -> Path.of(TestProject.folder(), folder, file))
      .forEach(path ->
        assertions
          .assertThat(path)
          .as(() -> "Can't find file " + path + ", found " + projectFiles())
          .exists()
      );

    assertions.assertAll();
  }

  @Then("I should not have files in {string}")
  public void shouldNotHaveFiles(String folder, List<String> files) {
    SoftAssertions assertions = new SoftAssertions();

    files
      .stream()
      .map(file -> Path.of(TestProject.folder(), folder, file))
      .forEach(path ->
        assertions
          .assertThat(path)
          .as(() -> "Unexpected file " + path)
          .doesNotExist()
      );

    assertions.assertAll();
  }

  @Then("I should have {int} file in {string}")
  @Then("I should have {int} files in {string}")
  public void shouldHaveFilesCount(int filesCount, String folder) throws IOException {
    try (Stream<Path> files = Files.list(Path.of(TestProject.folder(), folder))) {
      assertThat(files.count()).isEqualTo(filesCount);
    }
  }

  @Then("I should have {string} in {string}")
  public void shouldHaveFileContent(String content, String file) throws IOException {
    assertThat(Files.readString(Path.of(TestProject.folder(), file))).contains(content);
  }

  @Then("I should not have {string} in {string}")
  public void shouldNotHaveFileContent(String content, String file) throws IOException {
    assertThat(Files.readString(Path.of(TestProject.folder(), file))).doesNotContain(content);
  }

  @Then("I should have entries in {string}")
  public void shouldHaveEntries(String file, List<String> entries) throws IOException {
    assertThat(Files.readString(Path.of(TestProject.folder(), file))).contains(entries);
  }

  private static String projectFiles() {
    try (Stream<Path> files = Files.walk(Path.of(TestProject.folder()))) {
      return files.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.joining(", "));
    } catch (IOException _) {
      return "unreadable folder";
    }
  }
}
