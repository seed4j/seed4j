package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.application.Seed4JModulesApplicationService;
import com.seed4j.module.domain.*;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.preset.Presets;
import com.seed4j.module.domain.resource.Seed4JHiddenModules;
import com.seed4j.module.domain.resource.Seed4JModuleRank;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleSlugFactory;
import com.seed4j.module.domain.resource.Seed4JModuleTags;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
import com.seed4j.module.infrastructure.secondary.file.MustacheTemplateRenderer;
import com.seed4j.module.infrastructure.secondary.git.JGitGitRepository;
import com.seed4j.module.infrastructure.secondary.javabuild.FileSystemProjectJavaBuildToolRepository;
import com.seed4j.module.infrastructure.secondary.javadependency.FileSystemJavaBuildCommandsHandler;
import com.seed4j.module.infrastructure.secondary.javadependency.JavaDependenciesFixture;
import com.seed4j.module.infrastructure.secondary.nodejs.NpmVersionsFixture;
import java.util.List;
import java.util.Set;

public final class TestSeed4JModules {

  private TestSeed4JModules() {}

  static void apply(Seed4JModule module) {
    applyer(module).apply();
  }

  private static TestSeed4JModulesFinalApplyer applyer(Seed4JModule module) {
    return new TestSeed4JModulesApplyer(module);
  }

  private static final class TestSeed4JModulesApplyer implements TestSeed4JModulesFinalApplyer {

    private final Seed4JModule module;
    private final Seed4JModuleSlug slug;
    private final Seed4JModulesApplicationService modules;

    private TestSeed4JModulesApplyer(Seed4JModule module) {
      this.module = module;
      this.slug = new Seed4JModuleSlug("test-module");
      this.modules = buildApplicationService(module);
    }

    private static Seed4JModulesApplicationService buildApplicationService(Seed4JModule module) {
      ProjectFiles filesReader = new FileSystemProjectFiles();
      MustacheTemplateRenderer templateRenderer = new MustacheTemplateRenderer();
      FileSystemReplacer fileReplacer = new FileSystemReplacer(templateRenderer);
      FileSystemSeed4JModuleFiles files = new FileSystemSeed4JModuleFiles(filesReader, templateRenderer);

      FileSystemSeed4JModulesRepository modulesRepository = new FileSystemSeed4JModulesRepository(
        moduleApplied -> {},
        new Seed4JModulesResources(
          List.of(
            Seed4JModuleResource.builder()
              .slug(new TestModuleSlug())
              .withoutProperties()
              .apiDoc("test", "Test module")
              .standalone()
              .tags(Seed4JModuleTags.builder().build())
              .factory(properties -> module)
          ),
          new Seed4JHiddenModules(List.of(), List.of())
        ),
        files,
        fileReplacer,
        new FileSystemGitIgnoreHandler(fileReplacer),
        new FileSystemJavaBuildCommandsHandler(new FileSystemProjectJavaBuildToolRepository(), files, fileReplacer),
        new FileSystemPackageJsonHandler(NpmVersionsFixture.npmVersions(filesReader, Set.of()), templateRenderer),
        new FileSystemStartupCommandsReadmeCommandsHandler(fileReplacer)
      );

      return new Seed4JModulesApplicationService(
        moduleApplied -> {},
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader, Set.of()),
        JavaDependenciesFixture.projectVersionsRepository(),
        new FileSystemProjectJavaBuildToolRepository(),
        new JGitGitRepository(),
        new FileSystemGeneratedProjectRepository(),
        () -> new Presets(List.of())
      );
    }

    @Override
    public void apply() {
      modules.apply(new Seed4JModuleToApply(slug, module.properties()));
    }
  }

  private static final class TestModuleSlug implements Seed4JModuleSlugFactory {

    @Override
    public String get() {
      return "test-module";
    }

    @Override
    public Seed4JModuleRank rank() {
      return Seed4JModuleRank.RANK_D;
    }
  }

  public interface TestSeed4JModulesFinalApplyer {
    void apply();
  }
}
