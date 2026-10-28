package com.seed4j.module.domain.javaproperties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

/**
 * Logical application properties file (for example {@code application-local}), without extension: the format is chosen when
 * the module is applied.
 *
 * <p>
 * The file is searched in the candidate folders, in order. When it doesn't exist yet, it is created in the first one.
 * </p>
 */
public final class ApplicationPropertiesFile {

  private final String name;
  private final @Nullable String profile;
  private final List<String> folders;

  private ApplicationPropertiesFile(String name, @Nullable String profile, List<String> folders) {
    Assert.notBlank("name", name);
    Assert.field("folders", folders).notEmpty().noNullElement();
    folders.forEach(folder -> Assert.notBlank("folder", folder));

    this.name = name;
    this.profile = profile;
    this.folders = List.copyOf(folders);
  }

  public static ApplicationPropertiesFileFoldersBuilder named(String name) {
    Assert.notBlank("name", name);

    return (folder, otherFolders) -> {
      Assert.notNull("otherFolders", otherFolders);

      return new ApplicationPropertiesFile(name, null, Stream.concat(Stream.of(folder), Arrays.stream(otherFolders)).toList());
    };
  }

  /**
   * @param profile
   *          profile of the file, replacing the current one if any ({@code application-local} for profile {@code local})
   * @return a new file, in the same folders, for this profile
   */
  public ApplicationPropertiesFile profile(String profile) {
    Assert.notBlank("profile", profile);

    return new ApplicationPropertiesFile(name, profile, folders);
  }

  public Optional<String> profile() {
    return Optional.ofNullable(profile);
  }

  public String filename() {
    return profile()
      .map(value -> name + "-" + value)
      .orElse(name);
  }

  public List<String> folders() {
    return folders;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof ApplicationPropertiesFile file)) {
      return false;
    }

    return name.equals(file.name) && Objects.equals(profile, file.profile) && folders.equals(file.folders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, profile, folders);
  }

  @Override
  public String toString() {
    return String.join(", ", folders) + " -> " + filename();
  }

  @FunctionalInterface
  public interface ApplicationPropertiesFileFoldersBuilder {
    ApplicationPropertiesFile in(String folder, String... otherFolders);
  }
}
