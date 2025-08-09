package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.SeedModulesResourceFixture.defaultModuleResourceBuilder;
import static com.seed4j.module.domain.resource.SeedModulesResourceFixture.emptyHiddenModules;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.seed4j.module.application.SeedModulesApplicationService;
import com.seed4j.module.domain.*;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.resource.SeedModulesResources;
import com.seed4j.module.infrastructure.secondary.file.MustacheTemplateRenderer;
import com.seed4j.module.infrastructure.secondary.git.GitTestUtil;
import com.seed4j.module.infrastructure.secondary.javabuild.FileSystemProjectJavaBuildToolRepository;
import com.seed4j.module.infrastructure.secondary.javadependency.FileSystemJavaBuildCommandsHandler;
import com.seed4j.module.infrastructure.secondary.javadependency.JavaDependenciesFixture;
import com.seed4j.module.infrastructure.secondary.javadependency.JavaDependenciesReader;
import com.seed4j.module.infrastructure.secondary.nodejs.NodePackagesVersionsReader;
import com.seed4j.module.infrastructure.secondary.nodejs.NpmVersionsFixture;
import com.seed4j.project.infrastructure.primary.JavaProjects;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class TestSeedModules {

  private static final Set<NodePackagesVersionsReader> customNodePackagesVersionsReader = Collections.newSetFromMap(
    new ConcurrentHashMap<>()
  );
  private static final Set<JavaDependenciesReader> customJavaDependenciesReaders = Collections.newSetFromMap(new ConcurrentHashMap<>());

  private TestSeedModules() {}

  public static void register(NodePackagesVersionsReader nodePackagesVersionsReader) {
    assertThat(nodePackagesVersionsReader).as("Npm versions reader to register can't be null").isNotNull();

    customNodePackagesVersionsReader.add(nodePackagesVersionsReader);
  }

  public static void register(JavaDependenciesReader javaDependenciesReader) {
    assertThat(javaDependenciesReader).as("Java dependencies reader to register can't be null").isNotNull();

    customJavaDependenciesReaders.add(javaDependenciesReader);
  }

  public static void unregisterReaders() {
    customNodePackagesVersionsReader.clear();
    customJavaDependenciesReaders.clear();
  }

  static void apply(SeedModule module) {
    applyer(module).apply();
  }

  private static TestJHipsterModulesFinalApplyer applyer(SeedModule module) {
    return new TestJHipsterModulesApplyer(module);
  }

  private static final class TestJHipsterModulesApplyer implements TestJHipsterModulesFinalApplyer {

    private final SeedModule module;
    private final SeedModuleSlug slug;
    private final SeedModulesApplicationService modules;

    private TestJHipsterModulesApplyer(SeedModule module) {
      this.module = module;
      this.slug = new SeedModuleSlug("test-module");
      this.modules = buildApplicationService(module);
    }

    private static SeedModulesApplicationService buildApplicationService(SeedModule module) {
      ProjectFiles filesReader = new FileSystemProjectFiles();
      MustacheTemplateRenderer templateRenderer = new MustacheTemplateRenderer();
      FileSystemReplacer fileReplacer = new FileSystemReplacer(templateRenderer);
      FileSystemSeedModuleFiles files = new FileSystemSeedModuleFiles(filesReader, templateRenderer);

      FileSystemSeedModulesRepository modulesRepository = new FileSystemSeedModulesRepository(
        mock(JavaProjects.class),
        new SeedModulesResources(
          List.of(
            defaultModuleResourceBuilder()
              .slug("test-module")
              .factory(properties -> module)
              .build()
          ),
          emptyHiddenModules()
        ),
        files,
        fileReplacer,
        new FileSystemGitIgnoreHandler(fileReplacer),
        new FileSystemJavaBuildCommandsHandler(new FileSystemProjectJavaBuildToolRepository(), files, fileReplacer),
        new FileSystemPackageJsonHandler(NpmVersionsFixture.npmVersions(filesReader, customNodePackagesVersionsReader), templateRenderer),
        new FileSystemStartupCommandsReadmeCommandsHandler(fileReplacer)
      );

      return new SeedModulesApplicationService(
        mock(SeedModuleEvents.class),
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader, customJavaDependenciesReaders),
        JavaDependenciesFixture.projectVersionsRepository(),
        new FileSystemProjectJavaBuildToolRepository(),
        GitTestUtil.gitRepository(),
        new FileSystemGeneratedProjectRepository(),
        mock(SeedPresetRepository.class)
      );
    }

    @Override
    public void apply() {
      modules.apply(new SeedModuleToApply(slug, module.properties()));
    }
  }

  public interface TestJHipsterModulesFinalApplyer {
    void apply();
  }
}
