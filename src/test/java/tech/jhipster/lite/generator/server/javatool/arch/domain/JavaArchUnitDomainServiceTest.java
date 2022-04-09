package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JavaArchUnitDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  JavaArchUnitDomainService javaArchUnitDomainService;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    when(buildToolService.getVersion(project, "archunit-junit5")).thenReturn(Optional.of("0.0.0"));

    javaArchUnitDomainService.init(project);

    verify(projectRepository, times(4)).template(any(Project.class), anyString(), anyString(), anyString());
    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any(Level.class));
  }

  @Test
  void shouldThrowExceptionOnInitWhenArchUnitDependencyVersionNotFound() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(project, "archunit-junit5")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> javaArchUnitDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
