package com.seed4j.module.domain;

import static com.seed4j.module.domain.javabuild.JavaBuildTool.GRADLE;
import static com.seed4j.module.domain.javabuild.JavaBuildTool.MAVEN;
import static com.seed4j.module.domain.properties.SpringConfigurationFormat.PROPERTIES;

import com.seed4j.module.domain.file.SeedTemplatedFile;
import com.seed4j.module.domain.file.SeedTemplatedFiles;
import com.seed4j.module.domain.git.GitRepository;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersionsRepository;
import com.seed4j.module.domain.javadependency.ProjectJavaDependenciesRepository;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.module.domain.replacement.ContentReplacer;
import com.seed4j.module.domain.replacement.ContentReplacers;
import com.seed4j.module.domain.startupcommand.DockerComposeStartupCommandLine;
import com.seed4j.module.domain.startupcommand.GradleStartupCommandLine;
import com.seed4j.module.domain.startupcommand.MavenStartupCommandLine;
import com.seed4j.module.domain.startupcommand.SeedStartupCommand;
import com.seed4j.module.domain.startupcommand.SeedStartupCommands;
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
public class SeedModulesApplyer {

  private static final Logger log = LoggerFactory.getLogger(SeedModulesApplyer.class);

  private final SeedModulesRepository modules;
  private final JavaDependenciesVersionsRepository javaVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;
  private final ProjectJavaBuildToolRepository javaBuildTools;
  private final GitRepository git;
  private final GeneratedProjectRepository generatedProject;

  public SeedModulesApplyer(
    SeedModulesRepository modules,
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

  public Collection<SeedModuleApplied> apply(SeedModulesToApply modulesToApply) {
    Assert.notNull("modulesToApply", modulesToApply);

    return modules.landscape().sort(modulesToApply.slugs()).stream().map(toModuleToApply(modulesToApply)).map(this::apply).toList();
  }

  private Function<SeedModuleSlug, SeedModuleToApply> toModuleToApply(SeedModulesToApply modulesToApply) {
    return slug -> new SeedModuleToApply(slug, modulesToApply.properties());
  }

  public SeedModuleApplied apply(SeedModuleToApply moduleToApply) {
    Assert.notNull("moduleToApply", moduleToApply);

    log.info("Apply module: {}", moduleToApply.slug());

    SeedModule module = modules.resources().build(moduleToApply.slug(), moduleToApply.properties());
    // @formatter:off
    var builder = SeedModuleChanges
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

    SeedModuleChanges changes;
    if (moduleToApply.properties().springConfigurationFormat() == PROPERTIES) {
      changes = builder.springProperties(module.springProperties()).springComments(module.springComments());
    } else {
      changes = builder.springYamlProperties(module.springProperties()).springYamlComments(module.springComments());
    }

    modules.apply(changes);

    SeedModuleApplied moduleApplied = new SeedModuleApplied(moduleToApply.slug(), moduleToApply.properties(), Instant.now());
    modules.applied(moduleApplied);

    commitIfNeeded(moduleToApply);

    return moduleApplied;
  }

  private SeedModuleContext contextWithJavaBuildTool(SeedModule module) {
    return detectedJavaBuildTool(module)
      .map(javaBuildTool -> module.context().withJavaBuildTool(javaBuildTool))
      .orElse(module.context());
  }

  private Optional<JavaBuildTool> detectedJavaBuildTool(SeedModule module) {
    return javaBuildTools.detect(module.projectFolder()).or(() -> javaBuildTools.detect(module.files()));
  }

  private SeedTemplatedFiles buildTemplatedFiles(SeedModule module) {
    SeedModuleContext context = contextWithJavaBuildTool(module);
    List<SeedTemplatedFile> templatedFiles = module
      .filesToAdd()
      .stream()
      .map(file -> SeedTemplatedFile.builder().file(file).context(context).build())
      .toList();

    return new SeedTemplatedFiles(templatedFiles);
  }

  private SeedStartupCommands buildStartupCommands(SeedModule module) {
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
    return new SeedStartupCommands(filteredCommands);
  }

  private static Predicate<SeedStartupCommand> isStartupCommandCompatibleWith(JavaBuildTool javaBuildTool) {
    return startupCommand ->
      switch (startupCommand) {
        case MavenStartupCommandLine __ -> javaBuildTool == MAVEN;
        case GradleStartupCommandLine __ -> javaBuildTool == GRADLE;
        case DockerComposeStartupCommandLine __ -> true;
      };
  }

  private ContentReplacers buildReplacers(SeedModule module) {
    List<ContentReplacer> replacers = Stream.concat(
      module.mandatoryReplacements().replacers(),
      module.optionalReplacements().buildReplacers(module.projectFolder(), generatedProject)
    ).toList();

    return new ContentReplacers(replacers);
  }

  private void commitIfNeeded(SeedModuleToApply moduleToApply) {
    if (moduleToApply.commitNeeded()) {
      SeedProjectFolder projectFolder = moduleToApply.properties().projectFolder();

      git.init(projectFolder);
      git.commitAll(projectFolder, commitMessage(moduleToApply));
    }
  }

  private String commitMessage(SeedModuleToApply moduleToApply) {
    return "Apply module: %s".formatted(moduleToApply.slug().get());
  }

  private JavaBuildCommands buildGradlePluginsChanges(SeedModule module) {
    return module.gradlePlugins().buildChanges(javaVersions.get());
  }

  private JavaBuildCommands buildDependenciesChanges(SeedModule module) {
    return module.javaDependencies().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPropertiesChanges(SeedModule module) {
    return module.javaBuildProperties().buildChanges();
  }

  private JavaBuildCommands buildProfilesChanges(SeedModule module) {
    return module.javaBuildProfiles().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(SeedModule module) {
    return module.mavenPlugins().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildGradleConfigurationsChanges(SeedModule module) {
    return module.gradleConfigurations().buildChanges();
  }

  private JavaBuildCommands buildMavenBuildExtensionsChanges(SeedModule module) {
    return module.mavenBuildExtensions().buildChanges(javaVersions.get());
  }
}
