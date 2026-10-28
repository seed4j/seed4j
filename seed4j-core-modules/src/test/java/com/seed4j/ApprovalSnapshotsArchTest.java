package com.seed4j;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class ApprovalSnapshotsArchTest {

  private static final Path SNAPSHOTS_ROOT = Path.of("src", "test", "resources");
  private static final String SNAPSHOT_SUFFIX = ".approved.txt";

  @Test
  void shouldNotHaveSnapshotsWithoutTestMethod() throws IOException {
    try (Stream<Path> files = Files.walk(SNAPSHOTS_ROOT)) {
      List<Path> orphans = files
        .filter(path -> path.getFileName().toString().endsWith(SNAPSHOT_SUFFIX))
        .filter(not(ApprovalSnapshotsArchTest::hasTestMethod))
        .toList();

      assertThat(orphans).as("Approved snapshots without matching test method (moved or removed test?)").isEmpty();
    }
  }

  private static boolean hasTestMethod(Path snapshot) {
    String packageName = SNAPSHOTS_ROOT.relativize(snapshot.getParent()).toString().replace(File.separatorChar, '.');
    String[] names = snapshot.getFileName().toString().split("\\.");

    return loadClass(packageName + "." + names[0])
      .map(testClass -> hasMethod(testClass, names, 1))
      .orElse(false);
  }

  private static boolean hasMethod(Class<?> testClass, String[] names, int index) {
    return Stream.of(testClass.getDeclaredClasses())
      .filter(nested -> nested.getSimpleName().equals(names[index]))
      .findFirst()
      .map(nested -> hasMethod(nested, names, index + 1))
      .orElseGet(() -> Stream.of(testClass.getDeclaredMethods()).anyMatch(method -> method.getName().equals(names[index])));
  }

  private static Optional<Class<?>> loadClass(String className) {
    try {
      return Optional.of(Class.forName(className));
    } catch (ClassNotFoundException _) {
      return Optional.empty();
    }
  }
}
