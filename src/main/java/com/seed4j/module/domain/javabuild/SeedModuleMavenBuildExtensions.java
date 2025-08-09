package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
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

public class SeedModuleMavenBuildExtensions {

  private final Collection<MavenBuildExtension> buildExtensions;

  public SeedModuleMavenBuildExtensions(JHipsterModuleMavenBuildExtensionsBuilder builder) {
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

  public static JHipsterModuleMavenBuildExtensionsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleMavenBuildExtensionsBuilder(module);
  }

  public static final class JHipsterModuleMavenBuildExtensionsBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<MavenBuildExtension> buildExtensions = new ArrayList<>();

    private JHipsterModuleMavenBuildExtensionsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleMavenBuildExtensionsBuilder addExtension(MavenBuildExtension buildExtension) {
      Assert.notNull("buildExtension", buildExtension);

      buildExtensions.add(buildExtension);

      return this;
    }

    public JHipsterModuleMavenBuildExtensionsBuilder addExtension(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      MavenBuildExtension buildExtension = MavenBuildExtension.builder()
        .groupId(groupId)
        .artifactId(artifactId)
        .versionSlug(versionSlug)
        .build();

      return addExtension(buildExtension);
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public SeedModuleMavenBuildExtensions build() {
      return new SeedModuleMavenBuildExtensions(this);
    }
  }
}
