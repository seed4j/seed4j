package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.LINE_BREAK;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.npm.NpmVersionSource;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.packagejson.PackageJsonDependencies;
import tech.jhipster.lite.module.domain.packagejson.PackageJsonDependency;
import tech.jhipster.lite.module.domain.packagejson.PackageJsonType;
import tech.jhipster.lite.module.domain.packagejson.PackageName;
import tech.jhipster.lite.module.domain.packagejson.Scripts;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.enumeration.domain.Enums;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

class FileSystemPackageJsonHandler {

  private static final String QUOTE = "\"";
  private static final String LINE_END = ",";
  private static final String LINE_SEPARATOR = LINE_END + LINE_BREAK;

  private final NpmVersions npmVersions;

  public FileSystemPackageJsonHandler(NpmVersions npmVersions) {
    Assert.notNull("npmVersions", npmVersions);

    this.npmVersions = npmVersions;
  }

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, JHipsterModulePackageJson packageJson) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("packageJson", packageJson);

    if (packageJson.isEmpty()) {
      return;
    }

    Path file = getPackageJsonFile(projectFolder);

    String content = readContent(file);
    content = replaceType(indentation, packageJson.type(), content);
    content = replaceScripts(indentation, packageJson.scripts(), content);
    content = replaceDevDependencies(indentation, packageJson.devDependencies(), content);
    content = replaceDependencies(indentation, packageJson.dependencies(), content);
    content = removeDependencies(indentation, packageJson.dependenciesToRemove(), content);
    content = removeDevDependencies(indentation, packageJson.devDependenciesToRemove(), content);

    content = cleanupLineBreaks(indentation, content);
    write(file, content);
  }

  private Path getPackageJsonFile(JHipsterProjectFolder projectFolder) {
    Path file = projectFolder.filePath("package.json");

    if (Files.notExists(file)) {
      throw new MissingPackageJsonException(projectFolder);
    }

    return file;
  }

  private String cleanupLineBreaks(Indentation indentation, String content) {
    return content.replaceAll(",(\\s*|[\r\n]*)}", LINE_BREAK + indentation.spaces() + "}");
  }

  private String replaceScripts(Indentation indentation, Scripts scripts, String content) {
    return JsonAction.replace().blocName("scripts").jsonContent(content).indentation(indentation).entries(scriptEntries(scripts)).apply();
  }

  private List<JsonEntry> scriptEntries(Scripts scripts) {
    return scripts.stream().map(script -> new JsonEntry(script.key().get(), script.command().get())).toList();
  }

  private String replaceDevDependencies(Indentation indentation, PackageJsonDependencies devDependencies, String content) {
    return JsonAction
      .replace()
      .blocName("devDependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(devDependencies))
      .apply();
  }

  private String removeDevDependencies(Indentation indentation, PackageJsonDependencies dependenciesToRemove, String content) {
    return JsonAction
      .remove()
      .blocName("devDependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependenciesToRemove))
      .apply();
  }

  private String replaceDependencies(Indentation indentation, PackageJsonDependencies dependencies, String content) {
    return JsonAction
      .replace()
      .blocName("dependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependencies))
      .apply();
  }

  private String removeDependencies(Indentation indentation, PackageJsonDependencies dependenciesToRemove, String content) {
    return JsonAction
      .remove()
      .blocName("dependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependenciesToRemove))
      .apply();
  }

  private String replaceType(Indentation indentation, PackageJsonType packageJsonType, String content) {
    return JsonAction.replace().blocName("type").jsonContent(content).indentation(indentation).blocValue(packageJsonType.type()).apply();
  }

  private List<JsonEntry> dependenciesEntries(PackageJsonDependencies devDependencies) {
    return devDependencies.stream().map(dependency -> new JsonEntry(dependency.packageName().get(), getNpmVersion(dependency))).toList();
  }

  private String getNpmVersion(PackageJsonDependency dependency) {
    PackageName packageName = dependency.versionPackageName().orElse(dependency.packageName());
    return npmVersions.get(packageName.get(), Enums.map(dependency.versionSource(), NpmVersionSource.class)).get();
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private String readContent(Path file) {
    try {
      return Files.readString(file);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error reading " + file.toAbsolutePath() + " content" + e.getMessage(), e);
    }
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "The error handling is an hard to test implementation detail")
  private void write(Path file, String content) {
    try {
      Files.write(file, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error writing " + file.toAbsolutePath() + ": " + e.getMessage(), e);
    }
  }

  private static class JsonAction {

    private final String blocName;
    private final String jsonContent;
    private final Indentation indentation;
    private final Collection<JsonEntry> entries;
    private final JsonActionType action;
    private final String blocValue;

    private JsonAction(JsonActionBuilder builder) {
      blocName = builder.blocName;
      jsonContent = builder.jsonContent;
      indentation = builder.indentation;
      entries = builder.entries;
      action = builder.action;
      blocValue = builder.blocValue;
    }

    public static JsonActionBuilder replace() {
      return new JsonActionBuilder(JsonActionType.REPLACE);
    }

    public static JsonActionBuilder remove() {
      return new JsonActionBuilder(JsonActionType.REMOVE);
    }

    @ExcludeFromGeneratedCodeCoverage(reason = "Jacoco thinks there is a missed branch")
    public String handle() {
      Assert.notNull("action", action);

      if (blocValue != null) {
        return appendNewRootEntry(jsonContent);
      }

      if (entries == null || entries.isEmpty()) {
        return jsonContent;
      }

      return switch (action) {
        case REPLACE -> replaceEntries();
        case REMOVE -> removeEntries();
      };
    }

    private String replaceEntries() {
      String result = removeExistingEntries();

      Matcher blocMatcher = buildBlocMatcher(result);

      if (blocMatcher.find()) {
        result = appendEntries(blocMatcher);
      } else {
        result = appendNewBlock(result);
      }

      return result;
    }

    private String removeEntries() {
      return removeExistingEntries();
    }

    private String appendEntries(Matcher blocMatcher) {
      return blocMatcher.replaceFirst(match -> {
        StringBuilder result = new StringBuilder().append(match.group(1)).append(LINE_BREAK);

        result.append(entriesBloc(indentation, entries));

        result.append(LINE_END);
        return result.toString();
      });
    }

    private String appendNewRootEntry(String result) {
      String jsonBloc = new StringBuilder()
        .append(LINE_SEPARATOR)
        .append(indentation.spaces())
        .append(QUOTE)
        .append(blocName)
        .append(QUOTE)
        .append(": ")
        .append(QUOTE)
        .append(blocValue)
        .append(QUOTE)
        .toString();

      return result.replaceFirst("(\\s{1,10})\\}(\\s{1,10})$", jsonBloc + "$1}$2");
    }

    private String appendNewBlock(String result) {
      String jsonBloc = new StringBuilder()
        .append(LINE_SEPARATOR)
        .append(indentation.spaces())
        .append(QUOTE)
        .append(blocName)
        .append(QUOTE)
        .append(": {")
        .append(LINE_BREAK)
        .append(entriesBloc(indentation, entries))
        .append(LINE_BREAK)
        .append(indentation.spaces())
        .append("}")
        .toString();

      return result.replaceFirst("(\\s{1,10})\\}(\\s{1,10})$", jsonBloc + "$1}$2");
    }

    private Matcher buildBlocMatcher(String result) {
      return Pattern.compile("(\"" + blocName + "\"\\s*:\\s*\\{)").matcher(result);
    }

    @ExcludeFromGeneratedCodeCoverage(reason = "Combiner can't be tested and an implementation detail")
    private String removeExistingEntries() {
      return entries
        .stream()
        .map(toEntryPattern(blocName, indentation))
        .reduce(jsonContent, (json, entryPattern) -> entryPattern.matcher(json).replaceAll("$1$2"), (first, second) -> first);
    }

    private Function<JsonEntry, Pattern> toEntryPattern(String blocName, Indentation indentation) {
      return entry -> {
        String pattern = new StringBuilder()
          .append("(\"")
          .append(blocName)
          .append("\"\\s*:\\s*\\{)([^}]*)(\"")
          .append(entry.key())
          .append("\"\\s*:\\s*\"[^\\r\\n]+[\\r\\n]{1,2}(\\s{")
          .append(indentation.spacesCount() * 2)
          .append("})?)")
          .toString();

        return Pattern.compile(pattern, Pattern.DOTALL);
      };
    }

    private String entriesBloc(Indentation indentation, Collection<JsonEntry> entries) {
      return entries.stream().map(entry -> entry.toJson(indentation)).collect(Collectors.joining(LINE_SEPARATOR));
    }

    private static class JsonActionBuilder {

      private String blocName;
      private String jsonContent;
      private Indentation indentation;
      private Collection<JsonEntry> entries;
      private final JsonActionType action;
      private String blocValue;

      private JsonActionBuilder(JsonActionType action) {
        this.action = action;
      }

      private JsonActionBuilder blocName(String blocName) {
        this.blocName = blocName;

        return this;
      }

      private JsonActionBuilder jsonContent(String jsonContent) {
        this.jsonContent = jsonContent;

        return this;
      }

      private JsonActionBuilder indentation(Indentation indentation) {
        this.indentation = indentation;

        return this;
      }

      private JsonActionBuilder entries(Collection<JsonEntry> entries) {
        this.entries = entries;

        return this;
      }

      private String apply() {
        return new JsonAction(this).handle();
      }

      public JsonActionBuilder blocValue(String blocValue) {
        this.blocValue = blocValue;

        return this;
      }
    }
  }

  private enum JsonActionType {
    REPLACE,
    REMOVE,
  }

  private record JsonEntry(String key, String value) {
    String toJson(Indentation indentation) {
      return new StringBuilder()
        .append(indentation.times(2))
        .append(QUOTE)
        .append(key())
        .append(QUOTE)
        .append(": ")
        .append(QUOTE)
        .append(value())
        .append(QUOTE)
        .toString();
    }
  }
}
