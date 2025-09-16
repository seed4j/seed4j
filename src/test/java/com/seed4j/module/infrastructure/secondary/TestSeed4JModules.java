package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.defaultModuleResourceBuilder;
import static com.seed4j.module.domain.resource.Seed4JModulesResourceFixture.emptyHiddenModules;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.seed4j.module.application.Seed4JModulesApplicationService;
import com.seed4j.module.domain.*;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.resource.Seed4JModulesResources;
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

public final class TestSeed4JModules {

  private static final Set<NodePackagesVersionsReader> customNodePackagesVersionsReader = Collections.newSetFromMap(
    new ConcurrentHashMap<>()
  );
  private static final Set<JavaDependenciesReader> customJavaDependenciesReaders = Collections.newSetFromMap(new ConcurrentHashMap<>());

  private TestSeed4JModules() {}

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
        mock(JavaProjects.class),
        new Seed4JModulesResources(
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

      return new Seed4JModulesApplicationService(
        mock(Seed4JModuleEvents.class),
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader, customJavaDependenciesReaders),
        JavaDependenciesFixture.projectVersionsRepository(),
        new FileSystemProjectJavaBuildToolRepository(),
        GitTestUtil.gitRepository(),
        new FileSystemGeneratedProjectRepository(),
        mock(Seed4JPresetRepository.class)
      );
    }

    @Override
    public void apply() {
      modules.apply(new Seed4JModuleToApply(slug, module.properties()));
    }
  }

  public interface TestSeed4JModulesFinalApplyer {
    void apply();
  }
}
