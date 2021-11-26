package tech.jhipster.light.generator.server.springboot.cache.caffeine.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.light.TestUtils;
import tech.jhipster.light.UnitTest;
import tech.jhipster.light.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.light.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.light.generator.project.domain.Project;

import static org.mockito.Mockito.*;


@UnitTest
@ExtendWith(MockitoExtension.class)
class CaffeineDomainServiceTest {

  @InjectMocks
  CaffeineDomainService cacheDomainService;
  @Mock
  BuildToolService buildToolService;

  @Test
  void shouldInitCache() {
    Project project = TestUtils.tmpProject();
    cacheDomainService.initCache(project);

    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
  }
}
