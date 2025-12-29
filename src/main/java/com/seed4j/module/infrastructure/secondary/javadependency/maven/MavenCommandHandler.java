package com.seed4j.module.infrastructure.secondary.javadependency.maven;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.buildproperties.BuildProperty;
import com.seed4j.module.domain.buildproperties.PropertyKey;
import com.seed4j.module.domain.buildproperties.PropertyValue;
import com.seed4j.module.domain.javabuild.MavenBuildExtension;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.AddDirectMavenPlugin;
import com.seed4j.module.domain.javabuild.command.AddGradleConfiguration;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin;
import com.seed4j.module.domain.javabuild.command.AddGradleTasksTestInstruction;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.AddJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.AddMavenBuildExtension;
import com.seed4j.module.domain.javabuild.command.AddMavenPlugin;
import com.seed4j.module.domain.javabuild.command.AddMavenPluginManagement;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.javabuildprofile.BuildProfileActivation;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyClassifier;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.mavenplugin.MavenBuildPhase;
import com.seed4j.module.domain.mavenplugin.MavenPluginConfiguration;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecution;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecutionGoal;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecutionId;
import com.seed4j.module.infrastructure.secondary.javadependency.JavaDependenciesCommandHandler;
import com.seed4j.shared.enumeration.domain.Enums;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.error.domain.GeneratorException;
import io.fabric8.maven.Maven;
import io.fabric8.maven.XMLFormat;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.maven.model.Activation;
import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Extension;
import org.apache.maven.model.Model;
import org.apache.maven.model.ModelBase;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.Profile;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jspecify.annotations.Nullable;

public class MavenCommandHandler implements JavaDependenciesCommandHandler {

  private static final String COMMAND = "command";

  private final XMLFormat xmlFormat;
  private final Path pomPath;
  private final Model pomModel;

  public MavenCommandHandler(Indentation indentation, Path pomPath) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("pomPath", pomPath);

    this.xmlFormat = XMLFormat.builder(XMLFormat.DEFAULT).indent(indentation.spaces()).build();
    this.pomPath = pomPath;
    pomModel = readModel(pomPath);
  }

  private Model readModel(Path pomPath) {
    try {
      return Maven.readModel(pomPath);
    } catch (UncheckedIOException e) {
      throw GeneratorException.technicalError("Error reading pom: " + e.getMessage(), e);
    }
  }

  @Override
  public void handle(SetVersion command) {
    Assert.notNull(COMMAND, command);

    BuildProperty property = new BuildProperty(new PropertyKey(command.property()), new PropertyValue(command.dependencyVersion()));
    handle(new SetBuildProperty(property));
  }

  @Override
  public void handle(SetBuildProperty command) {
    Assert.notNull(COMMAND, command);

    ModelBase model = command.buildProfile().map(this::findProfile).map(ModelBase.class::cast).orElse(pomModel);
    model.addProperty(command.property().key().get(), command.property().value().get());

    writePom();
  }

  private Profile findProfile(BuildProfileId buildProfileId) {
    return pomModel
      .getProfiles()
      .stream()
      .filter(profileMatch(buildProfileId))
      .findFirst()
      .orElseThrow(() -> new MissingMavenProfileException(buildProfileId));
  }

  private static Predicate<Profile> profileMatch(BuildProfileId buildProfileId) {
    return profile -> profile.getId().equals(buildProfileId.value());
  }

  @Override
  public void handle(AddJavaBuildProfile command) {
    Assert.notNull(COMMAND, command);

    List<Profile> profiles = pomModel.getProfiles();
    if (profiles.stream().noneMatch(profileMatch(command.buildProfileId()))) {
      Profile profile = toMavenProfile(command);
      pomModel.addProfile(profile);
    }

    writePom();
  }

  private static Profile toMavenProfile(AddJavaBuildProfile command) {
    Profile profile = new Profile();
    profile.setId(command.buildProfileId().value());
    command.activation().ifPresent(activation -> profile.setActivation(toMavenActivation(activation)));

    return profile;
  }

  private static Activation toMavenActivation(BuildProfileActivation activation) {
    Activation mavenActivation = new Activation();
    activation.activeByDefault().ifPresent(mavenActivation::setActiveByDefault);
    return mavenActivation;
  }

  @Override
  public void handle(RemoveJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    DependencyManagement dependencyManagement = command
      .buildProfile()
      .map(this::findProfile)
      .map(Profile::getDependencyManagement)
      .orElse(pomModel.getDependencyManagement());
    if (dependencyManagement != null) {
      removeDependencyFrom(command.dependency(), dependencyManagement.getDependencies())
        .stream()
        .map(Dependency::getVersion)
        .forEach(this::removeUnusedVersionProperty);
    }
  }

  @Override
  public void handle(RemoveDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    List<Dependency> dependencies = command
      .buildProfile()
      .map(this::findProfile)
      .map(Profile::getDependencies)
      .orElse(pomModel.getDependencies());

    removeDependencyFrom(command.dependency(), dependencies)
      .stream()
      .map(Dependency::getVersion)
      .forEach(this::removeUnusedVersionProperty);
  }

  private List<Dependency> removeDependencyFrom(DependencyId dependency, List<Dependency> dependencies) {
    List<Dependency> dependenciesToRemove = dependencies.stream().filter(matchesDependency(dependency)).toList();

    if (!dependenciesToRemove.isEmpty()) {
      dependencies.removeAll(dependenciesToRemove);
      writePom();
    }

    return dependenciesToRemove;
  }

  private Predicate<Dependency> matchesDependency(DependencyId dependency) {
    return mavenDependency -> {
      boolean sameGroupId = mavenDependency.getGroupId().equals(dependency.groupId().get());
      boolean sameArtifactId = mavenDependency.getArtifactId().equals(dependency.artifactId().get());
      return sameGroupId && sameArtifactId;
    };
  }

  private void removeUnusedVersionProperty(String version) {
    extractVersionPropertyKey(version)
      .filter(this::versionPropertyUnused)
      .ifPresent(propertyKey -> {
        pomModel.getProperties().remove(propertyKey);
        writePom();
      });
  }

  private Stream<Dependency> allDependencies() {
    return Stream.of(
      pomModel.getDependencies().stream(),
      pomModel
        .getProfiles()
        .stream()
        .flatMap(profile -> profile.getDependencies().stream()),
      dependenciesFrom(pomModel.getDependencyManagement()),
      pomModel
        .getProfiles()
        .stream()
        .flatMap(profile -> dependenciesFrom(profile.getDependencyManagement()))
    ).flatMap(Function.identity());
  }

  private Stream<Dependency> dependenciesFrom(@Nullable DependencyManagement dependencyManagement) {
    return dependencyManagement != null ? dependencyManagement.getDependencies().stream() : Stream.empty();
  }

  private Optional<String> extractVersionPropertyKey(String version) {
    return VersionSlug.of(version).map(VersionSlug::propertyName);
  }

  private boolean versionPropertyUnused(String versionPropertyKey) {
    return allDependencies()
      .map(Dependency::getVersion)
      .flatMap(version -> extractVersionPropertyKey(version).stream())
      .noneMatch(versionPropertyKey::equals);
  }

  @Override
  public void handle(AddMavenBuildExtension command) {
    Assert.notNull(COMMAND, command);

    projectBuild().addExtension(toMavenExtension(command.buildExtension()));

    writePom();
  }

  private Build projectBuild() {
    if (pomModel.getBuild() == null) {
      pomModel.setBuild(new Build());
    }
    return pomModel.getBuild();
  }

  private static Extension toMavenExtension(MavenBuildExtension mavenBuildExtension) {
    Extension extension = new Extension();
    extension.setArtifactId(mavenBuildExtension.artifactId().get());
    extension.setGroupId(mavenBuildExtension.groupId().get());
    mavenBuildExtension.versionSlug().map(VersionSlug::mavenVariable).ifPresent(extension::setVersion);
    return extension;
  }

  @Override
  public void handle(AddJavaDependencyManagement command) {
    Assert.notNull(COMMAND, command);

    DependencyManagement dependencyManagement = command
      .buildProfile()
      .map(this::findProfile)
      .map(this::dependencyManagement)
      .orElse(dependencyManagement());
    addDependencyTo(command.dependency(), dependencyManagement.getDependencies());
  }

  private DependencyManagement dependencyManagement(Profile mavenProfile) {
    return Optional.ofNullable(mavenProfile.getDependencyManagement())
      .or(() -> Optional.of(new DependencyManagement()))
      .map(dependencyManagement -> {
        mavenProfile.setDependencyManagement(dependencyManagement);
        return dependencyManagement;
      })
      .orElseThrow();
  }

  private DependencyManagement dependencyManagement() {
    if (pomModel.getDependencyManagement() == null) {
      pomModel.setDependencyManagement(new DependencyManagement());
    }
    return pomModel.getDependencyManagement();
  }

  @Override
  public void handle(AddDirectJavaDependency command) {
    Assert.notNull(COMMAND, command);

    List<Dependency> dependencies = command
      .buildProfile()
      .map(this::findProfile)
      .map(Profile::getDependencies)
      .orElse(pomModel.getDependencies());
    addDependencyTo(command.dependency(), dependencies);
  }

  private void addDependencyTo(JavaDependency dependency, List<Dependency> dependencies) {
    if (dependency.scope() == JavaDependencyScope.TEST) {
      dependencies.add(toMavenDependency(dependency));
    } else {
      Dependency mavenDependency = toMavenDependency(dependency);
      insertDependencyBeforeFirstTestDependency(mavenDependency, dependencies);
    }

    writePom();
  }

  private void insertDependencyBeforeFirstTestDependency(Dependency mavenDependency, List<Dependency> dependencies) {
    List<Dependency> nonTestDependencies = dependencies
      .stream()
      .filter(dependency -> !MavenScope.TEST.key().equals(dependency.getScope()))
      .toList();
    if (nonTestDependencies.isEmpty()) {
      dependencies.add(mavenDependency);
    } else {
      dependencies.add(dependencies.indexOf(nonTestDependencies.getLast()) + 1, mavenDependency);
    }
  }

  private Dependency toMavenDependency(JavaDependency javaDependency) {
    Dependency mavenDependency = new Dependency();
    mavenDependency.setGroupId(javaDependency.id().groupId().get());
    mavenDependency.setArtifactId(javaDependency.id().artifactId().get());
    javaDependency.version().map(VersionSlug::mavenVariable).ifPresent(mavenDependency::setVersion);
    javaDependency.classifier().map(JavaDependencyClassifier::get).ifPresent(mavenDependency::setClassifier);
    javaDependency
      .type()
      .map(type -> Enums.map(type, MavenType.class))
      .map(MavenType::key)
      .ifPresent(mavenDependency::setType);
    javaDependency.exclusions().stream().map(toMavenExclusion()).forEach(mavenDependency::addExclusion);

    if (javaDependency.scope() != JavaDependencyScope.COMPILE) {
      mavenDependency.setScope(MavenScope.from(javaDependency.scope()).key());
    }
    if (javaDependency.optional()) {
      mavenDependency.setOptional(true);
    }

    return mavenDependency;
  }

  private Function<DependencyId, Exclusion> toMavenExclusion() {
    return dependencyId -> {
      Exclusion mavenExclusion = new Exclusion();
      mavenExclusion.setGroupId(dependencyId.groupId().get());
      mavenExclusion.setArtifactId(dependencyId.artifactId().get());
      return mavenExclusion;
    };
  }

  @Override
  public void handle(AddMavenPluginManagement command) {
    Assert.notNull(COMMAND, command);

    command.pluginVersion().ifPresent(version -> handle(new SetVersion(version)));
    command.dependenciesVersions().forEach(version -> handle(new SetVersion(version)));

    PluginManagement pluginManagement = command
      .buildProfile()
      .map(this::findProfile)
      .map(this::pluginManagement)
      .orElse(pluginManagement());
    replaceOrAddPlugin(pluginManagement.getPlugins(), toMavenPlugin(command));

    writePom();
  }

  private PluginManagement pluginManagement(Profile mavenProfile) {
    if (profileBuild(mavenProfile).getPluginManagement() == null) {
      profileBuild(mavenProfile).setPluginManagement(new PluginManagement());
    }
    return profileBuild(mavenProfile).getPluginManagement();
  }

  private PluginManagement pluginManagement() {
    if (projectBuild().getPluginManagement() == null) {
      projectBuild().setPluginManagement(new PluginManagement());
    }
    return projectBuild().getPluginManagement();
  }

  @Override
  public void handle(AddDirectMavenPlugin command) {
    Assert.notNull(COMMAND, command);

    command.pluginVersion().ifPresent(version -> handle(new SetVersion(version)));
    command.dependenciesVersions().forEach(version -> handle(new SetVersion(version)));

    BuildBase build = command.buildProfile().map(this::findProfile).map(this::profileBuild).orElse(projectBuild());
    replaceOrAddPlugin(build.getPlugins(), toMavenPlugin(command));

    writePom();
  }

  private static void replaceOrAddPlugin(List<Plugin> plugins, Plugin newPlugin) {
    for (int i = 0; i < plugins.size(); i++) {
      Plugin existingPlugin = plugins.get(i);
      if (existingPlugin.getGroupId().equals(newPlugin.getGroupId()) && existingPlugin.getArtifactId().equals(newPlugin.getArtifactId())) {
        plugins.set(i, newPlugin);
        return;
      }
    }
    plugins.add(newPlugin);
  }

  private BuildBase profileBuild(Profile mavenProfile) {
    if (mavenProfile.getBuild() == null) {
      mavenProfile.setBuild(new Build());
    }
    return mavenProfile.getBuild();
  }

  @Override
  public void handle(AddGradlePlugin command) {
    // Gradle commands are ignored
  }

  @Override
  public void handle(AddGradleConfiguration command) {
    // Gradle commands are ignored
  }

  @Override
  public void handle(AddGradleTasksTestInstruction command) {
    // Gradle commands are ignored
  }

  private Plugin toMavenPlugin(AddMavenPlugin command) {
    Plugin mavenPlugin = new Plugin();
    mavenPlugin.setArtifactId(command.dependencyId().artifactId().get());
    mavenPlugin.setGroupId(command.dependencyId().groupId().get());
    command.versionSlug().map(VersionSlug::mavenVariable).ifPresent(mavenPlugin::setVersion);
    command.configuration().map(toMavenConfiguration()).ifPresent(mavenPlugin::setConfiguration);
    command.executions().stream().map(toMavenExecution()).forEach(mavenPlugin::addExecution);
    command.dependencies().stream().map(this::toMavenDependency).forEach(mavenPlugin::addDependency);
    return mavenPlugin;
  }

  private Function<MavenPluginExecution, PluginExecution> toMavenExecution() {
    return execution -> {
      PluginExecution mavenExecution = new PluginExecution();
      execution.id().map(MavenPluginExecutionId::get).ifPresent(mavenExecution::setId);
      execution.phase().map(MavenBuildPhase::mavenKey).ifPresent(mavenExecution::setPhase);
      execution.goals().stream().map(MavenPluginExecutionGoal::get).forEach(mavenExecution::addGoal);
      execution.configuration().map(toMavenConfiguration()).ifPresent(mavenExecution::setConfiguration);
      return mavenExecution;
    };
  }

  private Function<MavenPluginConfiguration, Xpp3Dom> toMavenConfiguration() {
    return configuration -> {
      try (Reader reader = Reader.of("<configuration>" + configuration.get() + "</configuration>")) {
        return Xpp3DomBuilder.build(reader);
      } catch (XmlPullParserException | IOException e) {
        throw new MalformedAdditionalInformationException(e);
      }
    };
  }

  private void writePom() {
    Maven.writeModel(pomModel, pomPath, xmlFormat);
  }
}
