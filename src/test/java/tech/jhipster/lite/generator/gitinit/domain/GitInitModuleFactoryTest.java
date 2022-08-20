package tech.jhipster.lite.generator.gitinit.domain;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GitInitModuleFactoryTest {

  @Mock
  private GitRepository git;

  @InjectMocks
  private GitInitModuleFactory factory;

  @Test
  void shouldBuildModule() {
    String folder = TestFileUtils.tmpDirForTest();
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(folder).build();

    JHipsterModule module = factory.buildModule(properties);

    TestJHipsterModules.apply(module);

    verify(git).init(new JHipsterProjectFolder(folder));
  }
}
