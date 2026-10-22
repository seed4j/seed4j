package com.seed4j.module.domain.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.NotMatchingExpectedPatternException;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JDestinationTest {

  private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

  private static final Seed4JProjectFolder PROJECT = new Seed4JProjectFolder(TestFileUtils.tmpDirForTest());

  @Test
  void shouldAddSlashWhenHappeningElementWithoutSlash() {
    assertThat(new Seed4JDestination("src/main").append("file").pathInProject(PROJECT).toString()).endsWith(path("src", "main", "file"));
  }

  @Test
  void shouldDeduplicateSlashes() {
    assertThat(new Seed4JDestination("src/main/").append("/file").pathInProject(PROJECT).toString()).endsWith(path("src", "main", "file"));
  }

  @Test
  void testToStringShowsDestination() {
    assertThat(new Seed4JDestination("src")).hasToString("src");
  }

  @Test
  void shouldGetJavaPackageNameFromMainJavaFolder() {
    assertThat(new Seed4JDestination("src/main/java/com/my/app/shared").javaPackageName()).isEqualTo("com.my.app.shared");
  }

  @Test
  void shouldGetJavaPackageNameFromTestJavaFolder() {
    assertThat(new Seed4JDestination("src/test/java/com/my/app").javaPackageName()).isEqualTo("com.my.app");
  }

  @Test
  void shouldNotGetJavaPackageNameOutsideJavaSourceFolders() {
    assertThatThrownBy(() -> new Seed4JDestination("src/main/resources/com/my/app").javaPackageName()).isExactlyInstanceOf(
      NotMatchingExpectedPatternException.class
    );
  }

  @Test
  void shouldNotGetJavaPackageNameFromJavaFile() {
    assertThatThrownBy(() -> new Seed4JDestination("src/main/java/com/my/app/Foo.java").javaPackageName()).isExactlyInstanceOf(
      NotMatchingExpectedPatternException.class
    );
  }

  @Test
  void shouldNotGetJavaPackageNameFromFolderWithTrailingSlash() {
    assertThatThrownBy(() -> new Seed4JDestination("src/main/java/com/my/app/").javaPackageName()).isExactlyInstanceOf(
      NotMatchingExpectedPatternException.class
    );
  }

  @Test
  void shouldNotGetJavaPackageNameFromFolderWithEmptySegment() {
    assertThatThrownBy(() -> new Seed4JDestination("src/main/java/com//app").javaPackageName()).isExactlyInstanceOf(
      NotMatchingExpectedPatternException.class
    );
  }

  @Test
  void shouldNotGetJavaPackageNameFromFolderWithDigitLeadingSegment() {
    assertThatThrownBy(() -> new Seed4JDestination("src/main/java/com/1app").javaPackageName()).isExactlyInstanceOf(
      NotMatchingExpectedPatternException.class
    );
  }

  private static String path(String... part) {
    return String.join(SEPARATOR, part);
  }
}
