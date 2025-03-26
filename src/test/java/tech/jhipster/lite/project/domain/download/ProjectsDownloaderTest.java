package tech.jhipster.lite.project.domain.download;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.ProjectsRepository;
import tech.jhipster.lite.project.domain.UnknownProjectException;
import tech.jhipster.lite.shared.projectfolder.domain.ProjectFolder;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ProjectsDownloaderTest {

  @Mock
  private ProjectFolder projectFolder;

  @Mock
  private ProjectsRepository projects;

  @InjectMocks
  private ProjectsDownloader downloader;

  @Test
  void shouldNotDownloadFromInvalidPath() {
    when(projectFolder.isInvalid(anyString())).thenReturn(true);

    assertThatThrownBy(() -> downloader.download(new ProjectPath("invalid"))).isExactlyInstanceOf(InvalidDownloadException.class);
  }

  @Test
  void shouldNotDownloadUnknownProject() {
    assertThatThrownBy(() -> downloader.download(new ProjectPath("unknown"))).isExactlyInstanceOf(UnknownProjectException.class);
  }

  @Test
  void shouldDownloadProjectFromRepository() {
    Project project = new Project(new ProjectName("project"), new byte[] {});
    when(projects.get(any())).thenReturn(Optional.of(project));

    assertThat(downloader.download(new ProjectPath("path"))).isEqualTo(project);
  }
}
