package com.seed4j.module.domain;

import static com.seed4j.module.domain.javabuild.JavaBuildTool.GRADLE;
import static com.seed4j.module.domain.javabuild.JavaBuildTool.MAVEN;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.PROPERTIES;

import com.seed4j.module.domain.file.Seed4JTemplatedFile;
import com.seed4j.module.domain.file.Seed4JTemplatedFiles;
import com.seed4j.module.domain.git.GitRepository;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.domain.replacement.ContentReplacer;
import com.seed4j.module.domain.replacement.ContentReplacers;
import com.seed4j.module.domain.startupcommand.DockerComposeStartupCommandLine;
import com.seed4j.module.domain.startupcommand.GradleStartupCommandLine;
import com.seed4j.module.domain.startupcommand.MavenStartupCommandLine;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommand;
import com.seed4j.module.domain.startupcommand.Seed4JStartupCommands;
import com.seed4j.shared.error.domain.Assert;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S6539")
public class Seed4JModulesApplyer {

  private static final Logger log = LoggerFactory.getLogger(Seed4JModulesApplyer.class);

  private final Seed4JModulesRepository modules;
  private final JavaDependenciesVersionsRepository javaVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;
  private final ProjectJavaBuildToolRepository javaBuildTools;
  private final GitRepository git;
  private final GeneratedProjectRepository generatedProject;

  public Seed4JModulesApplyer(
    Seed4JModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    ProjectJavaBuildToolRepository javaBuildTools,
    GitRepository git,
    GeneratedProjectRepository generatedProject
  ) {
    this.modules = modules;
    this.javaVersions = currentVersions;
    this.projectDependencies = projectDependencies;
    this.javaBuildTools = javaBuildTools;
    this.git = git;
    this.generatedProject = generatedProject;
  }

  public Collection<Seed4JModuleApplied> apply(Seed4JModulesToApply modulesToApply) {
    Assert.notNull("modulesToApply", modulesToApply);

    return modules.landscape().sort(modulesToApply.slugs()).stream().map(toModuleToApply(modulesToApply)).map(this::apply).toList();
  }

  private Function<Seed4JModuleSlug, Seed4JModuleToApply> toModuleToApply(Seed4JModulesToApply modulesToApply) {
    return slug -> new Seed4JModuleToApply(slug, modulesToApply.properties());
  }

  public Seed4JModuleApplied apply(Seed4JModuleToApply moduleToApply) {
    Assert.notNull("moduleToApply", moduleToApply);

    log.info("Apply module: {}", moduleToApply.slug());

    Seed4JModule module = modules.resources().build(moduleToApply.slug(), moduleToApply.properties());
    // @formatter:off
    var builder = Seed4JModuleChanges
      .builder()
      .context(contextWithJavaBuildTool(module))
      .projectFolder(module.projectFolder())
      .indentation(module.indentation())
      .filesToAdd(buildTemplatedFiles(module))
      .filesToMove(module.filesToMove())
      .filesToDelete(module.filesToDelete())
      .replacers(buildReplacers(module))
      .startupCommands(buildStartupCommands(module))
      .javaBuildCommands(
        buildDependenciesChanges(module)
          .merge(buildPluginsChanges(module))
          .merge(buildMavenBuildExtensionsChanges(module))
          .merge(buildPropertiesChanges(module))
          .merge(buildProfilesChanges(module))
          .merge(buildGradlePluginsChanges(module))
          .merge(buildGradleConfigurationsChanges(module))
      )
      .packageJson(module.packageJson())
      .gitIgnore(module.gitIgnore())
      .dockerComposeFile(module.dockerComposeFile())
      .preActions(module.preActions())
      .postActions(module.postActions())
      .springFactories(module.springFactories());
    // @formatter:on

    Seed4JModuleChanges changes;
    if (moduleToApply.properties().springConfigurationFormat() == PROPERTIES) {
      changes = builder.springProperties(module.springProperties()).springComments(module.springComments());
    } else {
      changes = builder.springYamlProperties(module.springProperties()).springYamlComments(module.springComments());
    }

    modules.apply(changes);

    Seed4JModuleApplied moduleApplied = new Seed4JModuleApplied(moduleToApply.slug(), moduleToApply.properties(), Instant.now());
    modules.applied(moduleApplied);

    commitIfNeeded(moduleToApply);

    return moduleApplied;
  }

  private Seed4JModuleContext contextWithJavaBuildTool(Seed4JModule module) {
    return detectedJavaBuildTool(module)
      .map(javaBuildTool -> module.context().withJavaBuildTool(javaBuildTool))
      .orElse(module.context());
  }

  private Optional<JavaBuildTool> detectedJavaBuildTool(Seed4JModule module) {
    return javaBuildTools.detect(module.projectFolder()).or(() -> javaBuildTools.detect(module.files()));
  }

  private Seed4JTemplatedFiles buildTemplatedFiles(Seed4JModule module) {
    Seed4JModuleContext context = contextWithJavaBuildTool(module);
    List<Seed4JTemplatedFile> templatedFiles = module
      .filesToAdd()
      .stream()
      .map(file -> Seed4JTemplatedFile.builder().file(file).context(context).build())
      .toList();

    return new Seed4JTemplatedFiles(templatedFiles);
  }

  private Seed4JStartupCommands buildStartupCommands(Seed4JModule module) {
    Optional<JavaBuildTool> javaBuildTool = detectedJavaBuildTool(module);
    if (javaBuildTool.isEmpty()) {
      return module.startupCommands();
    }
    var filteredCommands = module
      .startupCommands()
      .get()
      .stream()
      .filter(isStartupCommandCompatibleWith(javaBuildTool.orElseThrow()))
      .toList();
    return new Seed4JStartupCommands(filteredCommands);
  }

  private static Predicate<Seed4JStartupCommand> isStartupCommandCompatibleWith(JavaBuildTool javaBuildTool) {
    return startupCommand ->
      switch (startupCommand) {
        case MavenStartupCommandLine __ -> javaBuildTool == MAVEN;
        case GradleStartupCommandLine __ -> javaBuildTool == GRADLE;
        case DockerComposeStartupCommandLine __ -> true;
      };
  }

  private ContentReplacers buildReplacers(Seed4JModule module) {
    List<ContentReplacer> replacers = Stream.concat(
      module.mandatoryReplacements().replacers(),
      module.optionalReplacements().buildReplacers(module.projectFolder(), generatedProject)
    ).toList();

    return new ContentReplacers(replacers);
  }

  private void commitIfNeeded(Seed4JModuleToApply moduleToApply) {
    if (moduleToApply.commitNeeded()) {
      Seed4JProjectFolder projectFolder = moduleToApply.properties().projectFolder();

      git.init(projectFolder);
      git.commitAll(projectFolder, commitMessage(moduleToApply));
    }
  }

  private String commitMessage(Seed4JModuleToApply moduleToApply) {
    return "Apply module: %s".formatted(moduleToApply.slug().get());
  }

  private JavaBuildCommands buildGradlePluginsChanges(Seed4JModule module) {
    return module.gradlePlugins().buildChanges(javaVersions.get());
  }

  private JavaBuildCommands buildDependenciesChanges(Seed4JModule module) {
    return module.javaDependencies().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPropertiesChanges(Seed4JModule module) {
    return module.javaBuildProperties().buildChanges();
  }

  private JavaBuildCommands buildProfilesChanges(Seed4JModule module) {
    return module.javaBuildProfiles().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(Seed4JModule module) {
    return module.mavenPlugins().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildGradleConfigurationsChanges(Seed4JModule module) {
    return module.gradleConfigurations().buildChanges();
  }

  private JavaBuildCommands buildMavenBuildExtensionsChanges(Seed4JModule module) {
    return module.mavenBuildExtensions().buildChanges(javaVersions.get());
  }
}
