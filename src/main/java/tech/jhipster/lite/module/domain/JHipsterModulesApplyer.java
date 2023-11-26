package tech.jhipster.lite.module.domain;

import static tech.jhipster.lite.module.domain.properties.SpringConfigurationFormat.YAML;
import static tech.jhipster.lite.shared.collection.domain.JHipsterCollections.concat;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.git.GitRepository;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacer;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModulesApplyer {

  private final JHipsterModulesRepository modules;
  private final JavaDependenciesVersionsRepository javaVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;
  private final GitRepository git;
  private final GeneratedProjectRepository generatedProject;

  public JHipsterModulesApplyer(
    JHipsterModulesRepository modules,
    JavaDependenciesVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    GitRepository git,
    GeneratedProjectRepository generatedProject
  ) {
    this.modules = modules;
    this.javaVersions = currentVersions;
    this.projectDependencies = projectDependencies;
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

    JHipsterModule module = modules.resources().build(moduleToApply.slug(), moduleToApply.properties());
    //@formatter:off
    var indentProjectFolder = JHipsterModuleChanges
      .builder()
      .projectFolder(module.projectFolder())
      .indentation(module.indentation());

    var operateFile = indentProjectFolder.filesToAdd(module.templatedFiles())
      .filesToMove(module.filesToMove())
      .filesToDelete(module.filesToDelete());

    var replaceAndBuild = operateFile.replacers(buildReplacers(module))
      .javaBuildCommands(buildDependenciesChanges(module).merge(buildPluginsChanges(module)));

    var builder = replaceAndBuild.packageJson(module.packageJson())
      .preActions(module.preActions())
      .postActions(module.postActions())
      .springFactories(module.springFactories());
    //@formatter:on

    JHipsterModuleChanges changes;
    if (moduleToApply.properties().configurationFormat() == YAML) {
      SpringProperties springProperties = concatSpringPropertiesAndBlockProperties(module);
      changes = builder.springYamlProperties(springProperties).springYamlComments(module.springComments());
    } else {
      changes =
        builder
          .springProperties(module.springProperties())
          .springComments(module.springComments())
          .springPropertiesBlockComments(module.springPropertiesBlockComments());
    }

    modules.apply(changes);

    JHipsterModuleApplied moduleApplied = new JHipsterModuleApplied(moduleToApply.slug(), moduleToApply.properties(), Instant.now());
    modules.applied(moduleApplied);

    commitIfNeeded(moduleToApply);

    return moduleApplied;
  }

  private static SpringProperties concatSpringPropertiesAndBlockProperties(JHipsterModule module) {
    List<SpringProperty> blockProperties = module
      .springPropertiesBlockComments()
      .get()
      .stream()
      .flatMap(springPropertiesBlockComment ->
        springPropertiesBlockComment
          .properties()
          .entrySet()
          .stream()
          .map(entry ->
            SpringProperty
              .builder(springPropertiesBlockComment.type())
              .key(entry.getKey())
              .value(entry.getValue())
              .profile(springPropertiesBlockComment.profile())
              .build()
          )
      )
      .toList();
    return new SpringProperties(concat(module.springProperties().properties(), blockProperties));
  }

  private ContentReplacers buildReplacers(JHipsterModule module) {
    List<ContentReplacer> replacers = Stream
      .concat(
        module.mandatoryReplacements().replacers(),
        module.optionalReplacements().buildReplacers(module.projectFolder(), generatedProject)
      )
      .toList();

    return new ContentReplacers(replacers);
  }

  private void commitIfNeeded(JHipsterModuleToApply moduleToApply) {
    if (moduleToApply.commitNeeded()) {
      JHipsterProjectFolder projectFolder = moduleToApply.properties().projectFolder();

      git.init(projectFolder);
      git.commitAll(projectFolder, addCommitMessage(moduleToApply));
    }
  }

  private String addCommitMessage(JHipsterModuleToApply moduleToApply) {
    return new StringBuilder().append("Apply ").append(moduleToApply.slug().get()).append(" module").toString();
  }

  private JavaBuildCommands buildDependenciesChanges(JHipsterModule module) {
    return module.javaDependencies().buildChanges(javaVersions.get(), projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(JHipsterModule module) {
    return module.javaBuildPlugins().buildChanges(javaVersions.get());
  }
}
