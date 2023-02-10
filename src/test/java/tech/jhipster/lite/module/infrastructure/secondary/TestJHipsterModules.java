package tech.jhipster.lite.module.infrastructure.secondary;

import static org.mockito.Mockito.mock;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import java.util.List;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;
import tech.jhipster.lite.module.infrastructure.secondary.git.GitTestUtil;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesFixture;
import tech.jhipster.lite.module.infrastructure.secondary.npm.NpmVersionsFixture;
import tech.jhipster.lite.project.infrastructure.primary.JavaProjects;

public final class TestJHipsterModules {

  private TestJHipsterModules() {}

  static void apply(JHipsterModule module) {
    applyer(module).apply();
  }

  private static TestJHipsterModulesFinalApplyer applyer(JHipsterModule module) {
    return new TestJHipsterModulesApplyer(module);
  }

  private static class TestJHipsterModulesApplyer implements TestJHipsterModulesFinalApplyer {

    private final JHipsterModule module;
    private final JHipsterModuleSlug slug;
    private final JHipsterModulesApplicationService modules;

    private TestJHipsterModulesApplyer(JHipsterModule module) {
      this.module = module;
      this.slug = new JHipsterModuleSlug("test-module");
      this.modules = buildApplicationService(module);
    }

    private static JHipsterModulesApplicationService buildApplicationService(JHipsterModule module) {
      ProjectFilesReader filesReader = new FileSystemProjectFilesReader();

      FileSystemJHipsterModulesRepository modulesRepository = new FileSystemJHipsterModulesRepository(
        filesReader,
        NpmVersionsFixture.npmVersions(filesReader),
        mock(JavaProjects.class),
        new JHipsterModulesResources(
          List.of(defaultModuleResourceBuilder().slug("test-module").factory(properties -> module).build()),
          emptyHiddenModules()
        )
      );

      return new JHipsterModulesApplicationService(
        mock(JHipsterModuleEvents.class),
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader),
        JavaDependenciesFixture.projectVersionsRepository(),
        GitTestUtil.gitRepository()
      );
    }

    @Override
    public void apply() {
      modules.apply(new JHipsterModuleToApply(slug, module.properties()));
    }
  }

  public interface TestJHipsterModulesFinalApplyer {
    void apply();
  }
}
