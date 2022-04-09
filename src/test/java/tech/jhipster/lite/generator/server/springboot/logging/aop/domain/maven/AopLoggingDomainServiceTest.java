package tech.jhipster.lite.generator.server.springboot.logging.aop.domain.maven;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.logging.aop.domain.AopLoggingDomainService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AopLoggingDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @InjectMocks
  AopLoggingDomainService aopLoggingDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    aopLoggingDomainService.init(project);
    // Java files
    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
    // Properties modifications
    verify(springBootCommonService).addProperties(any(Project.class), anyString(), anyString());
    verify(springBootCommonService).addPropertiesLocal(any(Project.class), anyString(), anyString());
    verify(springBootCommonService).addPropertiesTest(any(Project.class), anyString(), anyString());
    // Maven dependency
    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
  }

  @Test
  void shouldAddDialectJava() {
    Project project = tmpProjectWithPomXml();

    aopLoggingDomainService.addJavaFiles(project);
    verify(projectRepository, times(3)).template(any(Project.class), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProjectWithPomXml();

    aopLoggingDomainService.addProperties(project);

    verify(springBootCommonService).addProperties(any(Project.class), anyString(), anyString());
    verify(springBootCommonService).addPropertiesLocal(any(Project.class), anyString(), anyString());
    verify(springBootCommonService).addPropertiesTest(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddMavenDependencies() {
    Project project = tmpProjectWithPomXml();

    aopLoggingDomainService.addMavenDependencies(project);

    verify(buildToolService).addDependency(any(Project.class), any(Dependency.class));
  }
}
