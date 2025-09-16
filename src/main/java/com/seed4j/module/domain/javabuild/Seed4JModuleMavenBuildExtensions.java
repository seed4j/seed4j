package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.javabuild.command.AddMavenBuildExtension;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Seed4JModuleMavenBuildExtensions {

  private final Collection<MavenBuildExtension> buildExtensions;

  public Seed4JModuleMavenBuildExtensions(Seed4JModuleMavenBuildExtensionsBuilder builder) {
    this.buildExtensions = builder.buildExtensions;
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions) {
    Assert.notNull("versions", versions);

    List<JavaBuildCommand> addBuildExtensions = this.buildExtensions.stream()
      .flatMap(toCommands(versions, AddMavenBuildExtension::new))
      .toList();

    return new JavaBuildCommands(addBuildExtensions);
  }

  private static Function<MavenBuildExtension, Stream<JavaBuildCommand>> toCommands(
    JavaDependenciesVersions versions,
    Function<MavenBuildExtension, JavaBuildCommand> addCommandFactory
  ) {
    return extension -> {
      JavaBuildCommand addBuildExtensionCommand = addCommandFactory.apply(extension);

      return extension
        .versionSlug()
        .map(version -> Stream.of(new SetVersion(versions.get(version)), addBuildExtensionCommand))
        .orElseGet(() -> Stream.of(addBuildExtensionCommand));
    };
  }

  public static Seed4JModuleMavenBuildExtensionsBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleMavenBuildExtensionsBuilder(module);
  }

  public static final class Seed4JModuleMavenBuildExtensionsBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<MavenBuildExtension> buildExtensions = new ArrayList<>();

    private Seed4JModuleMavenBuildExtensionsBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleMavenBuildExtensionsBuilder addExtension(MavenBuildExtension buildExtension) {
      Assert.notNull("buildExtension", buildExtension);

      buildExtensions.add(buildExtension);

      return this;
    }

    public Seed4JModuleMavenBuildExtensionsBuilder addExtension(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      MavenBuildExtension buildExtension = MavenBuildExtension.builder()
        .groupId(groupId)
        .artifactId(artifactId)
        .versionSlug(versionSlug)
        .build();

      return addExtension(buildExtension);
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleMavenBuildExtensions build() {
      return new Seed4JModuleMavenBuildExtensions(this);
    }
  }
}
