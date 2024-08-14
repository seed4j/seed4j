package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.javabuild.JavaBuildTool.*;
import static tech.jhipster.lite.module.domain.properties.SpringConfigurationFormat.*;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFile;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFiles;
import tech.jhipster.lite.module.domain.git.GitRepository;
import tech.jhipster.lite.module.domain.javabuild.JavaBuildTool;
import tech.jhipster.lite.module.domain.javabuild.ProjectJavaBuildToolRepository;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacer;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.module.domain.startupcommand.DockerComposeStartupCommandLine;
import tech.jhipster.lite.module.domain.startupcommand.GradleStartupCommandLine;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterStartupCommand;
import tech.jhipster.lite.module.domain.startupcommand.JHipsterStartupCommands;
import tech.jhipster.lite.module.domain.startupcommand.MavenStartupCommandLine;
import tech.jhipster.lite.shared.error.domain.Assert;

@SuppressWarnings("java:S6539")
public class JHipsterModulesApplyer {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesApplyer.class);

  private final JHipsterModulesRepository modules;
  private final JavaDependenciesVersionsRepository javaVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;
  private final ProjectJavaBuildToolRepository javaBuildTools;
  private final GitRepository git;
  private final GeneratedProjectRepository generatedProject;

  public JHipsterModulesApplyer(
    JHipsterModulesRepository modules,
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

  public Collection<JHipsterModuleApplied> apply(JHipsterModulesToApply modulesToApply) {
    Assert.notNull("modulesToApply", modulesToApply);

    return modules.landscape().sort(modulesToApply.slugs()).stream().map(toModuleToApply(modulesToApply)).map(this::apply).toList();
  }

  private Function<JHipsterModuleSlug, JHipsterModuleToApply> toModuleToApply(JHipsterModulesToApply modulesToApply) {
    return slug -> new JHipsterModuleToApply(slug, modulesToApply.properties());
  }

  public JHipsterModuleApplied apply(JHipsterModuleToApply moduleToApply) {
    Assert.notNull("moduleToApply", moduleToApply);

    log.info("Apply module: {}", moduleToApply.slug());

    JHipsterModule module = modules.resources().build(moduleToApply.slug(), moduleToApply.properties());
    //@formatter:off
    var builder = JHipsterModuleChanges
      .builder()
      .context(contextWithJavaBuildTool(module))
      .projectFolder(module.projectFolder())
      .indentation(module.indentation())
      .filesToAdd(buildTemplatedFiles(module))
      .filesToMove(module.filesToMove())
      .filesToDelete(module.filesToDelete())
      .replacers(buildReplacers(module))
      .startupCommands(buildStartupCommands(module))
      .sonarAnalysisCommands(buildSonarAnalysisCommands(module))
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
      .preActions(module.preActions())
      .postActions(module.postActions())
      .springFactories(module.springFactories());
    //@formatter:on

    JHipsterModuleChanges changes;
    if (moduleToApply.properties().springConfigurationFormat() == PROPERTIES) {
      changes = builder.springProperties(module.springProperties()).springComments(module.springComments());
    } else {
      changes = builder.springYamlProperties(module.springProperties()).springYamlComments(module.springComments());
    }

    modules.apply(changes);

    JHipsterModuleApplied moduleApplied = new JHipsterModuleApplied(moduleToApply.slug(), moduleToApply.properties(), Instant.now());
    modules.applied(moduleApplied);

    commitIfNeeded(moduleToApply);

    return moduleApplied;
  }

  private JHipsterModuleContext contextWithJavaBuildTool(JHipsterModule module) {
    return detectedJavaBuildTool(module).map(javaBuildTool -> module.context().withJavaBuildTool(javaBuildTool)).orElse(module.context());
  }

  private Optional<JavaBuildTool> detectedJavaBuildTool(JHipsterModule module) {
    return javaBuildTools.detect(module.projectFolder()).or(() -> javaBuildTools.detect(module.files()));
  }

  private JHipsterTemplatedFiles buildTemplatedFiles(JHipsterModule module) {
    JHipsterModuleContext context = contextWithJavaBuildTool(module);
    List<JHipsterTemplatedFile> templatedFiles = module
      .filesToAdd()
      .stream()
      .map(file -> JHipsterTemplatedFile.builder().file(file).context(context).build())
      .toList();

    return new JHipsterTemplatedFiles(templatedFiles);
  }

  private JHipsterStartupCommands buildStartupCommands(JHipsterModule module) {
    Optional<JavaBuildTool> javaBuildTool = detectedJavaBuildTool(module);
    if (javaBuildTool.isEmpty()) {
      return module.startupCommands();
    }
    var filteredCommands = module.startupCommands().get().stream().filter(isStartupCommandCompatibleWith(javaBuildTool.get())).toList();
    return new JHipsterStartupCommands(filteredCommands);
  }

  private JHipsterStartupCommands buildSonarAnalysisCommands(JHipsterModule module) {
    Optional<JavaBuildTool> javaBuildTool = detectedJavaBuildTool(module);
    if (javaBuildTool.isEmpty()) {
      return module.sonarAnalysisCommands();
    }
    var filteredCommands = module
      .sonarAnalysisCommands()
      .get()
      .stream()
      .filter(isStartupCommandCompatibleWith(javaBuildTool.get()))
      .toList();
    return new JHipsterStartupCommands(filteredCommands);
  }

  private static Predicate<JHipsterStartupCommand> isStartupCommandCompatibleWith(JavaBuildTool javaBuildTool) {
    return startupCommand ->
      switch (startupCommand) {
        case MavenStartupCommandLine __ -> javaBuildTool == MAVEN;
        case GradleStartupCommandLine __ -> javaBuildTool == GRADLE;
        case DockerComposeStartupCommandLine __ -> true;
      };
  }

  private ContentReplacers buildReplacers(JHipsterModule module) {
    List<ContentReplacer> replacers = Stream.concat(
      module.mandatoryReplacements().replacers(),
      module.optionalReplacements().buildReplacers(module.projectFolder(), generatedProject)
    ).toList();

    return new ContentReplacers(replacers);
  }

  private void commitIfNeeded(JHipsterModuleToApply moduleToApply) {
    if (moduleToApply.commitNeeded()) {
      JHipsterProjectFolder projectFolder = moduleToApply.properties().projectFolder();

      git.init(projectFolder);
      git.commitAll(projectFolder, commitMessage(moduleToApply));
    }
  }

  private String commitMessage(JHipsterModuleToApply moduleToApply) {
    return "Apply module: %s".formatted(moduleToApply.slug().get());
  }

  private JavaBuildCommands buildGradlePluginsChanges(JHipsterModule module) {
    return module.gradlePlugins().buildChanges(javaVersions.get());
  }

  private JavaBuildCommands buildDependenciesChanges(JHipsterModule module) {
    return module.javaDependencies().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPropertiesChanges(JHipsterModule module) {
    return module.javaBuildProperties().buildChanges();
  }

  private JavaBuildCommands buildProfilesChanges(JHipsterModule module) {
    return module.javaBuildProfiles().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(JHipsterModule module) {
    return module.mavenPlugins().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildGradleConfigurationsChanges(JHipsterModule module) {
    return module.gradleConfigurations().buildChanges();
  }

  private JavaBuildCommands buildMavenBuildExtensionsChanges(JHipsterModule module) {
    return module.mavenBuildExtensions().buildChanges(javaVersions.get());
  }
}
