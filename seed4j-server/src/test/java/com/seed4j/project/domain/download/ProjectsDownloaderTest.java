package com.seed4j.project.domain.download;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.seed4j.UnitTest;
import com.seed4j.project.domain.ProjectPath;
import com.seed4j.project.domain.ProjectsRepository;
import com.seed4j.project.domain.UnknownProjectException;
import com.seed4j.shared.projectfolder.domain.ProjectFolder;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
