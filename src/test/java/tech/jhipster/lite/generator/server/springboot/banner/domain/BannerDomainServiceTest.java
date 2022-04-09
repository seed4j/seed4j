package tech.jhipster.lite.generator.server.springboot.banner.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class BannerDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  BannerDomainService bannerDomainService;

  @Test
  void shouldAddBannerJHipsterV7() {
    bannerDomainService.addBannerJHipsterV7(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerIppon() {
    bannerDomainService.addBannerIppon(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV7React() {
    bannerDomainService.addBannerJHipsterV7React(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV7Vue() {
    bannerDomainService.addBannerJHipsterV7Vue(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV2() {
    bannerDomainService.addBannerJHipsterV2(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddBannerJHipsterV3() {
    bannerDomainService.addBannerJHipsterV3(tmpProject());
    verify(projectRepository).add(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }
}
