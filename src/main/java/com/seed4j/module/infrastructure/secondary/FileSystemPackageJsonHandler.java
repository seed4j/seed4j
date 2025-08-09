package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.JHipsterModule.LINE_BREAK;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.SeedModuleContext;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.nodejs.NodeVersions;
import com.seed4j.module.domain.packagejson.NodeModuleFormat;
import com.seed4j.module.domain.packagejson.PackageJsonDependencies;
import com.seed4j.module.domain.packagejson.PackageJsonDependency;
import com.seed4j.module.domain.packagejson.PackageName;
import com.seed4j.module.domain.packagejson.PackageNames;
import com.seed4j.module.domain.packagejson.Scripts;
import com.seed4j.module.domain.packagejson.SeedModulePackageJson;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class FileSystemPackageJsonHandler {

  private static final String QUOTE = "\"";
  private static final String LINE_END = ",";
  private static final String LINE_SEPARATOR = LINE_END + LINE_BREAK;

  private final NodeVersions nodeVersions;
  private final TemplateRenderer templateRenderer;

  public FileSystemPackageJsonHandler(NodeVersions nodeVersions, TemplateRenderer templateRenderer) {
    Assert.notNull("nodeVersions", nodeVersions);
    Assert.notNull("templateRenderer", templateRenderer);

    this.nodeVersions = nodeVersions;
    this.templateRenderer = templateRenderer;
  }

  public void handle(
    Indentation indentation,
    SeedProjectFolder projectFolder,
    SeedModulePackageJson packageJson,
    SeedModuleContext context
  ) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("packageJson", packageJson);
    Assert.notNull("context", context);

    if (packageJson.isEmpty()) {
      return;
    }

    Path file = getPackageJsonFile(projectFolder);

    String content = readContent(file);
    content = replaceType(indentation, packageJson.nodeModuleFormat(), content);
    content = replaceScripts(indentation, packageJson.scripts(), content);
    content = replaceDevDependencies(indentation, packageJson.devDependencies(), content);
    content = replaceDependencies(indentation, packageJson.dependencies(), content);
    content = removeDependencies(indentation, packageJson.dependenciesToRemove(), content);
    content = removeDevDependencies(indentation, packageJson.devDependenciesToRemove(), content);

    content = replacePlaceholders(content, context);
    content = cleanupLineBreaks(indentation, content);

    write(file, content);
  }

  private Path getPackageJsonFile(SeedProjectFolder projectFolder) {
    Path file = projectFolder.filePath("package.json");

    if (Files.notExists(file)) {
      throw new MissingPackageJsonException(projectFolder);
    }

    return file;
  }

  private String replacePlaceholders(String content, SeedModuleContext context) {
    return templateRenderer.render(content, context);
  }

  private String cleanupLineBreaks(Indentation indentation, String content) {
    return content.replaceAll(",(\\s*|[\r\n]*)}", LINE_BREAK + indentation.spaces() + "}");
  }

  private String replaceScripts(Indentation indentation, Scripts scripts, String content) {
    return JsonAction.replace().blockName("scripts").jsonContent(content).indentation(indentation).entries(scriptEntries(scripts)).apply();
  }

  private List<JsonEntry> scriptEntries(Scripts scripts) {
    return scripts
      .stream()
      .map(script -> new JsonEntry(script.key().get(), script.command().get()))
      .toList();
  }

  private String replaceDevDependencies(Indentation indentation, PackageJsonDependencies devDependencies, String content) {
    return JsonAction.replace()
      .blockName("devDependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(devDependencies))
      .apply();
  }

  private String removeDevDependencies(Indentation indentation, PackageNames dependenciesToRemove, String content) {
    return JsonAction.remove()
      .blockName("devDependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependenciesToRemove))
      .apply();
  }

  private String replaceDependencies(Indentation indentation, PackageJsonDependencies dependencies, String content) {
    return JsonAction.replace()
      .blockName("dependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependencies))
      .apply();
  }

  private String removeDependencies(Indentation indentation, PackageNames dependenciesToRemove, String content) {
    return JsonAction.remove()
      .blockName("dependencies")
      .jsonContent(content)
      .indentation(indentation)
      .entries(dependenciesEntries(dependenciesToRemove))
      .apply();
  }

  private String replaceType(Indentation indentation, Optional<NodeModuleFormat> nodeModuleFormat, String content) {
    if (nodeModuleFormat.isEmpty()) {
      return content;
    }
    return JsonAction.replace()
      .blockName("type")
      .jsonContent(content)
      .indentation(indentation)
      .blockValue(nodeModuleFormat.orElseThrow().name().toLowerCase(Locale.ROOT))
      .apply();
  }

  private List<JsonEntry> dependenciesEntries(PackageJsonDependencies dependencies) {
    return dependencies
      .stream()
      .map(dependency -> new JsonEntry(dependency.packageName().get(), getNpmVersion(dependency)))
      .toList();
  }

  private Collection<JsonEntry> dependenciesEntries(PackageNames packageNames) {
    return packageNames
      .stream()
      .map(packageName -> new JsonEntry(packageName.get(), ""))
      .toList();
  }

  private String getNpmVersion(PackageJsonDependency dependency) {
    PackageName packageName = dependency.versionPackageName().orElse(dependency.packageName());
    return nodeVersions.get(packageName.get(), dependency.versionSource()).get();
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
      Files.writeString(file, content, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error writing " + file.toAbsolutePath() + ": " + e.getMessage(), e);
    }
  }

  private static final class JsonAction {

    private final String blockName;
    private final String jsonContent;
    private final Indentation indentation;
    private final Collection<JsonEntry> entries;
    private final JsonActionType action;
    private final String blockValue;

    private JsonAction(JsonActionBuilder builder) {
      Assert.notNull("action", builder.action);
      Assert.notNull("entries", builder.entries);

      blockName = builder.blockName;
      jsonContent = builder.jsonContent;
      indentation = builder.indentation;
      entries = builder.entries;
      action = builder.action;
      blockValue = builder.blockValue;
    }

    private static JsonActionBuilder replace() {
      return new JsonActionBuilder(JsonActionType.REPLACE);
    }

    private static JsonActionBuilder remove() {
      return new JsonActionBuilder(JsonActionType.REMOVE);
    }

    private String handle() {
      if (blockValue != null) {
        return appendNewRootEntry(jsonContent);
      }

      if (entries.isEmpty()) {
        return jsonContent;
      }

      return switch (action) {
        case REPLACE -> replaceEntries();
        case REMOVE -> removeEntries();
      };
    }

    private String replaceEntries() {
      String result = removeExistingEntries();

      Matcher blockMatcher = buildBlockMatcher(result);

      if (blockMatcher.find()) {
        result = appendEntries(blockMatcher);
      } else {
        result = appendNewBlock(result);
      }

      return result;
    }

    private String removeEntries() {
      return removeExistingEntries();
    }

    private String appendEntries(Matcher blockMatcher) {
      return blockMatcher.replaceFirst(match -> {
        StringBuilder result = new StringBuilder().append(match.group(1)).append(LINE_BREAK);

        result.append(entriesBlock(indentation, entries));

        result.append(LINE_END);
        return result.toString();
      });
    }

    private String appendNewRootEntry(String result) {
      String jsonBlock = new StringBuilder()
        .append(LINE_SEPARATOR)
        .append(indentation.spaces())
        .append(QUOTE)
        .append(blockName)
        .append(QUOTE)
        .append(": ")
        .append(QUOTE)
        .append(blockValue)
        .append(QUOTE)
        .toString();

      return result.replaceFirst("(\\s{1,10})}(\\s{1,10})$", jsonBlock + "$1}$2");
    }

    private String appendNewBlock(String result) {
      String jsonBlock = new StringBuilder()
        .append(LINE_SEPARATOR)
        .append(indentation.spaces())
        .append(QUOTE)
        .append(blockName)
        .append(QUOTE)
        .append(": {")
        .append(LINE_BREAK)
        .append(entriesBlock(indentation, entries))
        .append(LINE_BREAK)
        .append(indentation.spaces())
        .append("}")
        .toString();

      return result.replaceFirst("(\\s{1,10})}(\\s{1,10})$", jsonBlock + "$1}$2");
    }

    private Matcher buildBlockMatcher(String result) {
      return Pattern.compile("(\"" + blockName + "\"\\s*:\\s*\\{)").matcher(result);
    }

    @ExcludeFromGeneratedCodeCoverage(reason = "Combiner can't be tested and an implementation detail")
    private String removeExistingEntries() {
      return entries
        .stream()
        .map(toEntryPattern(blockName, indentation))
        .reduce(jsonContent, (json, entryPattern) -> entryPattern.matcher(json).replaceAll("$1$2"), (first, second) -> first);
    }

    private Function<JsonEntry, Pattern> toEntryPattern(String blockName, Indentation indentation) {
      return entry -> {
        String pattern = new StringBuilder()
          .append("(\"")
          .append(blockName)
          .append("\"\\s*:\\s*\\{)([^}]*)(\"")
          .append(entry.key())
          .append("\"\\s*:\\s*\"[^\\r\\n]+[\\r\\n]{1,2}(\\s{")
          .append(indentation.spacesCount() * 2)
          .append("})?)")
          .toString();

        return Pattern.compile(pattern, Pattern.DOTALL);
      };
    }

    private String entriesBlock(Indentation indentation, Collection<JsonEntry> entries) {
      return entries
        .stream()
        .map(entry -> entry.toJson(indentation))
        .collect(Collectors.joining(LINE_SEPARATOR));
    }

    private static final class JsonActionBuilder {

      private String blockName;
      private String jsonContent;
      private Indentation indentation;
      private Collection<JsonEntry> entries = List.of();
      private final JsonActionType action;
      private String blockValue;

      private JsonActionBuilder(JsonActionType action) {
        this.action = action;
      }

      private JsonActionBuilder blockName(String blockName) {
        this.blockName = blockName;

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
        this.entries = SeedCollections.immutable(entries);

        return this;
      }

      private String apply() {
        return new JsonAction(this).handle();
      }

      private JsonActionBuilder blockValue(String blockValue) {
        this.blockValue = blockValue;

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
